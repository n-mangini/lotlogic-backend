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
    private long parkingId;

    @Column
    private String address;

    @OneToMany
    private List<Floor> floors;

    @OneToMany
    private List<Fee> fees;

    @Column
    private boolean active = true;

    public Parking() {

    }

    public Parking(long parkingId, String address) {
        this.parkingId = parkingId;
        this.address = address;
        this.floors = new ArrayList<>();
        this.fees = new ArrayList<>();
    }

    public Parking(String address) {
        this.address = address;
        this.floors = new ArrayList<>();
        this.fees = new ArrayList<>();
    }

    public long getParkingId() {
        return this.parkingId;
    }

    public String getAddress() {
        return this.address;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }

    public List<Fee> getFees() {
        return this.fees;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setParkingId(long parkingId) {
        this.parkingId = parkingId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
