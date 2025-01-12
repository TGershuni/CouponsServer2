package com.tehila.coupons.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="coupons")
public class CouponEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name="price", nullable = false)
    private float price;

    @Column(name="start_date", nullable = false)
    private Date startDate;

    @Column(name="end_date", nullable = false)
    private Date endDate;

    @Column(name="amount", nullable = false)
    private int amount;

    @Column(name="image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchases;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;


    public CouponEntity() {

    }

    public CouponEntity(String title, String description, float price, Integer companyId, Integer categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.company = new CompanyEntity();
        this.company.setId(companyId);
        this.category = new CategoryEntity();
        this.category.setId(categoryId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageUrl = imageURL;
    }

    public CouponEntity(int id, String title, String description, float price, Integer companyId, Integer categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.company = new CompanyEntity();
        this.company.setId(companyId);
        this.category = new CategoryEntity();
        this.category.setId(categoryId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageUrl = imageURL;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\n" + "Coupon{" +
                "id=" + id + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", companyId=" + company.getId() +
                ", categoryId=" + category.getId() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", imageURL='" + imageUrl + '\'' +
                '}';
    }

}
