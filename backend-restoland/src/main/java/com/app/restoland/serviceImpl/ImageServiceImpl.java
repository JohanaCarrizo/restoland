package com.app.restoland.serviceImpl;

import com.app.restoland.POJO.Dish;
import com.app.restoland.POJO.Image;
import com.app.restoland.dao.ImageDAO;
import com.app.restoland.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageDAO imageDAO;

    @Override
    public Image save(MultipartFile file) {
        if(file != null){
            try {

                Image image = new Image();

                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());

                return imageDAO.save(image);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Image update(MultipartFile file, Long imageId) {

        if(file != null){
            try {

                Image image = new Image();

                if(imageId != null){
                    Optional<Image> request = imageDAO.findById(imageId);

                    if(request.isPresent()){
                        image = request.get();
                    }
                }
                image.setMime(file.getContentType());
                image.setName(file.getName());
                image.setContent(file.getBytes());

                return imageDAO.save(image);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }



}
