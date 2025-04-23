package com.amaiku.users.exceptions;

import com.amaiku.users.controllers.UsuarioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @PostConstruct
    public void init() {
        logger.info("✅ GlobalExceptionHandler registrado correctamente");
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<String> handleUsuarioExistente(UsuarioExistenteException e) {
        logger.error("\u274C Usuario ya existe");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UsuarioNoRegistradoException.class)
    public ResponseEntity<String> handleUsuarioNoRegistrado(UsuarioNoRegistradoException e) {
        logger.error("\u274C Usuario no esta registrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CuentaNoRegistradaException.class)
    public ResponseEntity<String> handleCuentaNoRegistrada(CuentaNoRegistradaException e) {
        logger.error("\u274C Clinina no esta registrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CuentaExistenteException.class)
    public ResponseEntity<String> handleCuentaExistente(CuentaExistenteException e) {
        logger.error("\u274C Clinina ya esta registrada");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RolNoRegistradoException.class)
    public ResponseEntity<String> handleRolNoRegistrado(RolNoRegistradoException e) {
        logger.error("\u274C Rol no esta registrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RolExistenteException.class)
    public ResponseEntity<String> handleRolExistente(RolExistenteException e) {
        logger.error("\u274C Rol ya esta registrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RolNoAsignadoAlUsuarioException.class)
    public ResponseEntity<String> handleRolNoAsignadoAlUsuario(RolNoAsignadoAlUsuarioException e) {
        logger.error("\u274C Rol no asignado al usuario");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RolYaAsignadoAlUsuarioException.class)
    public ResponseEntity<String> handleRolYaAsignadoAlUsuario(RolYaAsignadoAlUsuarioException e) {
        logger.error("\u274C Rol ya esta asignado al usuario");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(MailNoCoincideconElregistradoException.class)
    public ResponseEntity<String> handleMailNoCoincideconElregistrado(MailNoCoincideconElregistradoException e) {
        logger.error("\u274C Mail ingresado no coincide con el registrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // Si querés capturar otras excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        logger.error("❌ Error de validación: {}", errores);

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
