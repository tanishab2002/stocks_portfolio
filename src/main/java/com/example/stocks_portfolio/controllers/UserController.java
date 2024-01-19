package com.example.stocks_portfolio.controllers;

import com.example.stocks_portfolio.DTO.UserDTO;
import com.example.stocks_portfolio.entities.User;
import com.example.stocks_portfolio.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public boolean getUserById(Long id){
        return userService.isUserPresent(id);
    }

    @PostMapping("/save")
    public UserDTO saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
}
