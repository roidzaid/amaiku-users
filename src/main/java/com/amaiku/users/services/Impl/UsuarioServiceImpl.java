package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.*;
import com.amaiku.users.repositories.UsuarioRepository;
import com.amaiku.users.security.JwtService;
import com.amaiku.users.services.CuentaService;
import com.amaiku.users.services.RolService;
import com.amaiku.users.services.UsuarioCuentaRolService;
import com.amaiku.users.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuariosRepository;

    @Autowired
    private CuentaService CuentaService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioCuentaRolService usuarioCuentaRolService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RolService rolesService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EnvioMails envioMails;

    @Override
    public void crearUsuario(RegistroRequestModel registroRequestModel) throws UsuarioExistenteException, CuentaNoRegistradaException, UsuarioNoRegistradoException, RolNoRegistradoException, RolYaAsignadoAlUsuarioException {

        CuentaModel CuentaModel = CuentaService.buscarCuentaByNombre(registroRequestModel.getCuenta());

        RolModel rolModel = new RolModel();

        if (registroRequestModel.getRol() != null && registroRequestModel.getRol() != "") {
            rolModel = rolService.buscarRol(registroRequestModel.getRol());
        } else {
            rolModel = rolService.buscarRol(Rol.PACIENTE.getValor());
        }

        UsuarioEntity usuarioModel = usuariosRepository.findUsuario(registroRequestModel.getMail(), Estado.ACTIVO.getValor());

        if (usuarioModel != null){
            if (usuarioCuentaRolService.relacion(usuarioModel.getIdUsuario(), CuentaModel .getIdCuenta(), rolModel.getIdRol()) !=null){
                throw new UsuarioExistenteException();
            }
        } else {

            UsuarioEntity userNuevo = new UsuarioEntity(
                    registroRequestModel.getMail(),
                    encoder.encode(registroRequestModel.getPass()),
                    Date.from(Instant.now()),
                    Date.from(Instant.now()),
                    Estado.PENDIENTE.getValor()
            );

            this.usuariosRepository.save(userNuevo);
        }

        UsuarioCuentaRolModel relacion = new UsuarioCuentaRolModel(
                registroRequestModel.getMail(),
                rolModel.getNombre(),
                registroRequestModel.getCuenta()
        );

        this.usuarioCuentaRolService.asignarRolAUsuario(relacion);
    }

    @Override
    public void updateUsuario(EstadoUsuarioModel estadoUsuarioModel) throws UsuarioNoRegistradoException {

        UsuarioEntity usu = getUsuarioSinEstadoOrThrow(estadoUsuarioModel.getMail());

        usu.setEstado(estadoUsuarioModel.getEstado());
        usu.setFechaModif(Date.from(Instant.now()));

        usuariosRepository.save(usu);

    }

    @Override
    public UsuarioModel buscarUsuario(String usuario) throws UsuarioNoRegistradoException {

        UsuarioEntity usu = getUsuarioSinEstadoOrThrow(usuario);

        return new UsuarioModel(
                usu.getIdUsuario(),
                usu.getMail(),
                usu.getPass(),
                usu.getFechaAlta(),
                usu.getFechaModif(),
                usu.getEstado()
        );

    }

    @Override
    public UsuarioEntity getUsuarioOrThrow(String mail) throws UsuarioNoRegistradoException {
        return Optional.ofNullable(usuariosRepository.findUsuario(mail, Estado.ACTIVO.getValor()))
                .orElseThrow(UsuarioNoRegistradoException::new);

    }

    public UsuarioEntity getUsuarioSinEstadoOrThrow(String mail) throws UsuarioNoRegistradoException {
        return Optional.ofNullable(usuariosRepository.findUsuarioSinEstado(mail))
                .orElseThrow(UsuarioNoRegistradoException::new);

    }



    @Override
    public void recuperarPass(RecuperarPassModel recuperarPassModel)
            throws UsuarioNoRegistradoException, MailNoCoincideconElregistradoException {

        UsuarioEntity usu = getUsuarioOrThrow(recuperarPassModel.getMail());

        validarMail(recuperarPassModel.getMail(), usu.getMail());

        // 1. Generar JWT
        String token = jwtService.createRecoveryToken(usu.getMail());

        // 2. Crear link
        String recoveryLink = "https://amaiku.com/recuperar-password?token=" + token;

        // 3. Enviar mail
        String plantilla = "<h2>Recuperación de Contraseña</h2>"
                + "<p>Hola,</p>"
                + "<p>Hacé clic en el siguiente enlace para cambiar tu contraseña:</p>"
                + "<p><a href=\"" + recoveryLink + "\">Restablecer Contraseña</a></p>"
                + "<p>Este enlace expirará en 15 minutos.</p>";

        envioMails.enviarMail(usu.getMail(), plantilla);

    }

    private void validarMail(String mailInput, String mailUsuario) throws MailNoCoincideconElregistradoException {

        if (!mailInput.equals(mailUsuario)){
            throw new MailNoCoincideconElregistradoException();
        }

    }

    @Override
    public void resetearPass(String token, String newPassword) throws Exception {

        System.out.println("🔐 Token recibido en resetearPass: " + token);

        // 1. Validar y parsear el token
        String username = jwtService.extractUsername(token); // <-- acá recuperás el usuario

        // 2. Buscar el usuario
        UsuarioEntity usu = getUsuarioOrThrow(username);

        // 3. Setear nueva pass
        usu.setPass(encoder.encode(newPassword));

        usuariosRepository.save(usu);
    }


}
