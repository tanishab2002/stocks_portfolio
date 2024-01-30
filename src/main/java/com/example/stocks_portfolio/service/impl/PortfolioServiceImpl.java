package com.example.stocks_portfolio.service.impl;

import com.example.stocks_portfolio.DTO.HoldingDTO;
import com.example.stocks_portfolio.DTO.PortfolioDTO;
import com.example.stocks_portfolio.DTO.StockDTO;
import com.example.stocks_portfolio.DTO.TradeDTO;
import com.example.stocks_portfolio.entities.Portfolio;
import com.example.stocks_portfolio.entities.Stock;
import com.example.stocks_portfolio.entities.Trade;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.PortfolioRepository;
import com.example.stocks_portfolio.repositories.TradeRepository;
import com.example.stocks_portfolio.service.PortfolioService;
import com.example.stocks_portfolio.service.StockService;
import com.example.stocks_portfolio.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserService userService;
    private final StockService stockService;
    private final TradeRepository tradeRepository;

    @Override
    public PortfolioDTO getPortfolio(Long id) throws Exception {

        if(!userService.isUserPresent(id)){
//            System.out.println("user not present");
            throw new DataNotFoundException("User does not exist");
//            List<String> stockIds = new ArrayList<>();
//            return new PortfolioDTO(id, stockIds, 0, 0, 0, 0);
        }

        Optional<Portfolio> portfolio = portfolioRepository.findById(id);

        if(portfolio.isEmpty()){
            List<HoldingDTO> holdings = new ArrayList<>();
            return new PortfolioDTO(id, holdings, 0, 0, 0, 0);
        }


       List<String> stockIds = portfolio.get().getStockIdList();

//        System.out.println("Hello1");

        return preparePortfolio(id, stockIds);
    }

    private PortfolioDTO preparePortfolio(Long id, List<String> stockIds) throws Exception {

        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setUserId(id);
//        System.out.println("Hello2");
        List<HoldingDTO> holdings = createHoldingsList(id, stockIds);
        portfolioDTO.setHoldings(holdings);
        portfolioDTO.setTotalHoldings(portfolioDTO.getHoldings().size());
        Map<String, Double> m = calculateTotalBuyPrice(holdings);
        portfolioDTO.setTotalBuyPrice(m.get("totalBuyPrice"));
        portfolioDTO.setProfit(m.get("profit"));
        portfolioDTO.setProfitPercentage(calculateProfitPercent(portfolioDTO));
        return portfolioDTO;
    }

    private Map<String, Double> calculateTotalBuyPrice(List<HoldingDTO> holdings) {
        double totalBuyPrice = 0;
        double profit = 0;

        for(HoldingDTO holdingDTO : holdings){
            totalBuyPrice += holdingDTO.getBuyPrice();
            profit += holdingDTO.getProfit();
        }

        Map<String, Double> m = new HashMap<>();
        m.put("totalBuyPrice", totalBuyPrice);
        m.put("profit", profit);

        return m;
    }

    private double calculateProfitPercent(PortfolioDTO portfolioDTO) {
        double profit = portfolioDTO.getProfit();
        double buyPrice = portfolioDTO.getTotalBuyPrice();

        if(buyPrice == 0)
            return 0;

        return (profit / buyPrice) * 100;
    }

    private List<HoldingDTO> createHoldingsList(long userId, List<String> stockIds) throws Exception {
        List<HoldingDTO> holdings = new ArrayList<>();
//        System.out.println("Hello3");
        for(String stockId : stockIds){
//            System.out.println(stockId);
            HoldingDTO holding = new HoldingDTO();
            StockDTO stock = stockService.getStockById(stockId);
//            System.out.println("Hello4");
            String stockName = stock.getStockName();
            double currentPrice = stock.getClosePrice();
//            System.out.println("Hello5");
            holding.setStockId(stockId);
            holding.setStockName(stockName);
            holding.setCurrentPrice(currentPrice);
//            System.out.println("Hello6");
            Map<String, Double> m = findTradeValues(userId, stockId, holding.getCurrentPrice());

//            if(m.get("quantity") == 0){
//                continue;
//            }

            holding.setProfit(m.get("profit"));
            holding.setQuantity(m.get("quantity").intValue());
            holding.setBuyPrice(m.get("buyPrice"));

            holdings.add(holding);
        }

        return holdings;
    }

    private Map<String, Double> findTradeValues(long userId, String stockId, double currentPrice) throws Exception {

        List<Trade> trades = tradeRepository.findByUserIdAndStockId(userId, stockId);


//        System.out.println("Hello7");
        double buy_price = stockService.getStockById(stockId).getOpenPrice();

        double buyPrice = 0, totalBuyPrice = 0;
        double profit = 0;
        double quantity = 0;
        int totalQuantity = 0;

        for(Trade trade : trades){
            if(Objects.equals(trade.getTradeType(), "Buy")){
                quantity += trade.getQuantity();
                buyPrice += (trade.getQuantity() * buy_price);
                totalQuantity += trade.getQuantity();
            }else{
                quantity -= trade.getQuantity();
                buyPrice -= (trade.getQuantity() * buy_price);
                totalQuantity += trade.getQuantity();
            }
        }

        totalBuyPrice = buyPrice / totalQuantity;
        profit = totalBuyPrice - currentPrice;

        Map<String, Double> m = new HashMap<>();
        m.put("buyPrice", totalBuyPrice);
        m.put("profit", profit);
        m.put("quantity", quantity);

        return m;
    }


}
