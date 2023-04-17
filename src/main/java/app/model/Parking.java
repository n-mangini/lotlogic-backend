package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

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

    @ManyToMany
    private List<User> users;

    public Parking() {
    }

    public Parking(long parkingId, String address, int carFee, int motorbikeFee, int truckFee) {
        this.parkingId = parkingId;
        this.address = address;
        this.carFee = carFee;
        this.motorbikeFee = motorbikeFee;
        this.truckFee = truckFee;
    }

    public Parking(String address, int carFee, int motorbikeFee, int truckFee) {
        this.address = address;
        this.carFee = carFee;
        this.motorbikeFee = motorbikeFee;
        this.truckFee = truckFee;
    }

    public long getParkingId() {
        return this.parkingId;
    }

    public void setParkingId(long parkingId) {
        this.parkingId = parkingId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCarFee() {
        return this.carFee;
    }

    public void setCarFee(int carFee) {
        this.carFee = carFee;
    }

    public int getMotorbikeFee() {
        return this.motorbikeFee;
    }

    public void setMotorbikeFee(int motorbikeFee) {
        this.motorbikeFee = motorbikeFee;
    }

    public int getTruckFee() {
        return this.truckFee;
    }

    public void setTruckFee(int truckFee) {
        this.truckFee = truckFee;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
