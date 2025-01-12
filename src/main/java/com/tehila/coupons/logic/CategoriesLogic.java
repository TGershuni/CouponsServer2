package com.tehila.coupons.logic;

import com.tehila.coupons.dal.ICategoriesDal;
import com.tehila.coupons.dto.Category;
import com.tehila.coupons.entities.CategoryEntity;
import com.tehila.coupons.enums.ErrorType;
import com.tehila.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoriesLogic {

    private ICategoriesDal categoriesDal;

    @Autowired
    public CategoriesLogic(ICategoriesDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public void createCategory(Category category, String userType) throws ServerException {
        if (isCategoryNameExist(category.getName())) {
            throw new ServerException(ErrorType.CATEGORY_ALREADY_EXISTS, category.toString());
        }
        validateCategory(category, userType);
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        this.categoriesDal.save(categoryEntity);
    }

    private CategoryEntity convertCategoryToCategoryEntity(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity(category.getId(), category.getName());
        return categoryEntity;
    }

    public List<Category> getCategories() throws ServerException {
        List<CategoryEntity> categoryEntities = (List<CategoryEntity>) categoriesDal.findAll();
        List<Category> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntities) {
            categories.add(convertCategoryEntityToCategory(categoryEntity));
        }
        return categories;
    }

    private Category convertCategoryEntityToCategory(CategoryEntity categoryEntity) {
        Category category = new Category(categoryEntity.getId(), categoryEntity.getName());
        return category;
    }

    public void deleteCategory(int id, String userType) throws ServerException {
        if(!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }
        if (!isCategoryIdExist(id)) {
            throw new ServerException(ErrorType.CATEGORY_NOT_EXIST);
        }
        this.categoriesDal.deleteById(id);
    }

    public void updateCategory(Category category, String userType) throws ServerException {
        if (!isCategoryIdExist(category.getId())) {
            throw new ServerException(ErrorType.CATEGORY_NOT_EXIST, category.toString());
        }
        CategoryEntity categoryBeforeUpdate = this.categoriesDal.findById(category.getId()).get();
        if(categoryBeforeUpdate.getName().equals(category.getName())){
            if (isCategoryNameExist(category.getName())) {
                throw new ServerException(ErrorType.CATEGORY_ALREADY_EXISTS, category.toString());
            }
        }
        validateCategory(category, userType);
        CategoryEntity categoryEntity = convertCategoryToCategoryEntity(category);
        this.categoriesDal.save(categoryEntity);
    }

    public Category getCategory(int id) throws ServerException {
        Category category = convertCategoryEntityToCategory(categoriesDal.findById(id).get());
        if(category == null) {
            throw new ServerException(ErrorType.CATEGORY_NOT_EXIST);
        }
        return category;
    }

    boolean isCategoryNameExist(String categoryName) {
        return categoriesDal.existsByName(categoryName);
    }

    boolean isCategoryIdExist(int categoryId) {
        return this.categoriesDal.existsById(categoryId);
    }

    private void validateCategory(Category category, String userType) throws ServerException {

        if(!userType.equals("Admin")) {
            throw new ServerException(ErrorType.INVALID_ACTION);
        }

        if (category.getName() == null) {
            throw new ServerException(ErrorType.INVALID_CATEGORY_NAME, category.toString());
        }

        if (category.getName().length() > 45) {
            throw new ServerException(ErrorType.CATEGORY_NAME_TOO_LONG, category.toString());
        }
    }
}
