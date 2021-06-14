package com.mcosta.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mcosta.model.Student;

public class StudentDao extends Dao implements Persistence<Student> {

    @Override
    public void save(Student object) throws Exception {
        
        new ReaderDao().save(object);
        
        String sql = "insert into student (reader_cpf, registration) values (?, ?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getCpf());
        ps.setString(2, object.getRegistration());
        ps.executeUpdate();

    }

    @Override
    public List<Student> findAll() throws Exception {
        String sql = "select reader.cpf, reader.name, reader.address, student.registration from student inner join " +
                "reader on reader.cpf = student.reader_cpf order by reader.name";

        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery()
                ;
        List<Student> students = new ArrayList<Student>();

        while (rs.next()) {
            Student student = new Student();
            student.setCpf(rs.getString("cpf"));
            student.setAddress(rs.getString("address"));
            student.setRegistration(rs.getString("registration"));
            student.setName(rs.getString("name"));
            students.add(student);
        }
        return students;
    }

    @Override
    public void update(Student object) throws Exception {
        new ReaderDao().update(object);

        String sql = "update student set registration = ? where reader_cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getRegistration());
        ps.setString(2, object.getCpf());
        ps.executeUpdate();
        
    }

    @Override
    public void delete(Student object) throws Exception {
        String sql = "delete from student where reader_cpf = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, object.getCpf());
        ps.executeUpdate();

        new ReaderDao().delete(object);
    }

    @Override
    public Student findById(Long id)  throws Exception{
        return null;
    }


    public Student findByCpf(String cpf) throws Exception {
        String sql = "select reader.cpf, reader.name, reader.address, student.registration from student inner join " +
                "reader on reader.cpf = student.reader_cpf where reader.cpf = ?";

        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, cpf);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Student student = new Student();
            student.setCpf(rs.getString("cpf"));
            student.setAddress(rs.getString("address"));
            student.setRegistration(rs.getString("registration"));
            student.setName(rs.getString("name"));
            return student;
        } else {
            return null;
        }

    }

    
}
