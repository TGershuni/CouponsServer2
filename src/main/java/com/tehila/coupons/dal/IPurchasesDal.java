package com.tehila.coupons.dal;

import com.tehila.coupons.dto.PurchaseDetails;
import com.tehila.coupons.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchasesDal extends CrudRepository<PurchaseEntity, Integer> {

    @Query("FROM PurchaseEntity WHERE customer_id = :customer_id")
    List<PurchaseEntity> findPurchasesByCustomerId(@Param("customer_id") Integer customerId);

    @Query("FROM PurchaseEntity WHERE coupon_id = :coupon_id")
    List<PurchaseEntity> findPurchasesByCouponId(@Param("coupon_id") Integer couponId);

    @Query("SELECT new com.tehila.coupons.dto.PurchaseDetails(p.id, p.coupon.id, p.amount, p.timestamp, " +
            "p.coupon.price * p.amount, p.coupon.title, p.coupon.description, p.coupon.imageUrl) " +
            "FROM PurchaseEntity p WHERE p.customer.id = :customerId")
    List<PurchaseDetails> getPurchasesDetailsByCustomerId(@Param("customerId") Integer customerId);

    @Query("FROM PurchaseEntity p WHERE p.coupon.company.id = :companyId")
    List<PurchaseEntity> findPurchasesByCompanyIdOfCoupon(@Param("companyId") Integer companyId);

    @Query("FROM PurchaseEntity p WHERE p.coupon.category.id = :categoryId")
    List<PurchaseEntity> findPurchasesByCategoryIdOfCoupon(@Param("categoryId") Integer categoryId);

    @Modifying
    @Query("DELETE FROM PurchaseEntity p WHERE p.coupon.endDate < CURRENT_DATE")
    void deletePurchasesOfExpiredCoupons();

}
