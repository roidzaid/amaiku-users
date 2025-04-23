package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.UsuarioCuentaRolEntity;
import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.*;
import com.amaiku.users.repositories.UsuarioRepository;
import com.amaiku.users.security.JwtService;
import com.amaiku.users.services.CuentaService;
import com.amaiku.users.services.RolService;
import com.amaiku.users.services.UsuarioCuentaRolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioCuentaRolService usuarioCuentaRolService;

    @Mock
    private CuentaServiceImpl CuentaService;


    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RolService rolService;

    @Mock
    private JwtService jwtService;

    @Mock
    private EnvioMails envioMails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void crearUsuario_deberiaCrearSiNoExiste() throws UsuarioExistenteException, CuentaNoRegistradaException, UsuarioNoRegistradoException, RolNoRegistradoException, RolYaAsignadoAlUsuarioException {
        // Arrange
        RegistroRequestModel model = new RegistroRequestModel();
        model.setPass("pass");
        model.setMail("test@mail.com");
        model.setCuenta("Cuenta1");
        model.setRol("TEST");

        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(null);
        when(CuentaService.buscarCuentaByNombre("Cuenta1")).thenReturn(new CuentaModel());
        when(rolService.buscarRol("TEST")).thenReturn(new RolModel("TEST"));
        when(usuarioCuentaRolService.relacion(any(), any(), any())).thenReturn(null);
        when(encoder.encode("pass")).thenReturn("hashedpass");

        // Act
        usuarioService.crearUsuario(model);

        // Assert
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
        verify(usuarioCuentaRolService, times(1)).asignarRolAUsuario(any(UsuarioCuentaRolModel.class));

    }

    @Test
    void crearUsuario_deberiaLanzarExcepcionSiExiste() throws Exception {
        // Arrange
        RegistroRequestModel model = new RegistroRequestModel();
        model.setMail("test@mail.com");
        model.setPass("password");
        model.setCuenta("Cuenta1");
        model.setRol("MEDICO");

        // Simular que el usuario ya existe
        UsuarioEntity usuarioExistente = new UsuarioEntity();
        usuarioExistente.setIdUsuario(1L);
        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(usuarioExistente);

        // Simular que la clínica existe
        CuentaModel CuentaModel = new CuentaModel();
        CuentaModel.setIdCuenta(1L);
        when(CuentaService.buscarCuentaByNombre("Cuenta1")).thenReturn(CuentaModel);

        // Simular que el rol existe
        RolModel rolModel = new RolModel();
        rolModel.setIdRol(2L);
        rolModel.setNombre("MEDICO");
        when(rolService.buscarRol("MEDICO")).thenReturn(rolModel);

        // Simular que ya existe la relación usuario-clínica-rol
        UsuarioCuentaRolEntity relacionExistente = new UsuarioCuentaRolEntity();
        when(usuarioCuentaRolService.relacion(1L, 1L, 2L)).thenReturn(relacionExistente);

        // Act & Assert
        assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.crearUsuario(model);
        });

        // Verifica que NO se llamó a guardar el nuevo usuario
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void updateUsuario_deberiaModificarSiExiste() throws UsuarioNoRegistradoException {

        // Arrange
        EstadoUsuarioModel model = new EstadoUsuarioModel();
        model.setMail("test@mail.com");
        model.setEstado(Estado.ACTIVO.getValor());

        UsuarioEntity entity = new UsuarioEntity();
        entity.setPass("pass");
        entity.setMail("test@mail.com");
        entity.setFechaAlta(Date.from(Instant.now()));
        entity.setFechaModif(Date.from(Instant.now()));
        entity.setEstado(Estado.PENDIENTE.getValor());

        when(usuarioRepository.findUsuarioSinEstado("test@mail.com")).thenReturn(entity);

        usuarioService.updateUsuario(model);

        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
    }

    @Test
    void updateUsuario_deberiaLanzarExcepcionSiNoExiste() {

        EstadoUsuarioModel model = new EstadoUsuarioModel();
        model.setMail("test@mail.com");

        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(null);

        assertThrows(UsuarioNoRegistradoException.class, () -> {
            usuarioService.updateUsuario(model);
        });

        verify(usuarioRepository, never()).delete(any());


    }


    @Test
    void buscarUsuario_deberiaDevolverDatosSiExiste() throws UsuarioNoRegistradoException {

        UsuarioEntity entity = new UsuarioEntity();
         entity.setPass("pass");
        entity.setMail("test@mail.com");
        entity.setFechaAlta(Date.from(Instant.now()));
        entity.setFechaModif(Date.from(Instant.now()));

        when(usuarioRepository.findUsuarioSinEstado("test@mail.com")).thenReturn(entity);

        UsuarioModel model = usuarioService.buscarUsuario("test@mail.com");

        assertEquals("pass", model.getPass());
        assertEquals("test@mail.com", model.getMail());
        assertEquals(entity.getFechaAlta(), model.getFechaAlta());
        assertEquals(entity.getFechaModif(), model.getFechaModif());

        verify(usuarioRepository, times(1)).findUsuarioSinEstado("test@mail.com");


    }

    @Test
    void buscarUsuario_deberiaLanzarExcepcionSiNoExiste() {

        String usuarioNoExiste = "test@mail.com";

        when(usuarioRepository.findUsuario(usuarioNoExiste, Estado.ACTIVO.getValor())).thenReturn(null);

        assertThrows(UsuarioNoRegistradoException.class, () -> {
            usuarioService.buscarUsuario(usuarioNoExiste);
        });

        verify(usuarioRepository, times(1)).findUsuarioSinEstado("test@mail.com");

    }

    @Test
    void getUsuarioOrThrow_deberiaDevolcerDatosSiExiste() throws UsuarioNoRegistradoException {
        // Arrange
        UsuarioEntity usuarioMock = new UsuarioEntity();
        usuarioMock.setMail("test@mail.com");

        when(usuarioRepository.findUsuarioSinEstado("test@mail.com")).thenReturn(usuarioMock);

        // Act
        UsuarioEntity result = usuarioService.getUsuarioSinEstadoOrThrow("test@mail.com");

        // Assert
        assertNotNull(result);
        assertEquals("test@mail.com", result.getMail());
    }

    @Test
    void getUsuarioOrThrow__deberiaLanzarExcepcionSiNoExiste() {

        // Arrange
        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(null);

        // Act & Assert
        assertThrows(UsuarioNoRegistradoException.class, () -> {
            usuarioService.getUsuarioOrThrow("test@mail.com");
        });
    }

    @Test
    void recuperarPass_deberiaEnviarMailSiUsuarioYMailSonCorrectos() throws Exception {
        // Arrange
        RecuperarPassModel model = new RecuperarPassModel("test@mail.com");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setMail("test@mail.com");

        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(usuario);
        when(jwtService.createRecoveryToken("test@mail.com")).thenReturn("fake-token");

        // Act
        usuarioService.recuperarPass(model);

        // Assert
        verify(jwtService).createRecoveryToken("test@mail.com");
        verify(envioMails).enviarMail(eq("test@mail.com"), contains("Restablecer Contraseña"));
    }

    @Test
    void recuperarPass_deberiaLanzarExcepcionSiUsuarioNoExiste() {
        // Arrange
        RecuperarPassModel model = new RecuperarPassModel("test@mail.com");

        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(null);

        // Act & Assert
        assertThrows(UsuarioNoRegistradoException.class, () -> {
            usuarioService.recuperarPass(model);
        });
    }

    @Test
    void recuperarPass_deberiaLanzarExcepcionSiElMailNoCoincide() {
        // Arrange
        RecuperarPassModel model = new RecuperarPassModel("test@mail.com");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setMail("original@mail.com");

        when(usuarioRepository.findUsuario("test@mail.com", Estado.ACTIVO.getValor())).thenReturn(usuario);

        // Act & Assert
        assertThrows(MailNoCoincideconElregistradoException.class, () -> {
            usuarioService.recuperarPass(model);
        });
    }
}