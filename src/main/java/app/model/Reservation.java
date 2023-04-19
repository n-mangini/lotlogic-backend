package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table
public class Reservation {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private Long reservationId;

    @Column
    private String entryDate;

    @Column
    private String exitDate;

    @Column
    private String carPlate;

    @Column
    private String carModel;

    @Column
    private String carType;


    public Reservation() {
    }

    public Reservation(Long reservationId, String entryDate, String exitDate, String carPlate, String carModel, String carType) {
        this.reservationId = reservationId;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.carType = carType;
    }

    public Reservation(String entryDate, String exitDate, String carPlate, String carModel, String carType) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.carType = carType;
    }

    public Long getReservationId() {
        return this.reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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

    public String getCarPlate() {
        return this.carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
