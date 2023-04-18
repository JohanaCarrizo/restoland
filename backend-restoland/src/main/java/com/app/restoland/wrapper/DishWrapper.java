package com.app.restoland.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DishWrapper {

    private Long id;
    private String name;
    private String description;
    private Integer qualification;
    private Double price;
    private String status;
    private Integer healtScore;
    private String preparationTime;
    private byte[] idImagen;
    private Long idCategory;
    private String nameCategory;

    public DishWrapper() {
    }

    public DishWrapper(Long id, String name, String description, Integer qualification, Double price, String status, Integer healtScore, String preparationTime, Long idCategory, String nameCategory) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.qualification = qualification;
        this.price = price;
        this.status = status;
        this.healtScore = healtScore;
        this.preparationTime = preparationTime;
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
    }

    public DishWrapper(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DishWrapper(Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
