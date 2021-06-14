package com.mcosta.model;

public enum Status {
    BORROWED("Emprestado"),
    AVAILABLE("Disponível");

    private final String valor;

    private Status(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

}
