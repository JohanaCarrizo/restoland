package com.app.restoland.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllUser", query = "select new com.app.restoland.wrapper.UserWrapper(u.id, u.name, u.email, u.password) from User u where u.role='cliente'")
@NamedQuery(name = "User.updateRole", query = "update User u set u.role=:role where u.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email",nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password",nullable = false, length = 50)
    private String password;

    @Column(name = "role")
    private String role;
}
