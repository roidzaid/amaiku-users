package com.amaiku.users.services;

import com.amaiku.users.entities.UsuarioCuentaRolEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.UsuarioCuentaRolModel;

public interface UsuarioCuentaRolService {

    public void asignarRolAUsuario(UsuarioCuentaRolModel asignarRolModel)
           throws UsuarioNoRegistradoException, CuentaNoRegistradaException, RolNoRegistradoException;

    public UsuarioCuentaRolEntity relacion(Long idUsuario, Long idCuenta, Long IdRol) throws RolYaAsignadoAlUsuarioException;

    public boolean usuarioTieneRolEnCuenta(String usuario, String Cuenta, String rol);
}
