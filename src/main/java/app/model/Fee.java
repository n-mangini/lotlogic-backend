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
    private String feeType;

    @Column
    private int feePrice;

    public Fee() {
    }

    public Fee(long feeId, String feeType, int feePrice) {
        this.feeId = feeId;
        this.feeType = feeType;
        this.feePrice = feePrice;
    }

    public Fee(String feeType, int feePrice) {
        this.feeType = feeType;
        this.feePrice = feePrice;
    }

    public long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(long feeId) {
        this.feeId = feeId;
    }

    public String getFeeType() {
        return this.feeType;
    }

    public int getFeePrice() {
        return this.feePrice;
    }

    public void setFeeType(String carType) {
        this.feeType = carType;
    }

    public void setFeePrice(int fee) {
        this.feePrice = fee;
    }
}

