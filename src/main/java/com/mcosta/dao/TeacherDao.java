package com.mcosta.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mcosta.model.Teacher;

public class TeacherDao extends Dao implements Persistence<Teacher> {

    @Override
    public void save(Teacher object) throws Exception {
        
        new ReaderDao().save(object);
        
        String sql = "insert into teacher (reader_cpf, discipline) values (?, ?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getCpf());
        ps.setString(2, object.getDiscipline());
        ps.executeUpdate();

    }

    @Override
    public List<Teacher> findAll() throws Exception {
        String sql = "select reader.cpf, reader.name, reader.address, teacher.discipline from teacher inner join " +
                "reader on reader.cpf = teacher.reader_cpf order by reader.name";
        PreparedStatement ps = getPreparedStatement(false, sql);

        ResultSet rs = ps.executeQuery();

        List<Teacher> teachers = new ArrayList<Teacher>();
        while (rs.next()) {
            Teacher teacher = new Teacher();
            teacher.setCpf(rs.getString("cpf"));
            teacher.setDiscipline(rs.getString("discipline"));
            teacher.setAddress(rs.getString("address"));
            teacher.setName(rs.getString("name"));
            teachers.add(teacher);
        }
        return teachers;
    }

    @Override
    public void update(Teacher object) throws Exception {
        new ReaderDao().update(object);

        String sql = "update teacher set discipline = ? where reader_cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getDiscipline());
        ps.setString(2, object.getCpf());
        ps.executeUpdate();
    }

    @Override
    public void delete(Teacher object) throws Exception {
        String sql = "delete from teacher where reader_cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getCpf());
        ps.executeUpdate();

        new ReaderDao().delete(object);
    }

    @Override
    public Teacher findById(Long id) throws Exception {
        return null;
    }

    public Teacher findByCpf(String cpf) throws Exception {
        String sql = "select reader.cpf, reader.name, reader.address, teacher.discipline from teacher inner join " +
                "reader on reader.cpf = teacher.reader_cpf where reader.cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Teacher teacher = new Teacher();
            teacher.setCpf(rs.getString("cpf"));
            teacher.setDiscipline(rs.getString("discipline"));
            teacher.setName(rs.getString("name"));
            teacher.setAddress(rs.getString("address"));
            return teacher;
        } else {
            return null;
        }
    }

}
