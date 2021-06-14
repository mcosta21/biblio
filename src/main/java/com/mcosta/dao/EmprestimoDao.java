package com.mcosta.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.mcosta.model.Emprestimo;

public class EmprestimoDao  extends Dao implements Persistence<Emprestimo> {

    @Override
    public void save(Emprestimo dado) throws Exception {
        String sql = "insert into emprestimo (dataemprestimo, leitor_cpf, exemplar_codigo) values (?,?,?)";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setDate(1, Date.valueOf(dado.getDataEmprestimo()));
        ps.setString(2, dado.getLeitor().getCpf());
        ps.setLong(3, dado.getExemplar().getId());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        
        if (rs.next()) {
            dado.setCodigo(rs.getLong("codigo"));
        }
        
    }

    @Override
    public List<Emprestimo> findAll() throws Exception {
        String sql = "select codigo, dataemprestimo, datadevolucao, leitor_cpf, exemplar_codigo from emprestimo order by dataemprestimo";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        while (rs.next()) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setCodigo(rs.getLong("codigo"));
            if(!(rs.getDate("datadevolucao")==null)) {
                emprestimo.setDataDevolucao(rs.getDate("datadevolucao").toLocalDate());
            }
            emprestimo.setDataEmprestimo(rs.getDate("dataemprestimo").toLocalDate());
            emprestimo.setLeitor(new ReaderDao().findByCpf(rs.getString("leitor_cpf")));
            emprestimo.setExemplar(new BookDao().buscarExemplar(rs.getLong("exemplar_codigo")));

            emprestimos.add(emprestimo);
            
        }

        return emprestimos;
        
    }

    @Override
    public void update(Emprestimo dado) throws Exception {

        
    }

    @Override
    public void delete(Emprestimo dado) throws Exception {
        
    }

    @Override
    public Emprestimo findById(Long id) throws Exception {
        return null;
    }


}
