package com.app.restoland.serviceImpl;

import com.app.restoland.JWT.JwtFilter;
import com.app.restoland.POJO.Category;
import com.app.restoland.constants.RestoConstants;
import com.app.restoland.dao.CategoryDAO;
import com.app.restoland.service.ICategoryService;
import com.app.restoland.utils.RestoUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap, false)){
                   categoryDAO.save(getCategoryFromMap(requestMap, false));
                   return RestoUtils.getResponseEntity("La categoría se creo con éxito", HttpStatus.OK);
                }
            }else {
                return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Dentro del if");
                return new ResponseEntity<>(categoryDAO.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDAO.findAll(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap, true)){
                    Optional optional = categoryDAO.findById(Long.valueOf(requestMap.get("id")));
                    if(!optional.isEmpty()){
                        categoryDAO.save(getCategoryFromMap(requestMap, true));
                        return RestoUtils.getResponseEntity("La categoría se edito con éxito", HttpStatus.OK);
                    }else {
                        return RestoUtils.getResponseEntity("El id de la categoría no existe", HttpStatus.OK);
                    }
                }else {
                    return RestoUtils.getResponseEntity(RestoConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    public Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Long.valueOf(requestMap.get("id")));
        }
        String nameCategory = requestMap.get("name");
        category.setName(nameCategory.toUpperCase().charAt(0) + nameCategory.substring(1, nameCategory.length()).toLowerCase());
        return category;
    }
}
