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
    private Long id;

    @Column
    private String carPlate;

    @Column
    private String carModel;

    @Column
    private String carType;

    @Column
    private String entryDate;

    @Column
    private String exitDate;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    public Reservation() {
    }

    public Reservation(Long id, String carPlate, String carModel, String carType, String entryDate, String exitDate) {
        this.id = id;
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.carType = carType;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public Reservation(String carPlate, String carModel, String carType, String entryDate, String exitDate) {
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.carType = carType;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long reservationId) {
        this.id = reservationId;
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
}
