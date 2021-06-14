package com.mcosta.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mcosta.model.Publisher;

public class PublisherDao extends Dao implements Persistence<Publisher> {

    @Override
    public void save(Publisher object) throws Exception {
        String sql = "insert into publisher (name) values (?)";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setString(1, object.getName());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            object.setId(rs.getLong("id"));
        }
        
    }

    @Override
    public List<Publisher> findAll() throws Exception {
        String sql = "select * from publisher order by name";

        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        List<Publisher> publishers = new ArrayList<Publisher>();
        while (rs.next()) {
            Publisher publisher = new Publisher();
            publisher.setId(rs.getLong("id"));
            publisher.setName(rs.getString("name"));
            publishers.add(publisher);
        }

        return publishers;
    }

    @Override
    public void update(Publisher object) throws Exception {
        String sql = "update publisher set name = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setString(1, object.getName());
        ps.setLong(2, object.getId());

        ps.executeUpdate();
        
    }

    @Override
    public void delete(Publisher object) throws Exception {
        String sql = "delete from publisher where id = ?";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setLong(1, object.getId());

        ps.executeUpdate();       

        object = null;
        
    }

    @Override
    public Publisher findById(Long id) throws Exception {
        return null;
    }

}
