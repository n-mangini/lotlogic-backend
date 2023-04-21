package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private Long id;

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

    @OneToMany(targetEntity = Parking.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Parking> parkings;

    @Column
    private boolean active = true;

    public User() {
    }

    public User(Long id, String dni, String firstName, String lastName, String password) {
        this.id = id;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.parkings = new ArrayList<>();
    }

    public User(String dni, String firstName, String lastName, String password) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.parkings = new ArrayList<>();
    }

    public User(String dni, String password, UserRole role) {
        this.dni = dni;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Parking> getParkings() {
        return this.parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
