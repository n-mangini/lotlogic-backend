package app.model.projection;

import app.model.Fee;
import app.model.Floor;
import app.model.User;

import java.util.List;

public interface ParkingProjection {
    Long getId();

    String getAddress();

    boolean isActive();

    List<Floor> getFloors();

    List<Fee> getFees();
}
