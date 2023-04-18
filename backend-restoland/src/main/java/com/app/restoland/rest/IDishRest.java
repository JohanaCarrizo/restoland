package com.app.restoland.rest;

import com.app.restoland.wrapper.DishWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/dish")
public interface IDishRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewDish(@RequestBody Map<String, String> requestMap, @RequestParam(value = "file", required = false) MultipartFile file);

    @GetMapping(path = "/get")
    ResponseEntity<List<DishWrapper>> getAllDish();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateDish(@RequestBody Map<String, String> requestMap, @RequestParam(value = "file", required = false) MultipartFile file);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteDish(@PathVariable Long id);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<DishWrapper>> getByCategory(@PathVariable Long id);

    @GetMapping(path = "/getById/{id}")
    ResponseEntity<DishWrapper> getDishById(@PathVariable Long id);
}
