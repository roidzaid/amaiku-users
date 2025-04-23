package com.amaiku.users.exceptions;

public class UsuarioNoRegistradoException extends Exception{

    public UsuarioNoRegistradoException(){
        super("El usuario no se encuentra registrado");
    }
}
