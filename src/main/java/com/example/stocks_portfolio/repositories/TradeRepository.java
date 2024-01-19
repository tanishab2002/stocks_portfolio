package com.example.stocks_portfolio.repositories;

import com.example.stocks_portfolio.DTO.TradeDTO;
import com.example.stocks_portfolio.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {


    List<Trade> findByUserIdAndStockId(long userId, String stockId);
}
