package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.CuentaEntity;
import com.amaiku.users.entities.RolEntity;
import com.amaiku.users.entities.UsuarioCuentaRolEntity;
import com.amaiku.users.entities.UsuarioEntity;
import com.amaiku.users.models.Estado;
import com.amaiku.users.models.Rol;
import com.amaiku.users.models.UsuarioCuentaRolModel;
import com.amaiku.users.repositories.CuentaRepository;
import com.amaiku.users.repositories.RolRepository;
import com.amaiku.users.repositories.UsuarioCuentaRolRepository;
import com.amaiku.users.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioCuentaRolServiceImplTest {

    @Mock
    private UsuarioCuentaRolRepository usuarioCuentaRolRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CuentaRepository CuentaRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioCuentaRolServiceImpl usuarioCuentaRolService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void asignarRolAUsuario_deberiaGuardarRelacionSiTodoExiste() throws Exception {
        // Arrange
        UsuarioCuentaRolModel relacion = new UsuarioCuentaRolModel("test@mail.com", Rol.AMAIKU.getValor(), "Cuenta");

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1L);

        RolEntity rol = new RolEntity();
        rol.setIdRol(1L);

        CuentaEntity Cuenta = new CuentaEntity();
        Cuenta.setIdCuenta(1L);

        when(usuarioRepository.findUsuario("test@mail.com", Estado.PENDIENTE.getValor())).thenReturn(usuario);
        when(CuentaRepository.findByNombre("Cuenta")).thenReturn(Cuenta);
        when(rolRepository.findByRol(Rol.AMAIKU.getValor())).thenReturn(rol);

        // Act
        usuarioCuentaRolService.asignarRolAUsuario(relacion);

        // Assert
        verify(usuarioCuentaRolRepository, times(1)).save(any(UsuarioCuentaRolEntity.class));
    }

}