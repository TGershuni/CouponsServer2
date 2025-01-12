package com.tehila.coupons.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class Purchase {

    private int id;

    private Integer customerId;

    private Integer couponId;

    private Integer amount;

    private Timestamp timestamp;

    public Purchase() {

    }

    public Purchase(Integer customerId, Integer couponId, Integer amount) {
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
    }

    public Purchase(int id, Integer customerId, Integer couponId, Integer amount) {
        this.id = id;
        this.customerId = customerId;
        this.couponId = couponId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }


    public Integer getCouponId() {
        return couponId;
    }


    public Integer getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "\n" + "Purchase{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}

