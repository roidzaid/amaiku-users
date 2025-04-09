package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.RolesEntity;
import com.amaiku.users.entities.UsuariosEntity;
import com.ItRoid.serviciousuarios.exceptions.*;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.RecuperarPassModel;
import com.amaiku.users.models.RolesModel;
import com.amaiku.users.models.UsuariosModel;
import com.amaiku.users.repositories.UsuariosRepository;
import com.amaiku.users.services.RolesService;
import com.amaiku.users.services.UsuariosService;
import com.amaiku.users.utils.PassAleatoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RolesService rolesService;

    @Override
    public void crearUsuario(UsuariosModel usuario) throws UsuarioExistenteException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario.getUsuario());

        if (usu==null){

            UsuariosEntity userNuevo = new UsuariosEntity(
                    usuario.getUsuario(),
                    encoder.encode(usuario.getPass()),
                    usuario.getMail(),
                    Date.from(Instant.now()),
                    usuario.getAplicacion(),
                    Date.from(Instant.now()),
                    false);

            this.usuariosRepository.save(userNuevo);
        }else {
            throw new UsuarioExistenteException();

        }

    }

    @Override
    public void updateUsuario(UsuariosModel usuario) throws UsuarioNoRegistradoException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario.getUsuario());

        if (usu!=null){

            usu.setPass(encoder.encode(usuario.getPass()));
            usu.setFechaModif(Date.from(Instant.now()));
            usu.setActualizar(false);

            this.usuariosRepository.save(usu);
        }else {
            throw new UsuarioNoRegistradoException();

        }

    }

    @Override
    public String recuperarPass(RecuperarPassModel recuperarPassModel)
            throws UsuarioNoRegistradoException, MailNoCoincideconElregistradoException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(recuperarPassModel.getUsuario());

        if (usu!=null){

            validarMail(recuperarPassModel.getMail(), usu);

            String passAleatoria = PassAleatoria.getRandomString(6);

            usu.setPass(encoder.encode(passAleatoria));
            usu.setFechaModif(Date.from(Instant.now()));
            usu.setActualizar(true);

            this.usuariosRepository.save(usu);

            return passAleatoria;

        }else {
            throw new UsuarioNoRegistradoException();

        }
    }

    @Override
    public void deleteUsuario(UsuariosModel usuario) throws UsuarioNoRegistradoException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario.getUsuario());

        if (usu!=null){

            this.usuariosRepository.delete(usu);
        }else {
            throw new UsuarioNoRegistradoException();

        }

    }

    @Override
    public UsuariosModel buscarUsuarioLogin(String usuario) throws UsuarioNoRegistradoException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario);

        if (usu!=null){

            UsuariosModel usuariosModel = new UsuariosModel(
                    usu.getUsuario(),
                    usu.getPass(),
                    usu.getAplicacion(),
                    usu.getMail(),
                    usu.isActualizar());

            return usuariosModel;

        }else {
            throw new UsuarioNoRegistradoException();

        }

    }

    @Override
    public UsuariosEntity buscarUsuario(String usuario) throws UsuarioNoRegistradoException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario);

        if (usu!=null){

            return usu;

        }else {
            throw new UsuarioNoRegistradoException();

        }

    }


    @Override
    public void agregarRol(String usuario, RolesModel rol)
            throws UsuarioNoRegistradoException, RolNoRegistradoException, RolYaAsignadoAlUsuarioException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario);

        if (usu != null) {

            RolesEntity r = this.rolesService.buscarRol(rol);

            validarRolAsignado(usu.getUsuariosRoles(), r);

            usu.agregarRol(r);
            this.usuariosRepository.save(usu);
        }else{
            throw new UsuarioNoRegistradoException();
        }
    }

    @Override
    public void quitarRol(String usuario, RolesModel rol)
            throws UsuarioNoRegistradoException, RolNoAsignadoAlUsuarioException, RolNoRegistradoException {

        UsuariosEntity usu = this.usuariosRepository.findByUsuario(usuario);

        if (usu != null) {

            RolesEntity r = this.rolesService.buscarRol(rol);
            validarRolAEliminar(usu.getUsuariosRoles(), rol);

            usu.eliminarRol(r);
            this.usuariosRepository.save(usu);
        }else{
            throw new UsuarioNoRegistradoException();
        }

    }

    private void validarRolAsignado(List<RolesEntity> roles, RolesEntity rol) throws RolYaAsignadoAlUsuarioException {

        for (int i = 0; i < roles.size(); i++){
            if(roles.get(i).getRol().equals(rol.getRol()) &&
               roles.get(i).getAplicacion().equals(rol.getAplicacion())){

                throw new RolYaAsignadoAlUsuarioException();
            }
        }
    }

    private void validarRolAEliminar(List<RolesEntity> roles, RolesModel rol) throws RolNoAsignadoAlUsuarioException {

        boolean rolAsignado = false;
        int i = 0;
        while (i < roles.size() && !rolAsignado){
            if(roles.get(i).getRol().equals(rol.getRol()) &&
                    roles.get(i).getAplicacion().equals(rol.getAplicacion())){

                rolAsignado = true;

            }
            i++;
        }

        if(!rolAsignado) {
            throw new RolNoAsignadoAlUsuarioException();
        }
    }

    private void validarMail(String mail, UsuariosEntity usuariosEntity) throws MailNoCoincideconElregistradoException {

        if (!mail.equals(usuariosEntity.getMail())){
            throw new MailNoCoincideconElregistradoException();
        }

    }

}
