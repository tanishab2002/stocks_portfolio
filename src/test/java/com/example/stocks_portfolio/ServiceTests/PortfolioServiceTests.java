package com.example.stocks_portfolio.ServiceTests;

import com.example.stocks_portfolio.DTO.HoldingDTO;
import com.example.stocks_portfolio.DTO.PortfolioDTO;
import com.example.stocks_portfolio.DTO.StockDTO;
import com.example.stocks_portfolio.entities.Portfolio;
import com.example.stocks_portfolio.entities.Trade;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.PortfolioRepository;
import com.example.stocks_portfolio.repositories.TradeRepository;
import com.example.stocks_portfolio.service.PortfolioService;
import com.example.stocks_portfolio.service.StockService;
import com.example.stocks_portfolio.service.UserService;
import com.example.stocks_portfolio.service.impl.PortfolioServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PortfolioServiceTests {

    private UserService userService;
    private StockService stockService;
    private TradeRepository tradeRepository;
    private PortfolioRepository portfolioRepository;

    private PortfolioService portfolioService;

    @BeforeEach
    public void setUp(){
        userService = mock(UserService.class);
        stockService = mock(StockService.class);
        tradeRepository = mock(TradeRepository.class);
        portfolioRepository = mock(PortfolioRepository.class);
        portfolioService = new PortfolioServiceImpl(portfolioRepository, userService, stockService, tradeRepository);
    }

    @Test
    public void testGetPortfolioUserPresentButNotTraded() throws Exception {

        long userId = 3;
        List<HoldingDTO> holdings = new ArrayList<>();

        when(userService.isUserPresent(userId))
                .thenReturn(true);

        when(portfolioRepository.findById(userId))
                .thenReturn(Optional.empty());



        PortfolioDTO response = portfolioService.getPortfolio(userId);

        assertEquals(userId, response.getUserId());
        assertEquals(holdings, response.getHoldings());
        assertEquals(0, response.getTotalHoldings());
        assertEquals(0.0, response.getTotalBuyPrice());
        assertEquals(0.0, response.getProfit());
        assertEquals(0.0, response.getProfitPercentage());

    }

    @Test
    public void testGetPortfolioUserNotPresent(){
        long userId = 20;

        when(userService.isUserPresent(userId))
                .thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> {
            portfolioService.getPortfolio(userId);
        });
    }

    @Test
    public void testGetPortfolioUserPresentAndTraded() throws Exception {
        long userId = 2;

        when(userService.isUserPresent(userId))
                .thenReturn(true);

        List<String> stockId = new ArrayList<>();
        stockId.add("INF174KA1GC5");

        when(portfolioRepository.findById(userId))
                .thenReturn(Optional.of(new Portfolio(userId, stockId)));

        when(stockService.getStockById("INF174KA1GC5"))
                .thenReturn(new StockDTO("INF174KA1GC5", "KOTAKIT", 39.2, 38.6, 39.2, 38.45));

        List<Trade> trades = new ArrayList<>();
        trades.add(new Trade(4,2,"INF174KA1GC5","Buy", 1));

        when(tradeRepository.findByUserIdAndStockId(userId, "INF174KA1GC5"))
                .thenReturn(trades);

        PortfolioDTO response = portfolioService.getPortfolio(userId);


//        System.out.println(response.getHoldings().get(0).getStockName());
//        System.out.println(response.getHoldings().get(0).getStockId());
//        System.out.println(response.getHoldings().get(0).getQuantity());
//        System.out.println(response.getHoldings().get(0).getBuyPrice());
//        System.out.println(response.getHoldings().get(0).getCurrentPrice());
//        System.out.println(response.getHoldings().get(0).getProfit());
//        System.out.println("Holdings end");
//        System.out.println(response.getTotalHoldings());
//        System.out.println(response.getTotalBuyPrice());
//        System.out.println(response.getProfit());
//        System.out.println(response.getProfitPercentage());



        HoldingDTO holding = new HoldingDTO("KOTAKIT", "INF174KA1GC5", 1, 39.2, 38.6, 0.6000000000000014);
        List<HoldingDTO> holdings = new ArrayList<>();
        holdings.add(holding);

        assertEquals(userId, response.getUserId());
//        assertEquals(holdings, response.getHoldings());
        assertEquals(1, response.getTotalHoldings());
        assertEquals(39.2, response.getTotalBuyPrice());
        assertEquals(0.6000000000000014, response.getProfit());
        assertEquals(1.5306122448979627, response.getProfitPercentage());
    }
}
