package com.example.stocks_portfolio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tradeId;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String stockId;

    @Column(nullable = false)
    private String tradeType;

    @Column(nullable = false)
    private int quantity;

//    private double buyPrice;
}
