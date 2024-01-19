package com.example.stocks_portfolio.service;

import com.example.stocks_portfolio.DTO.StockDTO;
import org.springframework.web.multipart.MultipartFile;

public interface StockService {

    StockDTO getStockById(String id);

    String updateStocksFromFile(MultipartFile fileName) throws Exception;
}
