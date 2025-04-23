package com.amaiku.users.exceptions;

public class CuentaNoRegistradaException extends Exception{

    public CuentaNoRegistradaException(){
        super("La Cuenta no esta registrada en el sistema");

    }
}
