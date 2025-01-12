package com.tehila.coupons.dal;

import com.tehila.coupons.entities.CategoryEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriesDal extends CrudRepository<CategoryEntity, Integer> {

    @Query("SELECT COUNT(c.id) > 0 FROM CategoryEntity c WHERE name = :name")
    boolean existsByName(@Param("name") String name);

}
