package com.tehila.coupons.logic;

import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dal.IPurchasesDal;
import com.tehila.coupons.dto.Coupon;
import com.tehila.coupons.dto.Purchase;
import com.tehila.coupons.dto.PurchaseDetails;
import com.tehila.coupons.entities.PurchaseEntity;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PurchasesLogic {

    private IPurchasesDal purchasesDal;
    @Autowired
    private CouponsLogic couponsLogic;

    @Autowired
    public PurchasesLogic(IPurchasesDal purchasesDal) {
        this.purchasesDal = purchasesDal;
    }

    public void createPurchase(Purchase purchase, int userId) throws ServerException {
        purchase.setCustomerId(userId);
        validatePurchase(purchase);
        this.couponsLogic.decreaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        PurchaseEntity purchaseEntity = convertPurchaseToPurchaseEntity(purchase);
        this.purchasesDal.save(purchaseEntity);
    }

    private PurchaseEntity convertPurchaseToPurchaseEntity(Purchase purchase) {
        PurchaseEntity purchaseEntity = new PurchaseEntity(purchase.getId(), purchase.getCustomerId(), purchase.getCouponId(), purchase.getAmount(), purchase.getTimestamp());
        return purchaseEntity;
    }

    public List<Purchase> getPurchases(String userType) throws ServerException {
        if(!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        List<PurchaseEntity> purchaseEntities = (List<PurchaseEntity>) purchasesDal.findAll();
        List<Purchase> purchases = new ArrayList<>();
        for(PurchaseEntity purchaseEntity : purchaseEntities) {
            purchases.add(convertPurchaseEntityToPurchase(purchaseEntity));
        }
        return purchases;
    }

    private Purchase convertPurchaseEntityToPurchase(PurchaseEntity purchaseEntity) {
        Purchase purchase = new Purchase(purchaseEntity.getId(), purchaseEntity.getCustomer().getId(), purchaseEntity.getCoupon().getId(), purchaseEntity.getAmount());
        return purchase;
    }

    public void deletePurchase(int id, String userType) throws ServerException {
        Purchase purchase = getPurchase(id);
        if(!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        this.couponsLogic.increaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        this.purchasesDal.deleteById(id);
    }

    public void updatePurchase(Purchase purchase, SuccessfulLoginDetails successfulLoginDetails) throws ServerException {
        if(purchase.getCustomerId() != successfulLoginDetails.getId() && !successfulLoginDetails.getUserType().equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        validatePurchase(purchase);
        PurchaseEntity purchaseBeforeUpdate = this.purchasesDal.findById(purchase.getId()).get();
        if (purchaseBeforeUpdate.getCoupon().getId() != purchase.getCouponId()) {
            throw new ServerException(ErrorType.INVALID_COUPON_ID);
        }
        if (purchaseBeforeUpdate.getAmount() > purchase.getAmount()) {
            int amountToIncrease = purchaseBeforeUpdate.getAmount() - purchase.getAmount();
            this.couponsLogic.increaseCouponAmount(amountToIncrease, purchase.getCouponId());
        } else if (purchase.getAmount() > purchaseBeforeUpdate.getAmount()) {
            int amountToDecrease = purchase.getAmount() - purchaseBeforeUpdate.getAmount();
            this.couponsLogic.decreaseCouponAmount(amountToDecrease, purchase.getCouponId());
        }
        PurchaseEntity purchaseEntity = convertPurchaseToPurchaseEntity(purchase);
        this.purchasesDal.save(purchaseEntity);
    }

    public Purchase getPurchase(int id) {
        Purchase purchase = convertPurchaseEntityToPurchase(purchasesDal.findById(id).get());
        return purchase;
    }

    public List<PurchaseDetails> getPurchasesByCustomerId(Integer customerId) throws ServerException {
        return this.purchasesDal.getPurchasesDetailsByCustomerId(customerId);
    }

    public List<Purchase> getPurchasesByCouponId(Integer couponId) throws ServerException {
        List<PurchaseEntity> purchaseEntities = purchasesDal.findPurchasesByCouponId(couponId);
        List<Purchase> purchases = new ArrayList<>();
        for(PurchaseEntity purchaseEntity : purchaseEntities) {
            purchases.add(convertPurchaseEntityToPurchase(purchaseEntity));
        }
        return purchases;
    }

    public void deletePurchasesOfExpiredCoupons() {
        this.purchasesDal.deletePurchasesOfExpiredCoupons();
    }

    private void validatePurchase(Purchase purchase) throws ServerException {

        if (purchase.getCustomerId() == null || purchase.getCustomerId() <= 0) {
            throw new ServerException(ErrorType.INVALID_CUSTOMER_ID, purchase.toString());
        }

        if (purchase.getCouponId() == null || purchase.getCouponId() <= 0) {
            throw new ServerException(ErrorType.INVALID_COUPON_ID, purchase.toString());
        }

        if (purchase.getAmount() == null) {
            throw new ServerException(ErrorType.INVALID_PURCHASES_AMOUNT, purchase.toString());
        }

        if (purchase.getAmount() < 1) {
            throw new ServerException(ErrorType.AMOUNT_OF_PURCHASES_LESS_THAN_ZERO, purchase.toString());
        }

        if(!isPurchaseTimestampValid(purchase)){
            throw new ServerException(ErrorType.INVALID_PURCHASE_TIMESTAMP, purchase.toString());
        }

        if (!isCouponAmountSufficient(purchase)) {
            throw new ServerException(ErrorType.NOT_ENOUGH_COUPONS, purchase.toString());
        }
    }

    private boolean isPurchaseTimestampValid(Purchase purchase) throws ServerException {
        Coupon coupon = this.couponsLogic.getCoupon(purchase.getCouponId());

        if (purchase.getTimestamp().after(coupon.getEndDate())) {
            return false;
        }
        return true;
    }

    private boolean isCouponAmountSufficient(Purchase purchase) throws ServerException {

        Coupon coupon = this.couponsLogic.getCoupon(purchase.getCouponId());

        if (coupon == null || coupon.getAmount() < purchase.getAmount()) {
            return false;
        }
        return true;
    }

}
