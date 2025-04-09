package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.RolesEntity;
import com.amaiku.users.exceptions.RolExistenteException;
import com.amaiku.users.exceptions.RolNoRegistradoException;
import com.amaiku.users.models.RolesModel;
import com.amaiku.users.repositories.RolesRepository;
import com.amaiku.users.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void crearRol(RolesModel rol) throws RolExistenteException {

        RolesEntity r = this.rolesRepository.findByRol(rol.getRol(), rol.getAplicacion());

        if(r==null){
            RolesEntity rolNuevo = new RolesEntity(
                    rol.getRol(),
                    rol.getAplicacion()
            );


            this.rolesRepository.save(rolNuevo);
        }else {
            throw new RolExistenteException();

        }

    }

    @Override
    public void deleteRol(RolesModel rol) throws RolNoRegistradoException {

        RolesEntity r = this.rolesRepository.findByRol(rol.getRol(), rol.getAplicacion());

        if(r!=null){
            this.rolesRepository.delete(r);
        }else{
            throw new RolNoRegistradoException();
        }

    }

    @Override
    public RolesEntity buscarRol(RolesModel rol) throws RolNoRegistradoException {

        RolesEntity r = this.rolesRepository.findByRol(rol.getRol(), rol.getAplicacion());

        if(r!=null){
           return r;
        }else{
            throw new RolNoRegistradoException();
        }

    }
}
