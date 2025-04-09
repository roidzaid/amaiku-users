package com.amaiku.users.models;

public class UsuariosModel {

    private String usuario;
    private String pass;
    private String aplicacion;
    private String mail;
    private boolean actualizar;

    public UsuariosModel() {
    }

    public UsuariosModel(String usuario, String pass, String aplicacion, String mail, boolean actualizar) {
        this.usuario = usuario;
        this.pass = pass;
        this.aplicacion = aplicacion;
        this.mail = mail;
        this.actualizar = actualizar;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }
}
