package com.example.stocks_portfolio.ServiceTests;

import com.example.stocks_portfolio.entities.Stock;
import com.example.stocks_portfolio.entities.Trade;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.PortfolioRepository;
import com.example.stocks_portfolio.repositories.StockRepository;
import com.example.stocks_portfolio.repositories.TradeRepository;
import com.example.stocks_portfolio.service.PortfolioService;
import com.example.stocks_portfolio.service.TradeService;
import com.example.stocks_portfolio.service.UserService;
import com.example.stocks_portfolio.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TradeServiceTests {

    private TradeRepository tradeRepository;
    private UserService userService;
    private StockRepository stockRepository;
    private PortfolioRepository portfolioRepository;
    private TradeService tradeService;

    private PortfolioService portfolioService;

    @BeforeEach
    public void setUp(){
        tradeRepository = mock(TradeRepository.class);
        userService = mock(UserService.class);
        stockRepository = mock(StockRepository.class);
        portfolioRepository = mock(PortfolioRepository.class);
        tradeService = new TradeServiceImpl(tradeRepository, userService, stockRepository, portfolioRepository, portfolioService);
    }

    @Test
    public void testSaveTradeValid() throws Exception {
        Trade trade = new Trade(1, 1, "IN0020010081", "Buy", 1);

        when(userService.isUserPresent(trade.getUserId()))
                .thenReturn(true);

        when(stockRepository.findById(trade.getStockId()))
                .thenReturn(Optional.of(new Stock(trade.getStockId(), "1018GS2026", 121.31, 121.31, 121.31, 121.31)));


        ResponseEntity<String> response = tradeService.saveTrade(trade);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Saved Successfully with status code " + HttpStatus.ACCEPTED, response.getBody());

        verify(userService).isUserPresent(trade.getUserId());
        verify(stockRepository).findById(trade.getStockId());
        verify(tradeRepository).save(trade);
    }

    @Test
    public void testSaveTrade_InvalidData() throws Exception {
        Trade trade = new Trade(1, 1, "IIN0020010081", "Buy", 1);

        when(userService.isUserPresent(trade.getUserId()))
                .thenReturn(true);

        when(stockRepository.findById(trade.getStockId()))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = tradeService.saveTrade(trade);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data with status code " + HttpStatus.BAD_REQUEST, response.getBody());


    }
}
