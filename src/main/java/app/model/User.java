package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private Long userId;

    @Column(length = 9)
    private String dni;

    @Column(length = 46)
    private String firstName;

    @Column(length = 46)
    private String lastName;

    @Column(length = 16)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(Long userId, String dni, String firstName, String lastName, String password) {
        this.userId = userId;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(String dni, String firstName, String lastName, String password) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public boolean isOwner() {
        return this.role.equals(UserRole.OWNER);
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getDni() {
        return this.dni;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
