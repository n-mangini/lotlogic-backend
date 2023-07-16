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
    private int floorId;

    @Column
    private String vehiclePlate;

    @Column
    private String vehicleModel;

    @Column
    private String entryDate;

    @Column
    private String exitDate;

    @Column
    private Long feeId;

    @Column
    private Double amount = 0.0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;
    private String parkingReservationAddress;

    public Reservation() {
    }

    public Reservation(Long id, int floorId, String vehiclePlate, String vehicleModel, Long vehicleFee, String entryDate, String exitDate) {
        this.id = id;
        this.floorId = floorId;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.feeId = vehicleFee;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public Reservation(int floorId, String vehiclePlate, String vehicleModel, Long vehicleFee, String entryDate, String exitDate, String parkingReservationAddress) {
        this.floorId = floorId;
        this.vehiclePlate = vehiclePlate;
        this.vehicleModel = vehicleModel;
        this.feeId = vehicleFee;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.parkingReservationAddress = parkingReservationAddress;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFloorId() {
        return this.floorId;
    }

    public void setFloorId(int floor) {
        this.floorId = floor;
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

    public Long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(Long vehicleFee) {
        this.feeId = vehicleFee;
    }

    public Parking getParking() {
        return this.parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getParkingReservationAddress() {
        return this.parkingReservationAddress;
    }

    public void setParkingReservationAddress(String parkingReservationAddress) {
        this.parkingReservationAddress = parkingReservationAddress;
    }
}
