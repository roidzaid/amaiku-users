package com.amaiku.users.exceptions;

public class RolNoAsignadoAlUsuarioException extends Exception{

    public RolNoAsignadoAlUsuarioException(){
        super("El rol no esta asignado al usuario");
    }
}
