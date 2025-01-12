package com.tehila.coupons.dal;

import com.tehila.coupons.entities.CompanyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompaniesDal extends CrudRepository<CompanyEntity, Integer> {

    @Query("SELECT count(c.id) > 0 FROM CompanyEntity c WHERE name= :name")
    boolean existsByName(@Param("name") String name);

}
