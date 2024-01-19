package com.example.stocks_portfolio.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PortfolioDTO {

    private long userId;

    private List<HoldingDTO> holdings;

    private int totalHoldings;

    private double totalBuyPrice;

    private double profit;

    private double profitPercentage;
}
