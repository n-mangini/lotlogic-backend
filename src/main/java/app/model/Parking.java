package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking")
public class Parking {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private long parkingId;

    @Column
    private String address;

    @Column
    private int carFee;

    @Column
    private int motorbikeFee;

    @Column
    private int truckFee;

    @OneToMany
    private List<Floor> floors;

    @Column
    private boolean active = true;

    public Parking() {

    }

    public Parking(long parkingId, String address, int carFee, int motorbikeFee, int truckFee) {
        this.parkingId = parkingId;
        this.address = address;
        this.carFee = carFee;
        this.motorbikeFee = motorbikeFee;
        this.truckFee = truckFee;
        this.floors = new ArrayList<>();
    }

    public Parking(String address, int carFee, int motorbikeFee, int truckFee) {
        this.address = address;
        this.carFee = carFee;
        this.motorbikeFee = motorbikeFee;
        this.truckFee = truckFee;
        this.floors = new ArrayList<>();
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

    public int getCarFee() {
        return this.carFee;
    }

    public int getMotorbikeFee() {
        return this.motorbikeFee;
    }

    public int getTruckFee() {
        return this.truckFee;
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

    public void setCarFee(int carFee) {
        this.carFee = carFee;
    }

    public void setMotorbikeFee(int motorbikeFee) {
        this.motorbikeFee = motorbikeFee;
    }

    public void setTruckFee(int truckFee) {
        this.truckFee = truckFee;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
