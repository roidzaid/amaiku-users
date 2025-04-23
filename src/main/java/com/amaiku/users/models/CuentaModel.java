package com.amaiku.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaModel {

    private Long idCuenta;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El subdominio es obligatorio")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "El subdominio solo puede contener minúsculas, números y guiones")
    private String subdomino;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El teléfono debe contener solo números y tener entre 7 y 15 dígitos")
    private String telefono;

    private String estado;

    private Date fechaAlta;

    private Date fechaModif;

    public CuentaModel(String nombre, String subdomino, String email, String telefono, String estado) {
        this.nombre = nombre;
        this.subdomino = subdomino;
        this.email = email;
        this.telefono = telefono;
        this.estado = estado;
    }
}
