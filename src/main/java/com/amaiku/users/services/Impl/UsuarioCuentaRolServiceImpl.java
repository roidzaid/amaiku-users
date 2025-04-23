package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.CuentaEntity;
import com.amaiku.users.entities.RolEntity;
import com.amaiku.users.entities.UsuarioCuentaRolEntity;
import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.Estado;
import com.amaiku.users.models.UsuarioCuentaRolModel;
import com.amaiku.users.repositories.CuentaRepository;
import com.amaiku.users.repositories.RolRepository;
import com.amaiku.users.repositories.UsuarioCuentaRolRepository;
import com.amaiku.users.repositories.UsuarioRepository;
import com.amaiku.users.services.UsuarioCuentaRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioCuentaRolServiceImpl implements UsuarioCuentaRolService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CuentaRepository CuentaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioCuentaRolRepository usuarioCuentaRolRepository;

    @Override
    public void asignarRolAUsuario(UsuarioCuentaRolModel relacion) throws UsuarioNoRegistradoException, CuentaNoRegistradaException, RolNoRegistradoException {

        UsuarioEntity usuario = Optional.ofNullable(usuarioRepository.findUsuario(relacion.getMail(),
                Estado.PENDIENTE.getValor()))
                .orElseThrow(UsuarioNoRegistradoException::new);

        CuentaEntity Cuenta = Optional.ofNullable(CuentaRepository.findByNombre(relacion.getCuenta()))
                .orElseThrow(CuentaNoRegistradaException::new);

        RolEntity rol = Optional.ofNullable(rolRepository.findByRol(relacion.getRol()))
                .orElseThrow(RolNoRegistradoException::new);

        UsuarioCuentaRolEntity ucr = new UsuarioCuentaRolEntity();
        ucr.setUsuario(usuario);
        ucr.setCuenta(Cuenta);
        ucr.setRol(rol);
        ucr.setEstado(Estado.ACTIVO.getValor());
        ucr.setFechaAlta(new Date());
        ucr.setFechaModificacion(new Date());

        usuarioCuentaRolRepository.save(ucr);

    }




    @Override
    public UsuarioCuentaRolEntity relacion(Long idUsuario, Long idCuenta, Long IdRol) throws RolYaAsignadoAlUsuarioException {
        return usuarioCuentaRolRepository.findByUsuarioAndCuentaAndRol(idUsuario, idCuenta, IdRol);

    }

    @Override
    public boolean usuarioTieneRolEnCuenta(String usuario, String Cuenta, String rol) {

        UsuarioEntity usuarioEntity = usuarioRepository.findUsuario(usuario, Estado.ACTIVO.getValor());

        CuentaEntity CuentaEntity = CuentaRepository.findByNombre(Cuenta);

        RolEntity rolEntity = rolRepository.findByRol(rol);


        UsuarioCuentaRolEntity relacion = usuarioCuentaRolRepository.findByUsuarioAndCuentaAndRol(usuarioEntity.getIdUsuario(), CuentaEntity.getIdCuenta(), rolEntity.getIdRol());

        if (relacion == null){
            return false;
        }else {
            return true;
        }
    }


    private UsuarioCuentaRolEntity UsuarioAndCuentaAndRol(Long idUsuario, Long idCuenta, Long IdRol) throws RolNoAsignadoAlUsuarioException {
        return Optional.ofNullable(usuarioCuentaRolRepository.findByUsuarioAndCuentaAndRol(idUsuario, idCuenta, IdRol))
                .orElseThrow(RolNoAsignadoAlUsuarioException::new);
    }



}
