package com.amaiku.users.services;

import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.*;

public interface UsuarioService {

    public void crearUsuario(RegistroRequestModel usuario) throws UsuarioExistenteException, CuentaNoRegistradaException, UsuarioNoRegistradoException, RolNoRegistradoException, RolNoAsignadoAlUsuarioException, RolYaAsignadoAlUsuarioException;
    public void updateUsuario(EstadoUsuarioModel estadoUsuarioModel) throws UsuarioNoRegistradoException;
    public UsuarioModel buscarUsuario(String usuario) throws UsuarioNoRegistradoException;
    public UsuarioEntity getUsuarioOrThrow(String usuario) throws UsuarioNoRegistradoException;

    public void recuperarPass(RecuperarPassModel recuperarPassModel) throws UsuarioNoRegistradoException, MailNoCoincideconElregistradoException;
    public void resetearPass(String token, String newPassword) throws Exception;
}
