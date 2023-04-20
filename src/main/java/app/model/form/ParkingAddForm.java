package app.model.form;

import app.model.Fee;
import app.model.Floor;

import java.util.ArrayList;
import java.util.List;

public class ParkingAddForm {
    private final String dni;
    private final String address;
    private List<Floor> floors;
    private List<Fee> fees;

    public ParkingAddForm(String dni, String address) {
        this.dni = dni;
        this.address = address;
        this.floors = new ArrayList<>();
        this.fees = new ArrayList<>();
    }

    public String getDni() {
        return this.dni;
    }

    public String getAddress() {
        return this.address;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }

    public List<Fee> getFees() {
        return this.fees;
    }
}
