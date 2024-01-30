package com.example.stocks_portfolio.service;


import com.example.stocks_portfolio.DTO.UserDTO;
import com.example.stocks_portfolio.entities.User;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;

public interface UserService {

    boolean isUserPresent(Long id);

    UserDTO saveUser(User user) throws DataNotFoundException;
}
