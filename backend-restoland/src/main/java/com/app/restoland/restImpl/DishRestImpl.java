package com.app.restoland.restImpl;

import com.app.restoland.constants.RestoConstants;
import com.app.restoland.rest.IDishRest;
import com.app.restoland.service.IDishService;
import com.app.restoland.utils.RestoUtils;
import com.app.restoland.wrapper.DishWrapper;
import com.app.restoland.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DishRestImpl implements IDishRest {

    @Autowired
    IDishService dishService;

    @Override
    public ResponseEntity<String> addNewDish(Map<String, String> requestMap, MultipartFile file) {
        try {
            return dishService.addNewDish(requestMap, file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<DishWrapper>> getAllDish() {
        try {
            return dishService.getAllDish();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateDish(Map<String, String> requestMap, MultipartFile file) {
        try {
            return dishService.updateDish(requestMap, file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteDish(Long id) {
        try {
            return dishService.deleteDish(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            return dishService.updateStatus(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<DishWrapper>> getByCategory(Long id) {
        try {
            return dishService.getByCategory(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<DishWrapper> getDishById(Long id) {
        try {
            return dishService.getDishById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new DishWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
