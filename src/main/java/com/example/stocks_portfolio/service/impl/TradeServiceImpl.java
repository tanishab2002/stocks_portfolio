package com.example.stocks_portfolio.service.impl;

import com.example.stocks_portfolio.entities.Portfolio;
import com.example.stocks_portfolio.entities.Trade;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.PortfolioRepository;
import com.example.stocks_portfolio.repositories.StockRepository;
import com.example.stocks_portfolio.repositories.TradeRepository;
import com.example.stocks_portfolio.service.TradeService;
import com.example.stocks_portfolio.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final UserService userService;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public ResponseEntity<String> saveTrade(Trade trade) throws Exception {


        if(trade == null){
            return ResponseEntity.badRequest()
                    .body("Payload not provided with status code " + HttpStatus.BAD_REQUEST);
        }

        try{
            if(!userService.isUserPresent(trade.getUserId())){
                throw new DataNotFoundException("User not found");
            }

            if(stockRepository.findById(trade.getStockId()).isEmpty()){
                throw new DataNotFoundException("Stock does not exist");
            }

            if((!Objects.equals(trade.getTradeType(), "Buy") && !Objects.equals(trade.getTradeType(), "Sell")) || (trade.getQuantity() <= 0)){
                System.out.println("invalid types");
                return ResponseEntity.badRequest()
                        .body("Invalid data with status code " + HttpStatus.BAD_REQUEST);
            }
            saveUserTradeDetails(trade);
            tradeRepository.save(trade);
            return ResponseEntity.ok()
                    .body("Saved Successfully with status code " + HttpStatus.ACCEPTED);
        }catch(Exception e){
            System.out.println("Exception : " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body("Invalid data with status code " + HttpStatus.BAD_REQUEST);
        }
    }

    private void saveUserTradeDetails(Trade trade) {
//        System.out.println("Hello");

        Optional<Portfolio> portfolio = portfolioRepository.findById(trade.getUserId());

//        System.out.println("Hello2");

        if(portfolio.isEmpty()){
//            System.out.println("Empty");
            Portfolio portfolio1 = new Portfolio();
            portfolio1.setUserId(trade.getUserId());
            List<String> stockIdList = new ArrayList<>();
            stockIdList.add(trade.getStockId());
            portfolio1.setStockIdList(stockIdList);

            portfolioRepository.save(portfolio1);
            return;
        }

//        System.out.println("Hello3");
        List<String> stockIdList = portfolio.get().getStockIdList();
//        System.out.println("Hello4");

//        for(String s : stockIdList){
//            System.out.println(s);
//        }

        if(portfolio.get().getStockIdList().contains(trade.getStockId())){
            return;
        }


        stockIdList.add(trade.getStockId());
        Portfolio p = portfolio.get();
        p.setStockIdList(stockIdList);

        portfolioRepository.save(p);
    }
}
