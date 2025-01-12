package com.tehila.coupons.dto;

import java.sql.Date;

public class Coupon {

    private int id;

    private String title;

    private String description;

    private float price;

    private Integer companyId;

    private Integer categoryId;

    private Date startDate;

    private Date endDate;

    private int amount;

    private String imageURL;

    public Coupon() {

    }

    public Coupon(String title, String description, float price, Integer companyId, Integer categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public Coupon(int id, String title, String description, float price, Integer companyId, Integer categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "\n" + "Coupon{" +
                "id=" + id + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", companyId=" + companyId +
                ", categoryId=" + categoryId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
