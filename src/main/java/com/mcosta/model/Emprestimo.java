package com.mcosta.model;

import java.time.LocalDate;

public class Emprestimo {
    private Long codigo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private CopyBook copyBook;
    private Reader reader;

    public Emprestimo() {
        this.dataEmprestimo = LocalDate.now();
    }
    
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    public CopyBook getExemplar() {
        return copyBook;
    }
    public void setExemplar(CopyBook copyBook) {
        this.copyBook = copyBook;
    }
    public Reader getLeitor() {
        return reader;
    }
    public void setLeitor(Reader reader) {
        this.reader = reader;
    }
    @Override
    public String toString() {
        return codigo.toString();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Emprestimo other = (Emprestimo) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }



    
    
}
