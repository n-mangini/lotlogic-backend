package app.model.dto;


public record ReservationAddForm(Long parkingId, String dni, int floor, String vehiclePlate,
                                 String vehicleModel, String vehicleType) {
}
