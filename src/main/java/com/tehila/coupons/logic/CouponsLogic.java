package com.tehila.coupons.logic;

import com.tehila.coupons.dal.ICouponsDal;
import com.tehila.coupons.dto.Coupon;
import com.tehila.coupons.dto.CouponDetails;
import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.entities.CouponEntity;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CouponsLogic {

    private ICouponsDal couponsDal;
    @Autowired
    private CategoriesLogic categoriesLogic;
    @Autowired
    private PurchasesLogic purchasesLogic;
    private CompaniesLogic companiesLogic;


    @Autowired
    public CouponsLogic(ICouponsDal couponsDal, CompaniesLogic companiesLogic) {
        this.couponsDal = couponsDal;
        this.companiesLogic = companiesLogic;
    }

    public void createCoupon(Coupon coupon, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        validateCoupon(coupon, successfulLoginDetails);
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    private CouponEntity convertCouponToCouponEntity(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity(coupon.getId(), coupon.getTitle(), coupon.getDescription(), coupon.getPrice(), coupon.getCompanyId(), coupon.getCategoryId(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getImageURL());
        return couponEntity;
    }

    public List<CouponDetails> getCoupons() throws ServerException {
        return this.couponsDal.getCouponDetails();
    }

    private Coupon convertCouponEntityToCoupon(CouponEntity couponEntity) {
        Coupon coupon = new Coupon(couponEntity.getId(), couponEntity.getTitle(), couponEntity.getDescription(), couponEntity.getPrice(), couponEntity.getCompany().getId(), couponEntity.getCategory().getId(), couponEntity.getStartDate(), couponEntity.getEndDate(), couponEntity.getAmount(), couponEntity.getImageUrl());
        return coupon;
    }

    public void deleteCoupon(int id, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        Coupon coupon = convertCouponEntityToCoupon(couponsDal.findById(id).get());
        if(!isAuthorizedUser(coupon, successfulLoginDetails)) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }

        this.couponsDal.deleteById(id);
    }


    public void updateCoupon(Coupon coupon, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        validateCoupon(coupon, successfulLoginDetails);
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    public Coupon getCoupon(int id) throws ServerException {
        Coupon coupon = convertCouponEntityToCoupon(couponsDal.findById(id).get());
        return coupon;
    }

    public List<CouponDetails> getCouponsByCompanyId(Integer companyId) throws ServerException {
        return this.couponsDal.findCouponsByCompanyId(companyId);
    }

    void decreaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        Coupon coupon = getCoupon(couponId);
        int newAmount = coupon.getAmount() - purchaseAmount;
        coupon.setAmount(newAmount);
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    void increaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        Coupon coupon = getCoupon(couponId);
        int newAmount = coupon.getAmount() + purchaseAmount;
        coupon.setAmount(newAmount);
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    public List<CouponDetails> getCouponsUpToGivenPrice(float maxPrice) throws ServerException {
        return this.couponsDal.findCouponsUpToGivenPrice(maxPrice);
    }

    public List<CouponDetails> getCouponsByCategoryId(Integer categoryId) throws ServerException {
        return this.couponsDal.findCouponsByCategoryId(categoryId);
    }

    public void deleteExpiredCoupons() throws ServerException {
        this.purchasesLogic.deletePurchasesOfExpiredCoupons();
        this.couponsDal.deleteExpiredCoupons();
    }

    private void validateCoupon(Coupon coupon, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {

        if(!isAuthorizedUser(coupon, successfulLoginDetails)) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }

        if (coupon.getTitle() == null) {
            throw new ServerException(ErrorType.INVALID_COUPON_TITLE, coupon.toString());
        }

        if (coupon.getTitle().length() > 45) {
            throw new ServerException(ErrorType.COUPON_TITLE_TOO_LONG, coupon.toString());
        }

        if (coupon.getDescription() == null) {
            throw new ServerException(ErrorType.INVALID_COUPON_DESCRIPTION, coupon.toString());
        }

        if (coupon.getDescription().length() > 45) {
            throw new ServerException(ErrorType.COUPON_DESCRIPTION_TOO_LONG, coupon.toString());
        }

        if (coupon.getPrice() < 0.01) {
            throw new ServerException(ErrorType.COUPON_PRICE_LESS_THAN_ZERO, coupon.toString());
        }

        if (coupon.getStartDate() == null) {
            throw new ServerException(ErrorType.INVALID_COUPON_DATE, coupon.toString());
        }

        if (coupon.getEndDate() == null) {
            throw new ServerException(ErrorType.INVALID_COUPON_DATE, coupon.toString());
        }

        if (coupon.getCompanyId() == null || coupon.getCompanyId() <= 0) {
            throw new ServerException(ErrorType.INVALID_COMPANY_ID, coupon.toString());
        }

        if(!companiesLogic.isCompanyIdExist(coupon.getCompanyId())){
            throw new ServerException(ErrorType.COMPANY_NOT_EXIST, coupon.toString());
        }

        if(coupon.getCategoryId() == null || coupon.getCategoryId() <= 0){
            throw new ServerException(ErrorType.INVALID_CATEGORY_ID, coupon.toString());
        }

        if(!categoriesLogic.isCategoryIdExist(coupon.getCategoryId())){
            throw new ServerException(ErrorType.CATEGORY_NOT_EXIST, coupon.toString());
        }

        if (coupon.getCategoryId() <= 0) {
            throw new ServerException(ErrorType.INVALID_CATEGORY_ID, coupon.toString());
        }

        if (coupon.getAmount() < 1) {
            throw new ServerException(ErrorType.COUPON_AMOUNT_LESS_THAN_ZERO, coupon.toString());
        }

        if(coupon.getImageURL() != null && coupon.getImageURL().length() > 100){
            throw new ServerException(ErrorType.URL_TOO_LONG, coupon.toString());
        }
    }

    private boolean isAuthorizedUser(Coupon coupon, SuccessfulLoginDetails successfulLoginDetails) {
        if(successfulLoginDetails.getUserType().equals("Admin")) {
            return true;
        }
        if(successfulLoginDetails.getUserType().equals("Company") && coupon.getCompanyId().equals(successfulLoginDetails.getCompanyId())) {
            return true;
        }
        return false;
    }
}
