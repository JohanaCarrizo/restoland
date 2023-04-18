package com.app.restoland.restImpl;

import com.app.restoland.POJO.Dish;
import com.app.restoland.rest.ImageRest;
import com.app.restoland.service.IDishService;
import com.app.restoland.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
public class ImageRestImpl implements ImageRest {

    @Autowired
    IDishService dishService;

    @Autowired
    ImageService imageService;

    @Override
    public ResponseEntity<byte[]> uploadImage(Long id) {

        Dish dish = dishService.getOne(id);

        byte[] img = dish.getImage().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(img, headers, HttpStatus.OK);
    }
}
