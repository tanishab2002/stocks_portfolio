package com.example.stocks_portfolio.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Table(name = "stocks")
public class Stock {


    @Id
    @Column(name = "id")
    private String stockId;

    private String stockName;

    private double openPrice;

    private double closePrice;

    private double highPrice;

    private double lowPrice;

//    private double settlement_price;
}
