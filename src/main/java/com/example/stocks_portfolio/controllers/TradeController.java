package com.example.stocks_portfolio.controllers;

import com.example.stocks_portfolio.entities.Trade;
import com.example.stocks_portfolio.service.TradeService;
import lombok.AllArgsConstructor;
import org.aspectj.bridge.Message;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trades")
@AllArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/save")
    public ResponseEntity<String> saveTrade(@RequestBody Trade trade) throws Exception {
        return tradeService.saveTrade(trade);
    }
}
