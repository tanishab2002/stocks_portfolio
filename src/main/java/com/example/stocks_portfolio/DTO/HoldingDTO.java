package com.example.stocks_portfolio.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HoldingDTO {
    private String stockName;

    private String stockId;

    private int quantity;

    private double buyPrice;

    private double currentPrice;

    private double profit;
}
