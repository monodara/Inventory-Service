package com.inventorymanager.controller;

import com.inventorymanager.infrastructure.stock.StockRepo;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.IStockService;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("api/v1/reports")
public class ReportController {
    @Autowired
    private IStockService stockService;
    @GetMapping("/stock-level")
    public ResponseEntity<StreamingResponseBody> getStockLevelReport(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + authentication.getAuthorities());
        StreamingResponseBody steam = outputStream -> {

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
                .body(steam);
    }
}
