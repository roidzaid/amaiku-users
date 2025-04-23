package com.amaiku.users.exceptions;

public class RolNoRegistradoException extends Exception{

    public RolNoRegistradoException(){
        super("El rol no esta registrado en el sistema");
     }

    public RolNoRegistradoException(String message) {
        super(message);
    }
}
