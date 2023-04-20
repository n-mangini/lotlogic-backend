package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Floor {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private long floorId;

    @Column
    private int slotsNumber;

    @Column
    private boolean isEnabled = true;

    public Floor() {
    }

    public Floor(long floorId, int slotsNumber) {
        this.floorId = floorId;
        this.slotsNumber = slotsNumber;
    }

    public Floor(int slotsNumber) {
        this.slotsNumber = slotsNumber;
    }

    public long getFloorId() {
        return this.floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    public int getSlotsNumber() {
        return this.slotsNumber;
    }

    public void setSlotsNumber(int slotsNumber) {
        this.slotsNumber = slotsNumber;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
