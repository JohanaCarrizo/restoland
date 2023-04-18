package com.app.restoland.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Dish.getAllDish", query = "select new com.app.restoland.wrapper.DishWrapper(d.id, d.name, d.description, d.qualification, d.price, d.status, d.healtScore, d.preparationTime, d.category.id, d.category.name) from Dish d")
@NamedQuery(name = "Dish.updateDishStatus", query = "update Dish d set d.status=:status where d.id=:id")
@NamedQuery(name = "Dish.getDishByCategory", query = "select new com.app.restoland.wrapper.DishWrapper(d.id, d.name) from Dish d where d.category.id=:id and d.status='true'")
@NamedQuery(name = "Dish.getDishById", query = "select new com.app.restoland.wrapper.DishWrapper(d.id, d.name, d.description, d.price) from Dish d where d.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "dish")
public class Dish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;

    @OneToOne
    @JoinColumn(name = "image_fk", nullable = true)
    private Image image;

    private Double price;
    private Integer qualification;
    private String status;

    @Column(name = "heal_score")
    private Integer healtScore;

    @Column(name = "preparation_time")
    private String preparationTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;
}
