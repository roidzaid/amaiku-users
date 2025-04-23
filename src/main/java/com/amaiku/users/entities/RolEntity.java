package com.amaiku.users.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    private String nombre;

    @OneToMany(mappedBy = "rol")
    private List<UsuarioCuentaRolEntity> usuariosRoles;

    public RolEntity(String nombre) {
        this.nombre = nombre;
    }
}
