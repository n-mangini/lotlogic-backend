package app.model.form;

import app.model.Fee;
import app.model.Floor;

import java.util.List;

public class ParkingEditForm {
    private final String dni;
    private final String address;
    private final List<Floor> floors;
    private final List<Fee> fees;

    public ParkingEditForm(String dni, String address, List<Floor> floors, List<Fee> fees) {
        this.dni = dni;
        this.address = address;
        this.floors = floors;
        this.fees = fees;
    }

    public String getDni() {
        return this.dni;
    }

    public String getAddress() {
        return this.address;
    }

    public List<Fee> getFees() {
        return this.fees;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }
}
