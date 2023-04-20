package app.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Fee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column
    private long feeId;

    @Column
    private int feeType;

    @Column
    private String carType;

    public Fee() {
    }

    public Fee(long feeId, int feeType, String carType) {
        this.feeId = feeId;
        this.feeType = feeType;
        this.carType = carType;
    }

    public Fee(int feeType, String carType) {
        this.feeType = feeType;
        this.carType = carType;
    }

    public long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(long feeId) {
        this.feeId = feeId;
    }

    public int getFeeType() {
        return this.feeType;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setFeeType(int fee) {
        this.feeType = fee;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}

