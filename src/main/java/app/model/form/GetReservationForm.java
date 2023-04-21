package app.model.form;

public class GetReservationForm {
    private final Long userID;

    private final String entryDate;

    private final String exitDate;

    private final String carPlate;

    private final String carModel;

    private final String carType;

    public GetReservationForm(Long userID, String entryDate, String exitDate, String carPlate, String carModel, String carType) {
        this.userID = userID;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.carType = carType;
    }
}
