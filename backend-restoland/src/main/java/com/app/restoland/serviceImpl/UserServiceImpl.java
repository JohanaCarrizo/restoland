package com.app.restoland.serviceImpl;

import com.app.restoland.JWT.CustomerUsersDetailsService;
import com.app.restoland.JWT.JwtFilter;
import com.app.restoland.JWT.JwtUtil;
import com.app.restoland.POJO.User;
import com.app.restoland.constants.RestoConstants;
import com.app.restoland.dao.UserDAO;
import com.app.restoland.service.IUserService;
import com.app.restoland.utils.EmailUtils;
import com.app.restoland.utils.RestoUtils;
import com.app.restoland.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {

        log.info("regitro interno {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {

                User user = userDAO.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    sendMailWelcome(requestMap.get("email"));
                    userDAO.save(getUserFromMap(requestMap));
                    return RestoUtils.getResponseEntity("El registro se realizo con éxito", HttpStatus.OK);
                } else {
                    return RestoUtils.getResponseEntity("El email ya existe", HttpStatus.BAD_REQUEST);
                }
            } else {
                return RestoUtils.getResponseEntity(RestoConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailWelcome(String mail) {

        emailUtils.sendSimpleMessage(mail, "Cuenta aprobada", "USER:- "+mail+" \n Bienvenido/a a RESTOLAND. \n Attn:- ADMIN ");
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Dentro de login");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                return new ResponseEntity<String>("{\"token\":\""+
                        jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(), customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                HttpStatus.OK);
            }
        }catch (Exception ex){
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Credenciales no validas"+"\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDAO.getAllUser(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return RestoUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User user = userDAO.findByEmail(jwtFilter.getCurrentUser());
            if(!user.equals(null)){
                if(user.getPassword().equals(requestMap.get("oldPassword"))){
                    user.setPassword(requestMap.get("newPassword"));
                    userDAO.save(user);
                    return RestoUtils.getResponseEntity("Cambio de contraseña exitosa", HttpStatus.BAD_REQUEST);
                }
                return RestoUtils.getResponseEntity("Contraseña vieja incorrecta", HttpStatus.BAD_REQUEST);
            }
            return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDAO.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
                emailUtils.forgotMail(user.getEmail(), "Credenciales para Restoland", user.getPassword());
            return RestoUtils.getResponseEntity("Revise su correo para obtener sus credenciales", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public User getOne(Long id) {
        return userDAO.getOne(id);
    }

    @Override
    public ResponseEntity<String> updateRole(Map<String, String> requestMap) {
         try {
             if(jwtFilter.isAdmin()){
                 Optional<User> oUser = userDAO.findById(Long.valueOf(requestMap.get("id")));
                 if(!oUser.isEmpty()){
                     userDAO.updateRole(requestMap.get("role"), Long.valueOf(requestMap.get("id")));
                     return RestoUtils.getResponseEntity("El cambio de rol se realizo con éxito", HttpStatus.OK);
                 }
             }else {
                 return RestoUtils.getResponseEntity(RestoConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
             }

         }catch (Exception ex){
             ex.printStackTrace();
         }
         return RestoUtils.getResponseEntity(RestoConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap){

        if(requestMap.containsKey("name") && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setRole("cliente");

        return user;
    }
}
