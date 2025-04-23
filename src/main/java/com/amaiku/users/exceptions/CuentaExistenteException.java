package com.amaiku.users.exceptions;

public class CuentaExistenteException extends Exception{

    public CuentaExistenteException(){
        super("La Cuenta ya esta registrada en el sistema");
    }
}
