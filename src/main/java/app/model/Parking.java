package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Parking {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private long id;

    @Column
    private String address;

    @Column
    private boolean active = true;

    @OneToMany(targetEntity = Floor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private List<Floor> floors;

    @OneToMany(targetEntity = Fee.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private List<Fee> fees;

    public Parking() {

    }

    public Parking(long id, String address, boolean active, List<Floor> floors, List<Fee> fees) {
        this.id = id;
        this.address = address;
        this.active = active;
        this.floors = floors;
        this.fees = fees;
    }

    public Parking(String address, List<Floor> floors, List<Fee> fees) {
        this.address = address;
        this.floors = floors;
        this.fees = fees;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Fee> getFees() {
        return this.fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }
}
