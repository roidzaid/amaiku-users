package com.amaiku.users.services;

import com.amaiku.users.exceptions.CuentaExistenteException;
import com.amaiku.users.exceptions.CuentaNoRegistradaException;
import com.amaiku.users.exceptions.UsuarioExistenteException;
import com.amaiku.users.exceptions.UsuarioNoRegistradoException;
import com.amaiku.users.models.CuentaModel;

public interface CuentaService {

    public void crearCuenta(CuentaModel CuentaModel) throws UsuarioExistenteException, CuentaExistenteException;
    public void updateCuenta(CuentaModel CuentaModel) throws UsuarioNoRegistradoException, CuentaNoRegistradaException;
    public CuentaModel buscarCuentaByNombre(String subdominio) throws UsuarioNoRegistradoException, CuentaNoRegistradaException;
}
