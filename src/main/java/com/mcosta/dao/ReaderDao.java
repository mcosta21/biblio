package com.mcosta.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.mcosta.model.Student;
import com.mcosta.model.Reader;
import com.mcosta.model.Teacher;

public class ReaderDao extends Dao implements Persistence<Reader> {

    @Override
    public void save(Reader object) throws Exception {
        String sql = "insert into reader (cpf, name, address) values (?,?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getCpf());
        ps.setString(2, object.getName());
        ps.setString(3, object.getAddress());
        ps.executeUpdate();

    }

    @Override
    public List<Reader> findAll() throws Exception {
        List<Teacher> teachers = new TeacherDao().findAll();
        List<Student> students = new StudentDao().findAll();
        List<Reader> readers = new ArrayList<Reader>();

        readers.addAll(teachers);
        readers.addAll(students);

        return readers;
        
    }

    @Override
    public void update(Reader object) throws Exception {
        String sql = "update reader set name = ?, address = ? where cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getName());
        ps.setString(2, object.getAddress());
        ps.setString(3, object.getCpf());
        ps.executeUpdate();
    }

    @Override
    public void delete(Reader object) throws Exception {
        String sql = "delete from reader where cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getCpf());
        ps.executeUpdate();
     
    }

    @Override
    public Reader findById(Long id) throws Exception {
        return null;
    }

    public Reader findByCpf(String cpf) throws Exception {
        Reader reader = new StudentDao().findByCpf(cpf);

        if (reader ==null) {
            reader = new TeacherDao().findByCpf(cpf);
        }

        return reader;
    }
    
}
