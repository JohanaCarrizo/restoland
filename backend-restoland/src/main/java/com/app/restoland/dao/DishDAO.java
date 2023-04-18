package com.app.restoland.dao;

import com.app.restoland.POJO.Dish;
import com.app.restoland.wrapper.DishWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishDAO extends JpaRepository<Dish, Long> {

    List<DishWrapper>getAllDish();

    @Modifying
    @Transactional
    Integer updateDishStatus(@Param("status") String status, @Param("id") Long id);

    List<DishWrapper> getDishByCategory(@Param("id") Long id);

    DishWrapper getDishById(@Param("id") Long id);
}
