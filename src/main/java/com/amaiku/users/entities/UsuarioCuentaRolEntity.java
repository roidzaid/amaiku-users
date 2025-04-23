package com.amaiku.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario_Cuenta_rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCuentaRolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_Usuario")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "id_Cuenta")
    private CuentaEntity Cuenta;

    @ManyToOne
    @JoinColumn(name = "id_Rol")
    private RolEntity rol;

    private String estado;
    private Date fechaAlta;
    private Date fechaModificacion;


}