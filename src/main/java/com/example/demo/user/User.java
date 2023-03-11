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
    @Transient
    private boolean isOwner;

    public User() {
    }

    public User(int id, String dni, String name, String surname, String password, boolean isOwner) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isOwner = isOwner;
    }

    public User(String dni, String name, String surname, String password) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isOwner = isOwner();
    }

    public String getDni() {
        return this.dni;
    }

    public boolean isOwner() {
        return getPassword().length() > 4;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", isOwner=" + isOwner +
                '}';
    }
}
