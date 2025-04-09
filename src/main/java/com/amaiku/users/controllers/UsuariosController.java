package com.amaiku.users.controllers;

import com.ItRoid.serviciousuarios.exceptions.*;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.RecuperarPassModel;
import com.amaiku.users.models.RolesModel;
import com.amaiku.users.models.UsuariosModel;
import com.amaiku.users.security.JwtService;
import com.amaiku.users.services.MailsService;
import com.amaiku.users.services.UsuariosService;
import com.amaiku.users.utils.PlantillasMails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    @RequestMapping("usuarios")
public class UsuariosController {

    Logger logger = LoggerFactory.getLogger(UsuariosController.class);

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private MailsService mailsService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User usuario) {

        logger.info("El usuario: " + usuario.getUsername() + " se autentico correctamente");

        List<String> rolesList = usuario
                .getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        return jwtService.createToken(usuario.getUsername(), rolesList);

    }

    @GetMapping("/buscarUsuario/{usuario}")
    public ResponseEntity<?> buscarUsuario(@PathVariable("usuario") String usuario) {

        logger.info("Se busca usuario: " + usuario);

        try {

            UsuariosModel usuariosModel = this.usuariosService.buscarUsuarioLogin(usuario);

            return new ResponseEntity<UsuariosModel>(usuariosModel, HttpStatus.OK);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario inexistente", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al intentar recuperar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    @PutMapping("/recuperarPass")
    public ResponseEntity<?> recuperarPass(@RequestBody RecuperarPassModel recuperarPassModel) {

        logger.info("Se usuario " + recuperarPassModel.getUsuario() + " esta recuperando la pass");

        try {

            String passAleatoria = this.usuariosService.recuperarPass(recuperarPassModel);

            PlantillasMails plantilla = new PlantillasMails();

            mailsService.enviarMail(recuperarPassModel.getMail(), plantilla.crearPlantillaRecuperacionPass(passAleatoria));

            return new ResponseEntity<String>("Se envió una nueva pass a su mail, por favor cambiela cuando ingresa a la aplicación", HttpStatus.CREATED);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario inexistente", HttpStatus.NOT_FOUND);
        } catch (MailNoCoincideconElregistradoException mailNoCoincideconElregistradoException){
            return new ResponseEntity<String>("El mail ingresado no coincide con el registrado", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al intentar recuperar pass", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping()
    public ResponseEntity<?> guardarUsuario(@RequestBody UsuariosModel usuarioModel) throws Exception  {

        logger.info("Se guarda usuario: " + usuarioModel.getUsuario() + " para aplicacion: " + usuarioModel.getAplicacion());

        try {

            this.usuariosService.crearUsuario(usuarioModel);
            return new ResponseEntity<String>("Usuario registrado", HttpStatus.OK);

        } catch (UsuarioExistenteException existenteException){
            return new ResponseEntity<String>("Usuario existente", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al crear el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping()
    public ResponseEntity<?> updateUsuario(@RequestBody UsuariosModel usuarioModel) throws Exception  {

        logger.info("Se actualiza usuario: " + usuarioModel.getUsuario() + " para aplicacion: " + usuarioModel.getAplicacion());

        try {

            this.usuariosService.updateUsuario(usuarioModel);
            return new ResponseEntity<String>("Usuario actualizado", HttpStatus.CREATED);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario no existe", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al actualizar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping()
    public ResponseEntity<?> eliminarUsuario(@RequestBody UsuariosModel usuarioModel) throws Exception  {

        logger.info("Se elimina el usuario: " + usuarioModel.getUsuario() + " para aplicacion: " + usuarioModel.getAplicacion());

        try {

            this.usuariosService.deleteUsuario(usuarioModel);

            return new ResponseEntity<String>("Usuario eliminado", HttpStatus.CREATED);
        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario no registrado", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al eliminar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/AddRol/{usuario}")
    public ResponseEntity<?> agregarRol(@PathVariable("usuario") String usuario, @RequestBody RolesModel rolesModel) throws Exception  {

        logger.info("se agregar rol al usuario : " + usuario);

        try {

            this.usuariosService.agregarRol(usuario, rolesModel);
            return new ResponseEntity<String>("Rol asigando", HttpStatus.CREATED);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario no registrado", HttpStatus.NOT_FOUND);
        } catch (RolNoRegistradoException rolNoRegistradoException){
            return new ResponseEntity<String>("Rol no registrado", HttpStatus.NOT_FOUND);
        } catch (RolYaAsignadoAlUsuarioException rolYaAsignadoAlUsuarioException){
            return new ResponseEntity<String>("Rol ya asignado al usuario", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al crear el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/removeRol/{usuario}")
    public ResponseEntity<?> quitarRol(@PathVariable("usuario") String usuario, @RequestBody RolesModel rolesModel) throws Exception  {

        logger.info("se elimina el rol : " + rolesModel.getRol()  + " de usuario : " + usuario);

        try {

            this.usuariosService.quitarRol(usuario, rolesModel);
            return new ResponseEntity<String>("Rol eliminado del usuario", HttpStatus.CREATED);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario no registrado", HttpStatus.NOT_FOUND);
        } catch (RolNoRegistradoException rolNoRegistradoException){
            return new ResponseEntity<String>("Rol no registrado", HttpStatus.NOT_FOUND);
        } catch (RolNoAsignadoAlUsuarioException rolNoAsignadoAlUsuarioException){
            return new ResponseEntity<String>("El usuario no posee ese rol", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al crear el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
