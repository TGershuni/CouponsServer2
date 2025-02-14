package com.tehila.coupons.dto;

public class Customer {

    private int id;

    private String name;

    private String address;

    private Integer amountOfKids;

    private String phone;

    private User user;

    public Customer() {

    }

    public Customer(int id, String name, String address, Integer amountOfKids, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
    }

    public Customer(String name, String address, Integer amountOfKids, String phone, User user) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(int amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public String toString() {
        return "\n" + "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", amountOfKids=" + amountOfKids +
                ", phone='" + phone + '\'' +
                '}';
    }
}
