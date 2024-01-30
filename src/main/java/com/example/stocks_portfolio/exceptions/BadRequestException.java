package com.example.stocks_portfolio.exceptions;


public class BadRequestException extends Exception{

    public BadRequestException(String message){
        super(message);
    }
}
