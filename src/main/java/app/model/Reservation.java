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
        private String vehicleFee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Reservation() {
    }

    public Reservation(Long id, int floor, String vehiclePlate, String vehicleModel, String vehicleFee, String entryDate, String exitDate) {
        this.id = id;
        this.floor = floor;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.vehicleFee = vehicleFee;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public Reservation(int floor, String vehiclePlate, String vehicleModel, String vehicleFee, String entryDate, String exitDate) {
        this.floor = floor;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.vehicleFee = vehicleFee;
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

    public void setVehiclePlate(String carPlate) {
        this.vehiclePlate = carPlate;
    }

    public String getVehicleModel() {
        return this.vehicleModel;
    }

    public void setVehicleModel(String carModel) {
        this.vehicleModel = carModel;
    }

    public String getVehicleFee() {
        return this.vehicleFee;
    }

    public void setVehicleFee(String carType) {
        this.vehicleFee = carType;
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

    public Parking getParking() {
        return this.parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
