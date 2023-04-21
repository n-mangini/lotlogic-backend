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
    private int feePrice;

    @Column
    private String carType;

    public Fee() {
    }

    public Fee(long feeId, int feePrice, String carType) {
        this.feeId = feeId;
        this.feePrice = feePrice;
        this.carType = carType;
    }

    public Fee(int feePrice, String carType) {
        this.feePrice = feePrice;
        this.carType = carType;
    }

    public long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(long feeId) {
        this.feeId = feeId;
    }

    public int getFeePrice() {
        return this.feePrice;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setFeePrice(int fee) {
        this.feePrice = fee;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}

