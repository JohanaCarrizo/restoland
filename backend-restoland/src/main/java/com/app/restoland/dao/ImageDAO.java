package com.app.restoland.dao;

import com.app.restoland.POJO.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDAO extends JpaRepository<Image, Long> {
}
