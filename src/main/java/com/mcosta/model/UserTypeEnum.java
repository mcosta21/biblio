package com.mcosta.model;

public enum UserTypeEnum {
    ATTENDANT ("Atendente"),
    LIBRARIAN ("Bibliotecário"),
    ADMIN ("Administrador");

    private final String value;

    UserTypeEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return getValue();
    }
}
