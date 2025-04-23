package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.RolEntity;
import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.RolModel;
import com.amaiku.users.models.UsuarioModel;
import com.amaiku.users.repositories.RolRepository;
import com.amaiku.users.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolesServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;


    @Override
    public RolModel buscarRol(String nombre) throws RolNoRegistradoException {
        RolEntity entity = getRolOrThrow(nombre);

        return new RolModel(
                entity.getIdRol(),
                entity.getNombre()

        );
    }

    private RolEntity getRolOrThrow(String nombre) throws RolNoRegistradoException {
        return Optional.ofNullable(rolRepository.findByRol(nombre))
                .orElseThrow(RolNoRegistradoException::new);
    }
}
