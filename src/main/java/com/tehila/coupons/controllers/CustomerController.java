package com.tehila.coupons.controllers;

import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.Customer;
import com.tehila.coupons.dto.CustomerDetails;
import com.tehila.coupons.logic.CustomersLogic;
import com.tehila.coupons.logic.UsersLogic;
import com.tehila.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomersLogic customersLogic;
    private SuccessfulLoginDetails successfulLoginDetails;
    private UsersLogic usersLogic;

    @Autowired
    public CustomerController(CustomersLogic customersLogic, UsersLogic usersLogic) {
        this.customersLogic = customersLogic;
        this.usersLogic = usersLogic;
    }

    //  "name": "TehilaCustomer",
    //  "address": "Haifa",
    //  "amountOfKids": 1,
    //  "phone": "0527174423",
    //  "user": {
    //    "username": "T22344589@gmail.com",
    //    "password": "123456",
    //    "userType": "Customer",
    //    "companyId": null
    //  }
    @PostMapping
    public void createCustomer(@RequestBody Customer customer) throws Exception {
        this.customersLogic.createCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@RequestHeader("Authorization") String token, @PathVariable("customerId") int customerId) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.customersLogic.deleteCustomer(customerId, successfulLoginDetails);
    }

    @PutMapping
    public void updateCustomer(@RequestHeader("Authorization") String token, @RequestBody Customer customer) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        customer.setId(successfulLoginDetails.getId());
        customer.getUser().setId(successfulLoginDetails.getId());
        customer.getUser().setUserType("Customer");
        customer.getUser().setCompanyId(null);
        this.customersLogic.updateCustomer(customer);
        this.usersLogic.updateUser(customer.getUser());
    }

    @GetMapping("/{customer_id}")
    public CustomerDetails getCustomer(@RequestHeader("Authorization") String token, @PathVariable("customer_id") int customerId) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        return this.customersLogic.getCustomer(customerId, successfulLoginDetails);
    }

}
