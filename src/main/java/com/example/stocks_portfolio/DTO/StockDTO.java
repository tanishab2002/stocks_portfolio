package com.example.stocks_portfolio.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StockDTO {

    private String stockId;

    private String stockName;

    private double openPrice;

    private double closePrice;

    private double highPrice;

    private double lowPrice;

//    private double settlement_price;
}
