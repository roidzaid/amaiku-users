package com.amaiku.users.services;

import com.amaiku.users.entities.UsuariosEntity;
import com.ItRoid.serviciousuarios.exceptions.*;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.RecuperarPassModel;
import com.amaiku.users.models.RolesModel;
import com.amaiku.users.models.UsuariosModel;

public interface UsuariosService {

    public void crearUsuario(UsuariosModel usuario) throws UsuarioExistenteException;
    public void deleteUsuario(UsuariosModel usuario) throws UsuarioNoRegistradoException;
    public void agregarRol(String usuario, RolesModel rol) throws UsuarioNoRegistradoException, RolNoRegistradoException, RolYaAsignadoAlUsuarioException;
    public void quitarRol(String usuario, RolesModel rol) throws UsuarioNoRegistradoException, RolNoAsignadoAlUsuarioException, RolNoRegistradoException;
    public UsuariosModel buscarUsuarioLogin(String usuario) throws UsuarioNoRegistradoException;
    public UsuariosEntity buscarUsuario(String usuario) throws UsuarioNoRegistradoException;
    public void updateUsuario(UsuariosModel usuario) throws UsuarioNoRegistradoException;
    public String recuperarPass(RecuperarPassModel recuperarPassModel) throws UsuarioNoRegistradoException, MailNoCoincideconElregistradoException;

}
