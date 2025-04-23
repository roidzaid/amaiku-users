package com.amaiku.users.models;

public enum Rol {

    AMAIKU("AMAIKU"),
    ADMIN_CUENTA("ADMIN_CUENTA"),
    PROFESIONAL("PROFESIONAL"),
    SECRETARIA("SECRETARIA"),
    PACIENTE("PACIENTE");

    private final String valor;

    Rol(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}

