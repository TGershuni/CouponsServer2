package com.tehila.coupons.dto;

import java.sql.Timestamp;

public class PurchaseDetails {

    private int id;

    private Integer couponId;

    private int amount;

    private Timestamp timestamp;

    private float totalPrice;

    private String title;

    private String description;

    private String imageURL;

    public PurchaseDetails(int id, Integer couponId, int amount, java.util.Date timestamp, float totalPrice, String title, String description, String imageURL) {
        this.id = id;
        this.couponId = couponId;
        this.amount = amount;
        this.timestamp = new java.sql.Timestamp(timestamp.getTime());
        this.totalPrice = totalPrice;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public Integer getCouponId() {
        return couponId;
    }


    public int getAmount() {
        return amount;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }


    public float getTotalPrice() {
        return totalPrice;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "PurchaseDetails{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", totalPrice=" + totalPrice +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
