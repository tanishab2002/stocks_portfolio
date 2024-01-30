package com.example.stocks_portfolio.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import org.springframework.validation.annotation.Validated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long userId;

    private String name;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$")
    private String email;


    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;
}
