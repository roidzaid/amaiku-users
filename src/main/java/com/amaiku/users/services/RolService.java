package com.amaiku.users.services;

import com.amaiku.users.entities.RolEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.RolModel;

public interface RolService {

    public RolModel buscarRol(String rol) throws RolNoRegistradoException;

}
