package com.tehila.coupons.dal;

import com.tehila.coupons.dto.Coupon;
import com.tehila.coupons.dto.CouponDetails;
import com.tehila.coupons.entities.CouponEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICouponsDal extends CrudRepository<CouponEntity, Integer> {

    @Query("SELECT new com.tehila.coupons.dto.CouponDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageUrl) from CouponEntity co WHERE  co.company.id = :companyId")
    List<CouponDetails> findCouponsByCompanyId(@Param("companyId") Integer companyId);

    @Query("SELECT new com.tehila.coupons.dto.CouponDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageUrl) from CouponEntity co WHERE  co.category.id = :categoryId")
    List<CouponDetails> findCouponsByCategoryId(@Param("categoryId") Integer categoryId);

    @Query("SELECT new com.tehila.coupons.dto.CouponDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageUrl) from CouponEntity co WHERE co.price <= :maxPrice")
    List<CouponDetails> findCouponsUpToGivenPrice(@Param("maxPrice") float maxPrice);

    @Query("SELECT c FROM CouponEntity c WHERE c.category.id= :categoryId AND c.company.id = :companyId")
    List<CouponEntity> getCouponsByCategoryAndCompany(@Param("categoryId") int categoryId, @Param("companyId") int companyId);

    @Query("SELECT c FROM CouponEntity c WHERE c.company.id= :companyId AND c.price <= :price")
    List<CouponEntity> getCouponsByCompanyIdAndMaxPrice(@Param("companyId") int companyId, @Param("price") float maxPrice);

    @Query("SELECT new com.tehila.coupons.dto.CouponDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageUrl) from CouponEntity co")
    List<CouponDetails> getCouponDetails();

    @Modifying
    @Query("DELETE FROM CouponEntity WHERE end_date < CURDATE()")
    void deleteExpiredCoupons();
}
