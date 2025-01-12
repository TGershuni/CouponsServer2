package com.tehila.coupons.controllers;


import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.Company;
import com.tehila.coupons.exceptions.ServerException;
import com.tehila.coupons.logic.CompaniesLogic;
import com.tehila.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private CompaniesLogic companiesLogic;
    private SuccessfulLoginDetails successfulLoginDetails;

    @Autowired
    public CompaniesController(CompaniesLogic companiesLogic) {
        this.companiesLogic = companiesLogic;
    }

    // "name": "El Al",
    //  "address": "Lod",
    //  "phone": "00005555"
    @PostMapping
    public void createCompany(@RequestHeader("Authorization") String token, @RequestBody Company company) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.companiesLogic.createCompany(company, successfulLoginDetails.getUserType());
    }

    @GetMapping()
    public List<Company> getCompanies() throws ServerException {
        return this.companiesLogic.getCompanies();
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@RequestHeader("Authorization") String token, @PathVariable("companyId") int companyId) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.companiesLogic.deleteCompany(companyId, successfulLoginDetails.getUserType());
    }

    @PutMapping
    public void updateCompany(@RequestHeader("Authorization") String token, @RequestBody Company company) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.companiesLogic.updateCompany(company, successfulLoginDetails.getUserType());
    }

    @GetMapping("/{companyId}")
    public Company getCompany(@PathVariable("companyId") int companyId) throws ServerException {
        return this.companiesLogic.getCompany(companyId);
    }
}
