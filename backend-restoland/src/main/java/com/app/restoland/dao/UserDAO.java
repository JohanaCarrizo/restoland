package com.app.restoland.dao;

import com.app.restoland.POJO.User;
import com.app.restoland.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    @Transactional
    @Modifying
    Integer updateRole(@Param("role") String role, @Param("id") Long id);

    User findByEmail(String email);
}
