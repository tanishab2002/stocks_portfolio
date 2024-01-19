package com.example.stocks_portfolio.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TradeDTO {

    private long tradeId;


    private long userId;


    private String stockId;


    private String tradeType;


    private int quantity;
}
