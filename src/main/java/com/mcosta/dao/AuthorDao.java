package com.mcosta.dao;

import com.mcosta.model.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao extends Dao implements Persistence<Author> {
    
    public void save(Author object) throws Exception {
        String sql = "Insert into author (name, nationality) values (?, ?)";
        
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setString(1, object.getName());
        ps.setString(2, object.getNationality());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            object.setId(rs.getLong("id"));
        }
    }

    public List<Author> findAll() throws Exception {
        String sql = "Select * from author order by name";

        PreparedStatement ps = getPreparedStatement(false, sql);
        
        ResultSet rs = ps.executeQuery();
        
        List<Author> authors = new ArrayList<Author>();
        while (rs.next()) {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setNationality(rs.getString("nationality"));

            authors.add(author);
        }

        return authors;
    }

    @Override
    public void update(Author object) throws Exception {
        String sql = "update author set name = ? , nationality = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getName());
        ps.setString(2, object.getNationality());
        ps.setLong(3, object.getId());

        ps.executeUpdate();

    }

    @Override
    public void delete(Author object) throws Exception {
        String sql = "delete from author where id = ?";
        PreparedStatement ps = getPreparedStatement(false,sql);
        ps.setLong(1, object.getId());
        ps.executeUpdate();

        object = null;
    }

    @Override
    public Author findById(Long id) throws Exception {
        String sql = "Select * from author where id = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Author author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setNationality(rs.getString("nationality"));
            return author;
        }
        else {
            return null;
        }
    }

}
