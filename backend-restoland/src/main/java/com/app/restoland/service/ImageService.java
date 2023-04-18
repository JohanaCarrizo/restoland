package com.app.restoland.service;

import com.app.restoland.POJO.Dish;
import com.app.restoland.POJO.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image save(MultipartFile file);

    Image update(MultipartFile file, Long imageId);

}
