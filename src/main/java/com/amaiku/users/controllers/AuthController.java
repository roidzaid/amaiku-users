package com.amaiku.users.controllers;

import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.*;
import com.amaiku.users.security.JwtService;
import com.amaiku.users.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuariosService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registro")
    public ResponseEntity<?> guardarUsuario(@RequestBody @Valid RegistroRequestModel registroRequest) throws CuentaNoRegistradaException, UsuarioNoRegistradoException, RolNoRegistradoException, UsuarioExistenteException, RolNoAsignadoAlUsuarioException, RolYaAsignadoAlUsuarioException {

        logger.info("Se guarda usuario: " + registroRequest.getMail());

        this.usuariosService.crearUsuario(registroRequest);
        return new ResponseEntity<String>("Usuario registrado", HttpStatus.OK);

    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestModel loginRequest) {

        logger.info("El usuario: " + loginRequest.getMail() + " se intenta autenticar");

        try {
            String username = loginRequest.getMail(); // ej: juan@Cuenta1
            String password = loginRequest.getPass();
            String Cuenta = loginRequest.getCuenta();

            String composedUsername = username + "-" + Cuenta;

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(composedUsername, password)
            );

            User user = (User) authentication.getPrincipal();

            List<String> rolesList = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtService.createToken(user.getUsername(), rolesList);

            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PutMapping("/recuperarPass")
    public ResponseEntity<?> recuperarPass(@RequestBody @Valid RecuperarPassModel recuperarPassModel) throws UsuarioNoRegistradoException, MailNoCoincideconElregistradoException {

        logger.info("Se usuario " + recuperarPassModel.getMail() + " esta recuperando la pass");

        this.usuariosService.recuperarPass(recuperarPassModel);

        return new ResponseEntity<String>("Te enviamos un mail con las indicaciones para que recuperes tu clave de acceso", HttpStatus.CREATED);

    }

    @GetMapping("/validar-token-recuperacion")
    public ResponseEntity<?> validarToken(@RequestParam String token) {
        try {
            jwtService.verifyRecoveryToken(token); // Solo verifica, si falla lanza excepción
            return ResponseEntity.ok().build(); // Token válido
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido o expirado");
        }
    }


    @PostMapping("/resetear-password")
    public ResponseEntity<?> resetearPassword(@RequestBody @Valid ResetearPassModel model) {

        logger.info("token: " + model.getToken());

        try {
            usuariosService.resetearPass(model.getToken(), model.getNuevaPass());
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
    }
}
