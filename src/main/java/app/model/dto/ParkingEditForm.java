package app.model.dto;

import app.model.Fee;
import app.model.Floor;

import java.util.List;

public record ParkingEditForm(String dni, String address, List<Floor> floors, List<Fee> fees) {
}
