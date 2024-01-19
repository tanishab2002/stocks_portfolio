package com.example.stocks_portfolio.controllers;

import com.example.stocks_portfolio.DTO.PortfolioDTO;
import com.example.stocks_portfolio.entities.Portfolio;
import com.example.stocks_portfolio.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{id}")
    public PortfolioDTO getPortfolio(@PathVariable Long id) throws Exception{
        return portfolioService.getPortfolio(id);
    }
}
