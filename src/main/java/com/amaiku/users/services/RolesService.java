package com.amaiku.users.services;

import com.amaiku.users.entities.RolesEntity;
import com.amaiku.users.exceptions.RolExistenteException;
import com.amaiku.users.exceptions.RolNoRegistradoException;
import com.amaiku.users.models.RolesModel;

public interface RolesService {

    public void crearRol(RolesModel rol) throws RolExistenteException;
    public void deleteRol(RolesModel rol) throws RolNoRegistradoException;

    public RolesEntity buscarRol(RolesModel rol) throws RolNoRegistradoException;
}
