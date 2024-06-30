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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        double totalSales = 0.0;
        double totalProfit = 0.0;

        for (Order order : orders) {
            for (OrderItem item : order.getOrderItems()) {
                double sales = item.getQuantity() * item.getSellingPrice();
                double cost = item.getQuantity() * item.getStock().getInputPrice();
                double profit = sales - cost;

                totalSales += sales;
                totalProfit += profit;
            }
        }
        return new DailySalesReport(totalSales, totalProfit);
    }
}
