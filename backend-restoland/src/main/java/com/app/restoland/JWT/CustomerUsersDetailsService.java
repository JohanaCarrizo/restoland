package com.app.restoland.JWT;

import com.app.restoland.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDAO userDAO;
    private com.app.restoland.POJO.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Dentro loadUserByUsername {}", username);
        userDetail = userDAO.findByEmailId(username);
        if(!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public com.app.restoland.POJO.User getUserDetail(){
        return userDetail;
    }
}
