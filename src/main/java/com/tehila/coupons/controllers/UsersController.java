package com.tehila.coupons.controllers;

import com.tehila.coupons.dto.SuccessfulLoginDetails;
import com.tehila.coupons.dto.UserLoginData;
import com.tehila.coupons.dto.User;
import com.tehila.coupons.logic.UsersLogic;
import com.tehila.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private SuccessfulLoginDetails successfulLoginDetails;
    private UsersLogic usersLogic;

    @Autowired
    public UsersController(UsersLogic usersLogic) {
        this.usersLogic = usersLogic;
    }

    //  "username": "t0524@gmail.com",
    //  "password": "22334455",
    //  "userType": "Company",
    //  "companyId": 1
    @PostMapping
    public void createUser(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        if (successfulLoginDetails.getUserType().equals("Admin")) {
            this.usersLogic.createUser(user);
        }
    }

    @GetMapping()
    public List<User> getUsersListByAdmin(@RequestHeader("Authorization") String token) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        return this.usersLogic.getUsers(successfulLoginDetails.getUserType());
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@RequestHeader("Authorization") String token, @PathVariable("userId") int userId) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        this.usersLogic.deleteUser(userId, successfulLoginDetails);
    }

    @PutMapping
    public void updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        successfulLoginDetails = JWTUtils.decodeJWT(token);
        if (successfulLoginDetails.getUserType().equals("Admin")) {
            this.usersLogic.updateUser(user);
            return;
        }
        user.setId(successfulLoginDetails.getId());
        user.setUserType(successfulLoginDetails.getUserType());
        user.setCompanyId(successfulLoginDetails.getCompanyId());
        this.usersLogic.updateUser(user);
    }

    // admin user:
    // "username": "t05271744244@gmail.com",
    // "password": "22334455"
    @PostMapping("/login")
    public String login(@RequestBody UserLoginData userLoginData) throws Exception {
        return usersLogic.login(userLoginData);
    }

}
