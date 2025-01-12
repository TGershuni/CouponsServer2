package com.tehila.coupons.dto;


import org.springframework.stereotype.Component;

@Component
public class SuccessfulLoginDetails {
    private int id;
    private String userType;
    private Integer companyId;

    public SuccessfulLoginDetails(int id, String userType, Integer companyId) {
        this.id = id;
        this.userType = userType;
        this.companyId = companyId;
    }

    public SuccessfulLoginDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
