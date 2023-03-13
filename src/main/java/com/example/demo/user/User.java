package com.example.demo.user;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private int id;
    private String dni;
    private String name;
    private String surname;
    private String password;

    public User() {
    }

    public User(String dni, String name, String surname, String password) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
}
