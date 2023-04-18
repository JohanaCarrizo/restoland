package com.app.restoland.dao;

import com.app.restoland.POJO.Category;
import com.app.restoland.wrapper.CategoryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Long> {

    Category findByName(String name);

    List<Category> getAllCategory();

}
