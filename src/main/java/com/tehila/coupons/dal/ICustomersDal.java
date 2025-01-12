package com.tehila.coupons.dal;

import com.tehila.coupons.dto.Customer;
import com.tehila.coupons.dto.CustomerDetails;
import com.tehila.coupons.dto.User;
import com.tehila.coupons.entities.CustomerEntity;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import com.tehila.coupons.utils.JdbcUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ICustomersDal extends CrudRepository<CustomerEntity, Integer> {

    @Query("SELECT new com.tehila.coupons.dto.CustomerDetails(c.id, c.name, c.user.username, c.amountOfKids, c.address, c.phone) " +
            "FROM CustomerEntity c WHERE c.id = :id")
    CustomerDetails getCustomerDetails(@Param("id") int id);

}
