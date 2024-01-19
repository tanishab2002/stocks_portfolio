package com.example.stocks_portfolio.service;

import com.example.stocks_portfolio.DTO.PortfolioDTO;

public interface PortfolioService {
    PortfolioDTO getPortfolio(Long id) throws Exception;
}
