package com.amaiku.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolModel {

    private Long idRol;

    @Pattern(
            regexp = "^[A-Z_]+$",
            message = "El nombre del rol debe estar en mayúsculas y no debe contener espacios"
    )
    @NotBlank(message = "El nombre del rol no puede estar vacío")
    private String nombre;

    public RolModel(String nombre) {
        this.nombre = nombre;
    }
}