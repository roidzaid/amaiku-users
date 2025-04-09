package com.amaiku.users.models;

public class RecuperarPassModel {

    private String usuario;
    private String mail;

    public RecuperarPassModel() {
    }

    public RecuperarPassModel(String usuario, String mail) {
        this.usuario = usuario;
        this.mail = mail;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
