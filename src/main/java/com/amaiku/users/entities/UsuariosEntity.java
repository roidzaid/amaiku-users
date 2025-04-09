package com.amaiku.users.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuariosEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "seq")
    private Long idUsuario;
    private String usuario;
    private String pass;
    @Email
    private String mail;
    private Date fechaAlta;
    private String Aplicacion;
    private Date fechaModif;
    private boolean actualizar;

    @ManyToMany
    @JoinTable(
            name = "roles_usuario",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol"))
    private List<RolesEntity> usuariosRoles;

    public UsuariosEntity() {
    }

    public UsuariosEntity(String usuario, String pass, String mail, Date fechaAlta, String aplicacion, Date fechaModif, boolean actualizar) {
        this.usuario = usuario;
        this.pass = pass;
        this.mail = mail;
        this.fechaAlta = fechaAlta;
        this.Aplicacion = aplicacion;
        this.fechaModif = fechaModif;
        this.actualizar = actualizar;
    }

    public void agregarRol(RolesEntity rol){
        this.usuariosRoles.add(rol);
    }

    public void eliminarRol(RolesEntity rol){
        this.usuariosRoles.remove(rol);
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getAplicacion() {
        return Aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        Aplicacion = aplicacion;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public List<RolesEntity> getUsuariosRoles() {
        return usuariosRoles;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }
}
