package com.inventorymanager.service.order;

import com.inventorymanager.domain.exception.InsufficientStockException;
import com.inventorymanager.domain.order.*;
import com.inventorymanager.domain.stock.IStockRepo;
import com.inventorymanager.domain.stock.Stock;
import com.inventorymanager.domain.supplier.ISupplierRepo;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.service.notification.ILowStockAlertService;
import com.inventorymanager.service.notification.IOrderStatusNotiService;
import com.inventorymanager.service.order.Dtos.OrderCreateDto;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.Dtos.OrderUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepo orderRepo;
    @Autowired
    private IStockRepo stockRepo;
    @Autowired
    private ISupplierRepo supplierRepo;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ILowStockAlertService lowStockAlertService;
    @Autowired
    private IOrderStatusNotiService orderStatusNotiService;

    @Value("${stock.threshold}")
    private int stockThreshold;

    public void createDummyOrders() {
        List<Stock> stocks = stockRepo.getAllStocks();

        IntStream.range(0, 50).forEach(i -> {
            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setOrderDate(LocalDateTime.now().minusDays((int) (Math.random() * 10)));
            order.setStatus(OrderStatus.PROCESSING);
            order.setDeliverDate(LocalDateTime.now().plusDays((int) (Math.random() * 30)));
            order.setClientEmail("client" + i + "@example.com");

            List<OrderItem> orderItems = new ArrayList<>();
            IntStream.range(0, 10).forEach(j -> {
                OrderItem item = new OrderItem();
                item.setId(UUID.randomUUID());
                item.setOrder(order);
                item.setStock(stocks.get((int) (Math.random() * stocks.size())));
                item.setQuantity((int) (Math.random() * 10) + 1);

                double randomPrice = Math.random() * 100;
                BigDecimal sellingPrice = new BigDecimal(Double.toString(randomPrice));
                sellingPrice = sellingPrice.setScale(2, RoundingMode.HALF_UP);
                item.setSellingPrice(sellingPrice.doubleValue());

                orderItems.add(item);
            });

            order.setOrderItems(orderItems);
            orderRepo.createOrder(order);
        });
    }

    @Override
    public OrderReadDto getOrderById(UUID id) {
        Order order = orderRepo.getOrderById(id);
        return orderMapper.ReadOrder(order);
    }

    @Override
    public List<OrderReadDto> getAllOrders() {
        return orderRepo.getAllOrders().stream().map(orderMapper::ReadOrder).toList();
    }

    @Override
    @Transactional
    public OrderReadDto createOrder(OrderCreateDto orderCreateDto) {
        Order order = orderMapper.toOrder(orderCreateDto);
        List<OrderItem> orderItems = orderCreateDto.getOrderItems().stream()
                .map(itemDto -> {
                    Stock stock = stockRepo.getStockById(itemDto.getStockId());
                    if (stock.getQuantity() < itemDto.getQuantity()) {
                        throw new InsufficientStockException("Insufficient stock for item: " + stock.getId());
                    }
                    int orderedQuantity = itemDto.getQuantity();
                    int currentStockQuantity = stock.getQuantity();
                    int newStockQuantity = currentStockQuantity - orderedQuantity;
                    stock.setQuantity(newStockQuantity);
                    stock = stockRepo.updateStock(stock);
                    if(newStockQuantity < stockThreshold){//Send stock level warning
                        Supplier supplier = supplierRepo.getSupplierById(stock.getSupplier().getId());
                        lowStockAlertService.sendLowStockAlert(supplier.getEmail(), List.of(stock));
                    }
                    OrderItem orderItem = orderItemMapper.toOrderItem(itemDto);
                    orderItem.setOrder(order); // Ensure order is set correctly
                    return orderItem;
                }).collect(Collectors.toList());
        order.setOrderItems(orderItems); // Set the orderItems to the order
        Order orderCreated = orderRepo.createOrder(order);
        return orderMapper.ReadOrder(orderCreated);
    }

    @Override
    public OrderReadDto updateOrder(UUID id, OrderUpdateDto orderUpdateDto) {
        Order order = orderRepo.getOrderById(id);
        orderMapper.updateOrderFromDto(orderUpdateDto, order);
        Order orderUpdated = orderRepo.updateOrder(order);
        if(orderUpdated.getStatus() == OrderStatus.SHIPPED){
            orderStatusNotiService.sendShippedNotification(orderUpdated);
        }
        return orderMapper.ReadOrder(orderUpdated);
    }
    public boolean cancelOrder(UUID id){
        Order order = orderRepo.getOrderById(id);
        order.setStatus(OrderStatus.CANCELED);
        orderRepo.updateOrder(order);
        return true;
    }

    @Override
    public void deleteOrder(UUID id) {
        orderRepo.deleteOrder(id);
    }

    @Override
    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepo.getOrdersByDateRange(startDate,endDate);
    }

    @Override
    public Map<LocalDate, DailySalesReport> getDailySalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = getOrdersByDateRange(startDate, endDate);

        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getOrderDate().toLocalDate(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                this::calculateDailySalesReport
                        )
                ));
    }
    private DailySalesReport calculateDailySalesReport(List<Order> orders) {
        BigDecimal totalSales = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;

        for (Order order : orders) {
            for (OrderItem item : order.getOrderItems()) {
                BigDecimal sales = BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getSellingPrice()));
                BigDecimal cost = BigDecimal.valueOf(item.getQuantity()).multiply(BigDecimal.valueOf(item.getStock().getInputPrice()));
                BigDecimal profit = sales.subtract(cost);

                totalSales = totalSales.add(sales);
                totalProfit = totalProfit.add(profit);
            }
        }

        // Round totalSales and totalProfit to 2 decimal places
        totalSales = totalSales.setScale(2, RoundingMode.HALF_UP);
        totalProfit = totalProfit.setScale(2, RoundingMode.HALF_UP);

        return new DailySalesReport(totalSales.doubleValue(), totalProfit.doubleValue());
    }
}
