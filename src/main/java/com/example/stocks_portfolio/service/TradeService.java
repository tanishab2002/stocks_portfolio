package com.example.stocks_portfolio.service;

import com.example.stocks_portfolio.entities.Trade;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;

public interface TradeService {
    ResponseEntity<String> saveTrade(Trade trade) throws Exception;
}
