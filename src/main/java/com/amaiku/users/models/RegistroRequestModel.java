package com.amaiku.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    RegistroRequestModel {

    @NotBlank(message = "El mail no puede estar vacío")
    @Email(message = "El mail debe tener un formato válido")
    private String mail;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String pass;


    private String rol;

    @NotBlank(message = "La cuenta no puede estar vacío")
    @Size(min = 2, max = 100, message = "La cuenta debe tener entre 2 y 100 caracteres")
    private String cuenta;
}
