package app.model.form;

import app.model.Floor;

import java.util.List;

public class ParkingAddForm {
    private final String dni;
    private final String address;
    private final int carFee;
    private final int motorbikeFee;
    private final int truckFee;
    private final List<Floor> floors;

    public ParkingAddForm(String dni, String address, int carFee, int motorbikeFee, int truckFee, List<Floor> floors) {
        this.dni = dni;
        this.address = address;
        this.carFee = carFee;
        this.motorbikeFee = motorbikeFee;
        this.truckFee = truckFee;
        this.floors = floors;
    }

    public String getDni() {
        return this.dni;
    }

    public String getAddress() {
        return this.address;
    }

    public int getCarFee() {
        return this.carFee;
    }

    public int getMotorbikeFee() {
        return this.motorbikeFee;
    }

    public int getTruckFee() {
        return this.truckFee;
    }

    public List<Floor> getFloors() {
        return this.floors;
    }
}
