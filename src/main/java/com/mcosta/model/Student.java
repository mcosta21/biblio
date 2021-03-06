package com.mcosta.model;

public class Student extends Reader {
    private String registration;

    public static final Integer RETURN_LIMIT= 15;

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
