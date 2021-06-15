package com.mcosta.model;

import java.util.Objects;

public class Reader {

    protected String cpf;
    protected String name;
    protected String address;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType(){
        if(this instanceof Teacher){
            return "Professor";
        }
        else {
            return "Aluno";
        }
    }

    public String getDiscipline(){
        if(this instanceof Teacher){
            return ((Teacher) this).getDiscipline();
        }
        else {
            return "-";
        }
    }

    public String getRegistration(){
        if(this instanceof Teacher){
            return "-";
        }
        else {
            return ((Student) this).getRegistration();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader reader = (Reader) o;
        return Objects.equals(cpf, reader.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return cpf + " - " + name;
    }
}
