package app.model.dto;

public record GetReservationForm(Long userID, String entryDate, String exitDate, String carPlate, String carModel,
                                 String carType) {
}
