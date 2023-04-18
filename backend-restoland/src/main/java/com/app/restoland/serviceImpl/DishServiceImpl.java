package com.app.restoland.serviceImpl;

import com.app.restoland.JWT.JwtFilter;
import com.app.restoland.POJO.Category;
import com.app.restoland.POJO.Dish;
import com.app.restoland.POJO.Image;
import com.app.restoland.constants.RestoConstants;
import com.app.restoland.dao.DishDAO;
import com.app.restoland.service.IDishService;
import com.app.restoland.service.ImageService;
import com.app.restoland.utils.RestoUtils;
import com.app.restoland.wrapper.DishWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DishServiceImpl implements IDishService {

    @Autowired
    DishDAO dishDAO;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ImageService imageService;

    @Override
    public ResponseEntity<String> addNewDish(Map<String, String> requestMap, MultipartFile file) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateDishMap(requestMap, false)){
                    dishDAO.save(getDishFromMap(requestMap, file, false));
                    return RestoUtils.getResponseEntity("El plato se agrego con éxito", HttpStatus.OK);
                }
                return RestoUtils.getResponseEntity(RestoConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }else {
                return RestoUtils.getResponseEntity(RestoConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Dish getOne(Long id) {
        return dishDAO.getOne(id);
    }

    @Override
    public ResponseEntity<List<DishWrapper>> getAllDish() {
        try {
            return new ResponseEntity<>(dishDAO.getAllDish(), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateDish(Map<String, String> requestMap, MultipartFile file) {
        try {
            if(jwtFilter.isAdmin()){
                if(validateDishMap(requestMap, true)){
                    Optional<Dish> oDish = dishDAO.findById(Long.valueOf(requestMap.get("id")));
                    if(!oDish.isEmpty()){
                        Dish dish = getDishFromMap(requestMap, file, true);
                        dish.setStatus(oDish.get().getStatus());
                        dishDAO.save(dish);
                        return RestoUtils.getResponseEntity("El plato se modifico con éxito", HttpStatus.OK);
                    }else {
                        return RestoUtils.getResponseEntity("El plato con ese id no existe", HttpStatus.OK);
                    }
                }else {
                    return RestoUtils.getResponseEntity(RestoConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            }else {
                return RestoUtils.getResponseEntity(RestoConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteDish(Long id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional<Dish> oDish = dishDAO.findById(id);
                if(!oDish.isEmpty()){
                    dishDAO.deleteById(id);
                    return RestoUtils.getResponseEntity("El plato se eliminó con éxito", HttpStatus.OK);
                }
                    return RestoUtils.getResponseEntity("El plato con ese id no existe", HttpStatus.OK);
            }else {
                return RestoUtils.getResponseEntity(RestoConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                Optional<Dish> oDish = dishDAO.findById(Long.valueOf(requestMap.get("id")));
                if(!oDish.isEmpty()){
                    dishDAO.updateDishStatus(requestMap.get("status"), Long.valueOf(requestMap.get("id")));
                    return RestoUtils.getResponseEntity("El estado del plato se modifico con existe", HttpStatus.OK);
                }else{
                    return RestoUtils.getResponseEntity("El plato con ese id no existe", HttpStatus.OK);
                }
            }else {
                return RestoUtils.getResponseEntity(RestoConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<DishWrapper>> getByCategory(Long id) {
        try {
            return new ResponseEntity<>(dishDAO.getDishByCategory(id), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<DishWrapper> getDishById(Long id) {
        try {
            return new ResponseEntity<>(dishDAO.getDishById(id), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new DishWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateDishMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Dish getDishFromMap(Map<String, String> requestMap, MultipartFile file, boolean isAdd) {
        Category category = new Category();
        category.setId(Long.valueOf(requestMap.get("categoryId")));

        Dish dish = new Dish();
        if(isAdd){
            dish.setId(Long.valueOf(requestMap.get("id")));
        }else {
            dish.setStatus("true");
        }
        dish.setCategory(category);
        dish.setName(requestMap.get("name"));
        dish.setDescription(requestMap.get("description"));
        dish.setPrice(Double.valueOf(requestMap.get("price")));
        dish.setQualification(Integer.valueOf(requestMap.get("qualification")));
        dish.setHealtScore(Integer.valueOf(requestMap.get("healtScore")));
        dish.setPreparationTime(requestMap.get("preparationTime"));

        if(file != null){
            Image image = imageService.save(file);
            dish.setImage(image);
        }
        return dish;
    }
}
