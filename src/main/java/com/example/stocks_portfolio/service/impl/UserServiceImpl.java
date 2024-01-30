package com.example.stocks_portfolio.service.impl;

import com.example.stocks_portfolio.DTO.UserDTO;
import com.example.stocks_portfolio.entities.User;
import com.example.stocks_portfolio.exceptions.DataNotFoundException;
import com.example.stocks_portfolio.repositories.UserRepository;
import com.example.stocks_portfolio.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean isUserPresent(Long id) {
        Optional<User> user = userRepository.findById(id);
//        System.out.println(user);
        return user.isPresent();
    }

    @Override
    public UserDTO saveUser(User user) throws DataNotFoundException {

        if(user == null){
            throw new DataNotFoundException("Cannot be empty");
        }
        userRepository.save(user);

        return convertToUserDTO(user);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO(user.getUserId(), user.getName(), user.getEmail(), user.getContact());

        return userDTO;
    }


}
