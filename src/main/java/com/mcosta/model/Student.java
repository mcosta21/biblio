package com.mcosta.model;

public class Student extends Reader {
    private String registration;

    private static final Integer LIMITEDEVOLUCAO= 15;

    public Student(){}

    public Student(String cpf, String name, String address, String registration) {
        super();
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.registration = registration;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
