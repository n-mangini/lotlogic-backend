package app.user;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(generator = "user_sequence", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", initialValue = 50, allocationSize = 1)
    private Integer userId;
    private String dni;
    private String name;
    private String surname;
    private String password;
    @Transient
    private boolean isOwner = false;

    public User() {
    }

    public User(Integer userId, String dni, String name, String surname, String password) {
        this.userId = userId;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isOwner = isOwner();
    }

    public User(String dni, String name, String surname, String password) {
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isOwner = isOwner();
    }

    private boolean isOwner() {
        return this.password.length() > 4;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getDni() {
        return this.dni;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
