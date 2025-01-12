package com.tehila.coupons.controllers;

import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.Purchase;
import com.tehila.coupons.dto.PurchaseDetails;
import com.tehila.coupons.exceptions.ServerException;
import com.tehila.coupons.logic.PurchasesLogic;
import com.tehila.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private PurchasesLogic purchasesLogic;
    private SuccessfulLoginDetails successfulLoginDetails;

    @Autowired
    public PurchasesController(PurchasesLogic purchasesLogic) {
        this.purchasesLogic = purchasesLogic;
    }

    @GetMapping("/{purchaseId}")
    public Purchase getPurchase(@PathVariable("purchaseId") int purchaseId) throws ServerException {
        return this.purchasesLogic.getPurchase(purchaseId);
    }

    //  "amount": 2,
    //  "companyId": 3,
    //  "couponId": 1,
    //  "customerId": 8
    @PostMapping
    public void createPurchase(@RequestHeader("Authorization") String token, @RequestBody Purchase purchase) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        purchase.setTimestamp(Timestamp.from(Instant.now()));
        this.purchasesLogic.createPurchase(purchase, successfulLoginDetails.getId());
    }

    @GetMapping()
    public List<Purchase> getPurchases(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchases(successfulLoginDetails.getUserType());
    }

    @GetMapping("/byCustomerId")
    public List<PurchaseDetails> getPurchasesByCustomerId(@RequestParam("customerId") Integer customerId) throws ServerException {
        return this.purchasesLogic.getPurchasesByCustomerId(customerId);
    }

    @GetMapping("/byCouponId")
    public List<Purchase> getPurchasesByCouponId(@RequestParam("couponId") Integer couponId) throws ServerException {
        return this.purchasesLogic.getPurchasesByCouponId(couponId);
    }

    @DeleteMapping("/{purchaseId}")
    public void deletePurchase(@RequestHeader("Authorization") String token, @PathVariable("purchaseId") int purchaseId) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.purchasesLogic.deletePurchase(purchaseId, successfulLoginDetails.getUserType());
    }

    @PutMapping
    public void updatePurchase(@RequestHeader("Authorization") String token, @RequestBody Purchase purchase) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        purchase.setTimestamp(Timestamp.from(Instant.now()));
        this.purchasesLogic.updatePurchase(purchase, successfulLoginDetails);
    }
}
