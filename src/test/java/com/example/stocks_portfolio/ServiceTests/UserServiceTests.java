package com.example.stocks_portfolio.ServiceTests;

import com.example.stocks_portfolio.entities.User;
import com.example.stocks_portfolio.repositories.UserRepository;
import com.example.stocks_portfolio.service.UserService;
import com.example.stocks_portfolio.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp(){
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testIsUserPresent_Present(){
        long userId = 1;

        User user = new User(userId, "user1");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        boolean result = userService.isUserPresent(userId);

        assertTrue(result);

        verify(userRepository).findById(userId);
    }

    @Test
    public void testIsUserPresent_NotPresent(){
        long userId = 20;
        User user = new User(userId, "user1");

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        boolean result = userService.isUserPresent(userId);

        assertFalse(result);

        verify(userRepository).findById(userId);
    }
}
