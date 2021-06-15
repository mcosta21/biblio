package com.mcosta.dao;

import com.mcosta.model.User;
import com.mcosta.model.UserTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao implements Persistence<User> {

    @Override
    public void save(User object) throws Exception {
        String sql = "Insert into public.user (username, password, name, user_type) values (?, ?, ?, ?)";

        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setString(1, object.getUsername());
        ps.setString(2, object.getPassword());
        ps.setString(3, object.getName());
        ps.setString(4, object.getUserType().name());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            object.setId(rs.getLong("id"));
        }
    }

    public List<User> findAll() throws Exception {
        String sql = "Select * from public.user order by name";

        PreparedStatement ps = getPreparedStatement(false, sql);
        
        ResultSet rs = ps.executeQuery();
        
        List<User> users = new ArrayList<User>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setUserType(UserTypeEnum.valueOf(rs.getString("user_type")));

            users.add(user);
        }

        return users;
    }

    @Override
    public void update(User object) throws Exception {
        String sql = "update public.user set username = ?, password = ?, name = ?, user_type = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getUsername());
        ps.setString(2, object.getPassword());
        ps.setString(3, object.getName());
        ps.setString(4, object.getUserType().name());

        ps.setLong(5, object.getId());

        ps.executeUpdate();
    }

    @Override
    public void delete(User user) throws Exception {
        String sql = "delete from public.user where id = ?";
        PreparedStatement ps = getPreparedStatement(false,sql);
        ps.setLong(1, user.getId());
        ps.executeUpdate();

        user = null;
    }

    @Override
    public User findById(Long id) throws Exception {
        String sql = "Select * from public.user where id = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setUserType(UserTypeEnum.valueOf(rs.getString("user_type")));
            return user;
        }
        else {
            return null;
        }
    }

    public User findUserByUsernameAndPassword(String username, String password) throws Exception {
        String sql = "Select * from public.user u where u.username = ? and u.password = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setUserType(UserTypeEnum.valueOf(rs.getString("user_type")));
            return user;
        }
        else {
            return null;
        }
    }
}
