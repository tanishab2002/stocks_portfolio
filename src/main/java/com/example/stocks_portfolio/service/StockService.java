package com.example.stocks_portfolio.service;

import com.example.stocks_portfolio.DTO.StockDTO;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface StockService {

    StockDTO getStockById(String id) throws Exception;

    String updateStocksFromFile(MultipartFile fileName) throws Exception;
}
