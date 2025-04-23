package com.amaiku.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Cuenta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    private String nombre;

    private String subdomino;

    private String email;

    private String telefono;

    private String estado;

    private Date fechaAlta;

    private Date fechaModif;

    @OneToMany(mappedBy = "Cuenta")
    private List<UsuarioCuentaRolEntity> usuariosRoles;

    public CuentaEntity(String nombre, String subdomino, String email, String telefono, String estado, Date fechaAlta, Date fechaModif) {
        this.nombre = nombre;
        this.subdomino = subdomino;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.fechaModif = fechaModif;
    }
}
