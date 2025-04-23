package com.amaiku.users.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetearPassModel {

    @NotBlank(message = "El token no puede estar vacío")
    @Size(min = 100, message = "El token parece inválido o truncado")
    private String token;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String nuevaPass;
}
