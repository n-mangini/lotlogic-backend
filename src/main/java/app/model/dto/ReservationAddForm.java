package app.model.dto;


public record ReservationAddForm(Long parkingId, String employeeDni, String vehiclePlate,
                                 String vehicleModel, Long floor, Long fee) {
}
