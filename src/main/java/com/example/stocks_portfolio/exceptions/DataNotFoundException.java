package com.example.stocks_portfolio.exceptions;



public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message){
        super(message);
    }
}
