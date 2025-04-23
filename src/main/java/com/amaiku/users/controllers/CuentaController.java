package com.amaiku.users.controllers;

import com.amaiku.users.exceptions.CuentaNoRegistradaException;
import com.amaiku.users.exceptions.UsuarioExistenteException;
import com.amaiku.users.exceptions.UsuarioNoRegistradoException;
import com.amaiku.users.models.CuentaModel;
import com.amaiku.users.models.Rol;
import com.amaiku.users.security.CuentaSecurityService;
import com.amaiku.users.services.CuentaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/cuentas")
public class CuentaController {

    Logger logger = LoggerFactory.getLogger(CuentaController.class);

    @Autowired
    private CuentaService CuentaService;

    @Autowired
    private CuentaSecurityService cuentaSecurityService;

    @PreAuthorize("hasRole('AMAIKU')")
    @PostMapping()
    public ResponseEntity<?> guardarCuenta(@RequestBody @Valid CuentaModel CuentaModel) throws Exception  {

        logger.info("Se guarda nueva Cuenta: " + CuentaModel.getNombre());

        try {

            this.CuentaService.crearCuenta(CuentaModel);
            return new ResponseEntity<String>("Cuenta registrada", HttpStatus.OK);

        } catch (UsuarioExistenteException existenteException){
            return new ResponseEntity<String>("Cuenta existente", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al crear el Cuenta", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasAnyRole('AMAIKU', 'ADMIN_CUENTA')")
    @PutMapping()
    public ResponseEntity<?> updateCuenta(@RequestBody @Valid CuentaModel CuentaModel, HttpServletRequest request) throws Exception  {

        logger.info("Se actualiza Cuenta: " + CuentaModel.getNombre());

        try {

            if (request.isUserInRole("ADMIN_CUENTA")) {
                if (!cuentaSecurityService.tieneAcceso(request, Rol.ADMIN_CUENTA.getValor())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado para esa cuenta");
                }
            }

            this.CuentaService.updateCuenta(CuentaModel);

            return new ResponseEntity<String>("Cuenta actualizado", HttpStatus.CREATED);

        } catch (UsuarioNoRegistradoException usuarioNoRegistradoException){
            return new ResponseEntity<String>("Cuenta no existe", HttpStatus.FOUND);
        } catch (Exception e){
            return new ResponseEntity<String>("Error al actualizar Cuenta", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
