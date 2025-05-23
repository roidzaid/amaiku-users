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
public class RecuperarPassModel {

    @NotBlank(message = "El mail no puede estar vacío")
    @Email(message = "El mail debe tener un formato válido")
    private String mail;

}
