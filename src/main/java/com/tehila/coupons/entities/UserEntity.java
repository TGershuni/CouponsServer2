package com.tehila.coupons.entities;

import com.tehila.coupons.dto.Company;

import javax.persistence.*;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private CustomerEntity customer;

    public UserEntity() {
    }

    public UserEntity(int id, String username, String password, String userType, Integer companyId) {
        this(username, password, userType, companyId);
        this.id = id;
    }

    public UserEntity(String username, String password, String userType, Integer companyId) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        if(userType.equals("Company")) {
            this.company = new CompanyEntity();
            this.company.setId(companyId);
        }
    }

    public UserEntity(int id, String userType, Integer companyId) {
        this.id = id;
        this.userType = userType;
        if(userType.equals("Company")) {
            this.company = new CompanyEntity();
            this.company.setId(companyId);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "\n" + "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", companyId=" + company.getId() +
                '}';
    }
}
