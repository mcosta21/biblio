package com.mcosta.model;

public class Teacher extends Reader {

    private String discipline;

    public static final Integer RETURN_LIMIT = 30;

    public Teacher(){}

    public Teacher(String cpf, String name, String address, String discipline) {
        super();
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.discipline = discipline;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
}
