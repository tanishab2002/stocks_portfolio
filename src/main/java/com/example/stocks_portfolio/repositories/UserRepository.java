package com.example.stocks_portfolio.repositories;

import com.example.stocks_portfolio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
