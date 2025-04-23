package com.amaiku.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Email
    private String mail;
    private String pass;
    private Date fechaAlta;
    private Date fechaModif;
    private String estado;

    @OneToMany(mappedBy = "usuario")
    private List<UsuarioCuentaRolEntity> CuentasRoles;

    public UsuarioEntity(String mail, String pass, Date fechaAlta, Date fechaModif, String estado) {
        this.mail = mail;
        this.pass = pass;
        this.fechaAlta = fechaAlta;
        this.fechaModif = fechaModif;
        this.estado = estado;

    }
}
