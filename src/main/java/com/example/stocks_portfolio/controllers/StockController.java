package com.example.stocks_portfolio.controllers;

import com.example.stocks_portfolio.DTO.StockDTO;
import com.example.stocks_portfolio.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {


    private final StockService stockService;

    @GetMapping("/{id}")
    public StockDTO getStockById(@PathVariable String id){
        return stockService.getStockById(id);
    }

    @PostMapping("/update")
    public String updateStocksFromFile(@RequestParam("bhav") MultipartFile fileName) throws Exception {
        return stockService.updateStocksFromFile(fileName);
    }
}
