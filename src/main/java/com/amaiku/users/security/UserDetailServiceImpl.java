package com.amaiku.users.security;

import com.amaiku.users.entities.UsuarioCuentaRolEntity;
import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.exceptions.UsuarioNoRegistradoException;
import com.amaiku.users.models.Estado;
import com.amaiku.users.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuariosService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String composedUsername) {

        try {

            // Se espera algo como "juan@Cuenta1"
            String[] parts = composedUsername.split("-");

            if (parts.length != 2) {
                throw new UsernameNotFoundException("Formato inválido. Use usuario@Cuenta");
            }

            String username = parts[0];
            String subdominio = parts[1];


            UsuarioEntity u = this.usuariosService.getUsuarioOrThrow(username);

            // Filtrás los roles según la clínica
            List<String> roles = u.getCuentasRoles().stream()
                    .filter(rel -> rel.getCuenta().getSubdomino().equals(subdominio))
                    .filter(rel -> Estado.ACTIVO.getValor().equals(rel.getEstado()))
                    .map(rel -> rel.getRol().getNombre())
                    .collect(Collectors.toList());

            if (roles.isEmpty()) {
                throw new UsernameNotFoundException("No hay roles activos para esta clínica");
            }

            return userBuilder(u.getMail(), u.getPass(), roles);

        } catch (UsuarioNoRegistradoException e) {
            throw new UsernameNotFoundException("Usuario no registrado", e);
        }
    }

    private User userBuilder(String usuario, String clave, List<String> roles){

        boolean enable = true;
        boolean accountNonExpired = true;
        boolean credentianNonExpired = true;
        boolean accounNonLocked = true;

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String rol : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));
        }

        return new User(usuario, clave, enable, accountNonExpired, credentianNonExpired, accounNonLocked, authorities);

    }
}