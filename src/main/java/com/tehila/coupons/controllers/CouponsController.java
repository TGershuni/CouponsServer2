package com.tehila.coupons.controllers;

import com.tehila.coupons.dto.CouponDetails;
import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.Coupon;
import com.tehila.coupons.exceptions.ServerException;
import com.tehila.coupons.logic.CouponsLogic;
import com.tehila.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private CouponsLogic couponsLogic;
    private SuccessfulLoginDetails successfulLoginDetails;

    @Autowired
    public CouponsController(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    //   "title": "Flights to USA",
    //  "description": "50% off for all flights to USA",
    //  "price": 1000,
    //  "startDate": "2024-09-24T00:00:00.000Z",
    //  "endDate": "2024-12-31T23:59:59.999Z",
    //  "amount": 100,
    //  "imageUrl": "url of some image",
    //    "companyId": 3,
    //  "categoryId": 2
    @PostMapping
    public void createCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.couponsLogic.createCoupon(coupon, successfulLoginDetails);
    }

    @GetMapping()
    public List<CouponDetails> getCoupons() throws ServerException {
        return this.couponsLogic.getCoupons();
    }

    @DeleteMapping("/{couponId}")
    public void deleteCoupon(@RequestHeader("Authorization") String token, @PathVariable("couponId") int couponId) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.couponsLogic.deleteCoupon(couponId, successfulLoginDetails);
    }

    @PutMapping
    public void updateCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.couponsLogic.updateCoupon(coupon, successfulLoginDetails);
    }

    @GetMapping("/{couponId}")
    public Coupon getCoupon(@PathVariable("couponId") int couponId) throws ServerException {
        return this.couponsLogic.getCoupon(couponId);
    }

    @GetMapping("/byCompanyId")
    public List<CouponDetails> getCouponsByCompanyId(@RequestParam("companyId") Integer companyId) throws ServerException {
        return this.couponsLogic.getCouponsByCompanyId(companyId);
    }

    @GetMapping("/upToGivenPrice")
    public List<CouponDetails> getCouponsUpToGivenPrice(@RequestParam("maxPrice") float maxPrice) throws ServerException {
        return this.couponsLogic.getCouponsUpToGivenPrice(maxPrice);
    }

    @GetMapping("/byCategoryId")
    public List<CouponDetails> getCouponsByCategoryId(@RequestParam("categoryId") Integer categoryId) throws ServerException {
        return this.couponsLogic.getCouponsByCategoryId(categoryId);
    }


}
