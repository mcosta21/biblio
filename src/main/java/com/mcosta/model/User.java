package com.mcosta.model;

import com.mcosta.model.UserTypeEnum;

import java.util.Objects;

public class User {

    private Long id;
    private String username;
    private String password;
    private String name;
    private UserTypeEnum userType;

    public User(){}

    public User(String username, String password, String name, UserTypeEnum userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.userType = userType;
    }

    public User(Long id, String username, String password, String name, UserTypeEnum userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
