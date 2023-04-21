package app.model.form;


public class ReservationAddForm {
    private final Long parkingId;
    private final String dni;
    private final String entryDate;
    private final String exitDate;
    private final String carPlate;
    private final String carModel;
    private final String carType;

    public ReservationAddForm(Long parkingId, String dni, String entryDate, String exitDate, String carPlate, String carModel, String carType) {
        this.parkingId = parkingId;
        this.dni = dni;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.carType = carType;
    }

    public Long getParkingId() {
        return this.parkingId;
    }

    public String getDni() {
        return this.dni;
    }

    public String getEntryDate() {
        return this.entryDate;
    }

    public String getExitDate() {
        return this.exitDate;
    }

    public String getCarPlate() {
        return this.carPlate;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public String getCarType() {
        return this.carType;
    }
}
