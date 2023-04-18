package com.app.restoland.service;

import com.app.restoland.POJO.Dish;
import com.app.restoland.wrapper.DishWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IDishService {

    ResponseEntity<String> addNewDish(Map<String, String> requestMap, MultipartFile file);

    Dish getOne(Long id);

    ResponseEntity<List<DishWrapper>> getAllDish();

    ResponseEntity<String> updateDish(Map<String, String> requestMap, MultipartFile file);

    ResponseEntity<String> deleteDish(Long id);

    ResponseEntity<String> updateStatus(Map<String, String> requestMap);

    ResponseEntity<List<DishWrapper>> getByCategory(Long id);

    ResponseEntity<DishWrapper> getDishById(Long id);
}
