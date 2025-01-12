package com.tehila.coupons.dto;

public class CustomerDetails {

    private int id;

    private String customerName;

    private String userName;

    private int amountOfKids;

    private String address;

    private String phone;

    public CustomerDetails(int id, String customerName, String userName, int amountOfKids, String address, String phone) {
        this.id = id;
        this.customerName = customerName;
        this.userName = userName;
        this.amountOfKids = amountOfKids;
        this.address = address;
        this.phone = phone;
    }

    public CustomerDetails() {
    }

    public CustomerDetails(String customerName, String userName, int amountOfKids, String address, String phone) {
        this.customerName = customerName;
        this.userName = userName;
        this.amountOfKids = amountOfKids;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(int amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                ", customer name='" + customerName + '\'' +
                ", user name='" + userName + '\'' +
                ", amount of kids=" + amountOfKids +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
