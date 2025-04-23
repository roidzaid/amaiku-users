package com.amaiku.users.controllers;

import com.amaiku.users.exceptions.UsuarioExistenteException;
import com.amaiku.users.exceptions.UsuarioNoRegistradoException;
import com.amaiku.users.models.EstadoUsuarioModel;
import com.amaiku.users.models.Rol;
import com.amaiku.users.models.UsuarioModel;
import com.amaiku.users.security.CuentaSecurityService;
import com.amaiku.users.security.JwtService;
import com.amaiku.users.services.MailsService;
import com.amaiku.users.services.UsuarioCuentaRolService;
import com.amaiku.users.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/usuarios")
public class
UsuarioController {

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuariosService;

    @Autowired
    private UsuarioCuentaRolService usuarioCuentaRolService;

    @Autowired
    private CuentaSecurityService cuentaSecurityService;

    @Autowired
    private JwtService jwtService;



    @PreAuthorize("hasRole('AMAIKU')")
    @GetMapping("/{usuario}")
    public ResponseEntity<?> buscarUsuario(@PathVariable("usuario") String usuario, HttpServletRequest request) {

        logger.info("Se busca usuario: " + usuario);

        try {

            UsuarioModel usuariosModel = this.usuariosService.buscarUsuario(usuario);

            return new ResponseEntity<UsuarioModel>(usuariosModel, HttpStatus.OK);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario inexistente", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al intentar recuperar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('AMAIKU')")
    @PutMapping()
    public ResponseEntity<?> estadoUsuario(@RequestBody @Valid EstadoUsuarioModel estadoUsuarioModel,
                                            HttpServletRequest request) throws Exception  {

        logger.info("Se actualiza usuario: " + estadoUsuarioModel.getMail() + "a " + estadoUsuarioModel.getEstado());

        try {

            this.usuariosService.updateUsuario(estadoUsuarioModel);
            return new ResponseEntity<String>("Usuario actualizado", HttpStatus.CREATED);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Usuario no existe", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al actualizar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
