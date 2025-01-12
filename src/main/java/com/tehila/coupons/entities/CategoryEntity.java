package com.tehila.coupons.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
public class CategoryEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name="name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CouponEntity> coupons;

    public CategoryEntity() {
    }

    public CategoryEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<CouponEntity> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponEntity> coupons) {
        this.coupons = coupons;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\n" + "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
