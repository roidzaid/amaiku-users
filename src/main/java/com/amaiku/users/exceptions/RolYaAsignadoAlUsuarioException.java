package com.amaiku.users.exceptions;

public class RolYaAsignadoAlUsuarioException extends Exception{

    public RolYaAsignadoAlUsuarioException(){
        super("El rol ya esta asignado al usuario");
    }
}
