package com.amaiku.users.models;

public class RolesModel {

    private String rol;
    private String aplicacion;

    public RolesModel() {
    }

    public RolesModel(String rol, String aplicacion) {
        this.rol = rol;
        this.aplicacion = aplicacion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }
}