package app.model.dto;

import app.model.User;

public record EmployeeAddForm(User user, Long parkingId) {
}
