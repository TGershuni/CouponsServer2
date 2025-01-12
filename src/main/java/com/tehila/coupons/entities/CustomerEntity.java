package com.tehila.coupons.entities;

import com.tehila.coupons.dto.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "amount_of_kids")
    private Integer amountOfKids;

    @Column(name = "phone")
    private String phone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    public CustomerEntity() {

    }

    public CustomerEntity(int id, String name, String address, Integer amountOfKids, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user = new UserEntity();
        this.user.setId(id);
    }

    public CustomerEntity(String name, String address, Integer amountOfKids, String phone) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user = new UserEntity();
        this.user.setId(id);
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
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
