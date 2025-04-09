package com.amaiku.users.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator = "seq")
    private Long idRol;
    private String rol;
    private String aplicacion;

    @ManyToMany(mappedBy = "usuariosRoles")
    private List<UsuariosEntity> usuarios;

    public RolesEntity() {
    }

    public RolesEntity(String rol, String aplicacion) {
        this.rol = rol;
        this.aplicacion = aplicacion;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
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
