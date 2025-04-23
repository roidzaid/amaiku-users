package com.amaiku.users.models;

public enum Estado {

    ACTIVO("ACTIVO"),
    PENDIENTE("PENDIENTE"),
    CERRADO("CERRADO"),
    BLOQUEADO("BLOQUEADO");

    private final String valor;

    Estado(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
