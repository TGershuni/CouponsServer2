package com.tehila.coupons.dal;

import com.tehila.coupons.entities.UserEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDal extends CrudRepository<UserEntity, Integer> {

    @Query("FROM UserEntity WHERE username = :username and password = :password")
    UserEntity login(@Param("username") String username, @Param("password") String password);

    @Query("SELECT count(u.id)>0 FROM UserEntity u WHERE username = :username")
    boolean getUserByUsername(@Param("username") String username);

    @Query("FROM UserEntity WHERE company_id = :company_id")
    List<UserEntity> getUsersByCompanyId(@Param("company_id") Integer companyId);

}