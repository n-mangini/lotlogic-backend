package app.model.dto;


public record ReservationAddForm(Long parkingId, String dni, String exitDate, String carPlate,
                                 String carModel, String carType) {
}
