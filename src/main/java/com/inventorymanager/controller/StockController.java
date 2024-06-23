package com.inventorymanager.controller;

import com.inventorymanager.controller.shared.SuccessResponseEntity;
import com.inventorymanager.service.stock.IStockService;
import com.inventorymanager.service.stock.Dtos.StockCreateDto;
import com.inventorymanager.service.stock.Dtos.StockReadDto;
import com.inventorymanager.service.stock.Dtos.StockUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    @Autowired
    private IStockService stockService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<StockReadDto>> getStockById(@PathVariable UUID id) {
        StockReadDto stockReadDto = stockService.getStockById(id);
        SuccessResponseEntity<StockReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(stockReadDto)));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<SuccessResponseEntity<StockReadDto>> getAllStocks() {
        List<StockReadDto> stocks = stockService.getAllStocks();
        SuccessResponseEntity<StockReadDto> response = new SuccessResponseEntity<>();
        response.setData(stocks);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponseEntity<StockReadDto>> createStock(@RequestBody @Valid StockCreateDto stockCreateDto) {
        StockReadDto stockCreated = stockService.createStock(stockCreateDto);
        SuccessResponseEntity<StockReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(stockCreated)));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<StockReadDto>> updateStock(@PathVariable UUID id, @RequestBody StockUpdateDto stockUpdateDto) {
        StockReadDto stockUpdated = stockService.updateStock(id, stockUpdateDto);
        SuccessResponseEntity<StockReadDto> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(stockUpdated)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseEntity<Boolean>> deleteStock(@PathVariable UUID id) {
        stockService.deleteStock(id);
        SuccessResponseEntity<Boolean> response = new SuccessResponseEntity<>();
        response.setData(new ArrayList<>(List.of(true)));
        return ResponseEntity.ok(response);
    }
}
