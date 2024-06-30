package com.inventorymanager.controller;

import com.inventorymanager.domain.order.DailySalesReport;
import com.inventorymanager.domain.order.Order;
import com.inventorymanager.domain.supplier.Supplier;
import com.inventorymanager.infrastructure.stock.StockRepo;
import com.inventorymanager.service.order.Dtos.OrderReadDto;
import com.inventorymanager.service.order.IOrderService;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.IStockService;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/reports")
public class ReportController {
    @Autowired
    private IStockService stockService;
    @Autowired
    private IOrderService orderService;
    @GetMapping("/stock-level")
    public ResponseEntity<StreamingResponseBody> getStockLevelReport(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("SUPERADMIN"));
        if (isAdmin) {
            return getStockLevelReportForAdmin();
        }
        boolean isSupplier = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("SUPPLIER"));
        if (isSupplier) {
            Supplier supplierLogIn = (Supplier) authentication.getPrincipal();
            return getStockLevelReportForSupplier(supplierLogIn.getId());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/sales")
    public ResponseEntity<StreamingResponseBody> getSalesReport(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate")  LocalDateTime endDate
    ){
        StreamingResponseBody stream = outputStream -> {
            Map<LocalDate, DailySalesReport> report = orderService.getDailySalesReport(startDate, endDate);
            List<DailySalesReportDto> reportDtos = report.entrySet().stream()
                    .map(entry -> new DailySalesReportDto(entry.getKey(), entry.getValue().getTotalSales(), entry.getValue().getTotalProfit()))
                    .collect(Collectors.toList());

            try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                new StatefulBeanToCsvBuilder<DailySalesReportDto>(writer).build().write(reportDtos);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                throw new RuntimeException(e);
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", "stock_level_report.csv"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(stream);
    }


    private ResponseEntity<StreamingResponseBody> getStockLevelReportForAdmin(){
        StreamingResponseBody stream = outputStream -> {
            List<StockReadDto> stocks = stockService.getAllStocks();
            try(Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)){
                new StatefulBeanToCsvBuilder<StockReadDto>(writer).build().write(stocks);
            }catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
                throw new RuntimeException(e);
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", "stock_level_report.csv"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(stream);
    }

    private ResponseEntity<StreamingResponseBody> getStockLevelReportForSupplier(UUID supplierId){
        StreamingResponseBody stream = outputStream -> {
            List<StockReadDto> stocks = stockService.getStocksBySupplier(supplierId);
            try(Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)){
                new StatefulBeanToCsvBuilder<StockReadDto>(writer).build().write(stocks);
            }catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
                throw new RuntimeException(e);
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", "stock_level_report.csv"))
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .body(stream);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private class DailySalesReportDto {
        private LocalDate date;
        private double totalSales;
        private double totalProfit;

    }
}
