package com.amaiku.users.exceptions;

public class RolExistenteException extends Exception{

    public RolExistenteException(){
        super("El rol ya esta registrado en el sistema");
    }
}
