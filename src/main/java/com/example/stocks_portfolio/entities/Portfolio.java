package com.example.stocks_portfolio.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Portfolio {

    @Id
    private long userId;

    @ElementCollection(targetClass = String.class)
    private List<String> stockIdList = new ArrayList<>();

//    private int totalHoldings;
//
//    private double totalBuyPrice;


}
