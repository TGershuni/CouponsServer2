package com.tehila.coupons.controllers;
import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.Category;
import com.tehila.coupons.exceptions.ServerException;
import com.tehila.coupons.logic.CategoriesLogic;
import com.tehila.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoriesLogic categoriesLogic;
    private SuccessfulLoginDetails successfulLoginDetails;

    @Autowired
    public CategoriesController(CategoriesLogic categoriesLogic, SuccessfulLoginDetails successfulLoginDetails) {
        this.categoriesLogic = categoriesLogic;
        this.successfulLoginDetails = successfulLoginDetails;
    }

    // "name": "Food"
    @PostMapping
    public void createCategory(@RequestHeader("Authorization") String token, @RequestBody Category category) throws Exception{
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.categoriesLogic.createCategory(category, successfulLoginDetails.getUserType());
    }

    @GetMapping()
    public List<Category> getCategories() throws ServerException{
        return this.categoriesLogic.getCategories();
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@RequestHeader("Authorization") String token, @PathVariable("categoryId") int categoryId) throws Exception{
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.categoriesLogic.deleteCategory(categoryId, successfulLoginDetails.getUserType());
    }

    @PutMapping
    public void updateCategory(@RequestHeader("Authorization") String token, @RequestBody Category category) throws Exception{
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.categoriesLogic.updateCategory(category, successfulLoginDetails.getUserType());
    }

    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") int categoryId) throws ServerException {
        return this.categoriesLogic.getCategory(categoryId);
    }
}
