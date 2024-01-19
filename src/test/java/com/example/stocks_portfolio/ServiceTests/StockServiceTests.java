package com.example.stocks_portfolio.ServiceTests;

import com.example.stocks_portfolio.DTO.StockDTO;
import com.example.stocks_portfolio.entities.Stock;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.StockRepository;
import com.example.stocks_portfolio.service.StockService;
import com.example.stocks_portfolio.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static reactor.core.publisher.Mono.when;

public class StockServiceTests {
    private StockRepository stockRepository;
    private StockService stockService;

    @BeforeEach
    public void setUp(){
        stockRepository = mock(StockRepository.class);
        stockService = new StockServiceImpl(stockRepository);
    }

    @Test
    public void testGetStockByIdStockExists(){
        String stockId = "IN0020010081";
        Stock stock = new Stock(stockId, "1018GS2026", 121.31, 121.31, 121.31, 121.31);

        Mockito.when(stockRepository.findById(stockId))
                .thenReturn(Optional.of(stock));

        StockDTO result = stockService.getStockById(stockId);

        assertNotNull(result);
        assertEquals(stock.getStockId(), result.getStockId());
        assertEquals(stock.getStockName(), result.getStockName());
        assertEquals(stock.getOpenPrice(), result.getOpenPrice());
        assertEquals(stock.getClosePrice(), result.getClosePrice());
        assertEquals(stock.getHighPrice(), result.getHighPrice());
        assertEquals(stock.getLowPrice(), result.getLowPrice());
    }

    @Test
    public void testGetStockByIdStockNotExists(){
        String stockId = "IN0020010";
        Stock stock = new Stock(stockId, "1018GS2026", 121.31, 121.31, 121.31, 121.31);

        Mockito.when(stockRepository.findById(stockId))
                .thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> {
            stockService.getStockById(stockId);
        });
    }


}
