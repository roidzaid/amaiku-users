package com.amaiku.users.security;

import com.amaiku.users.services.UsuarioCuentaRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CuentaSecurityService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioCuentaRolService usuarioCuentaRolService;

    public boolean tieneAcceso(HttpServletRequest request, String rolRequerido) throws Exception {
        String token = request.getHeader("Authorization");
        String cuenta = request.getHeader("Cuenta");
        String usuarioLogueado = jwtService.user(token);

        return usuarioCuentaRolService.usuarioTieneRolEnCuenta(usuarioLogueado, cuenta, rolRequerido);
    }
}
