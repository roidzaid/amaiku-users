package com.amaiku.users.exceptions;

public class UsuarioExistenteException extends Exception{

    public UsuarioExistenteException(){
        super("El usuario ya se encuentra registrado");
    }
}
