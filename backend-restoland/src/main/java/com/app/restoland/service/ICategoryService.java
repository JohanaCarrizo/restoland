package com.app.restoland.service;

import com.app.restoland.POJO.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface ICategoryService {

    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCategory(String filterValue);

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
