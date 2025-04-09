package com.amaiku.users.security;

import com.amaiku.users.entities.UsuariosEntity;
import com.amaiku.users.services.UsuariosService;
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

@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String s) {

        UsuariosEntity u = null;
        User user = null;
        try {
            u = this.usuariosService.buscarUsuario(s);

            if (u != null) {

                List<String> roles = new ArrayList<>();
                for (int i= 0; i> u.getUsuariosRoles().size(); i++) {

                    roles.add(u.getUsuariosRoles().get(i).getRol());
                }

                user = userBuilder(u.getUsuario(), u.getPass(), roles);

            } else {
                throw new UsernameNotFoundException("Usuario no registrado");

            }

            return user;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
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