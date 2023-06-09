package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table
public class Reservation {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private Long id;

    @Column
    private int floor;

    @Column
    private String vehiclePlate;

    @Column
    private String vehicleModel;

    @Column
    private String entryDate;

    @Column
    private String exitDate;

    @Column
    private Long fee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Reservation() {
    }

    public Reservation(Long id, int floor, String vehiclePlate, String vehicleModel, Long vehicleFee, String entryDate, String exitDate) {
        this.id = id;
        this.floor = floor;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.fee = vehicleFee;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public Reservation(int floor, String vehiclePlate, String vehicleModel, Long vehicleFee, String entryDate, String exitDate) {
        this.floor = floor;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.fee = vehicleFee;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFloor() {
        return this.floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getVehiclePlate() {
        return this.vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
    }

    public Long getFee() {
        return this.fee;
    }

    public void setFee(Long vehicleFee) {
        this.fee = vehicleFee;
    }

    public Parking getParking() {
        return this.parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
