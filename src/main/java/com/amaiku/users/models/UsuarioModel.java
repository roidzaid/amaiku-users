package com.amaiku.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {

    private Long idUsuario;

    @NotBlank(message = "El mail no puede estar vacío")
    @Email(message = "El mail debe tener un formato válido")
    private String mail;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String pass;

    @PastOrPresent(message = "La fecha de alta no puede ser en el futuro")
    private Date fechaAlta;

    @PastOrPresent(message = "La fecha de modificación no puede ser en el futuro")
    private Date fechaModif;

    private String estado;

    public UsuarioModel(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }
}
