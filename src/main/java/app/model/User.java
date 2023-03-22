package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "dni", length = 9)
    private String dni;

    @Column(name = "first_name", length = 46)
    private String firstName;

    @Column(name = "last_name", length = 46)
    private String lastName;

    @Column(name = "password", length = 16)
    private String password;

    @Transient
    private boolean isOwner = false;

    public User() {
    }

    public User(Integer userId, String dni, String firstName, String lastName, String password) {
        this.userId = userId;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isOwner = isOwner();
    }

    public User(String dni, String firstName, String lastName, String password) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isOwner = isOwner();
    }

    public boolean isOwner() {
        return this.password.length() > 4;
    }

    public int getUserId() {
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
}
