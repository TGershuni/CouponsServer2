package com.tehila.coupons.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="purchases")
public class PurchaseEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponEntity coupon;

    @ManyToOne(fetch = FetchType.EAGER)
    private CompanyEntity company;

    public PurchaseEntity() {

    }

    public PurchaseEntity(Integer customerId, Integer couponId, Integer amount, Timestamp timestamp) {
        this.customer = new CustomerEntity();
        this.customer.setId(customerId);
        this.coupon = new CouponEntity();
        this.coupon.setId(couponId);
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public PurchaseEntity(int id, Integer customerId, Integer couponId, Integer amount, Timestamp timestamp) {
        this.id = id;
        this.customer = new CustomerEntity();
        this.customer.setId(customerId);
        this.coupon = new CouponEntity();
        this.coupon.setId(couponId);
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "\n" + "Purchase{" +
                "id=" + id +
                ", customerId=" + customer.getId() +
                ", couponId=" + coupon.getId() +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }

}
