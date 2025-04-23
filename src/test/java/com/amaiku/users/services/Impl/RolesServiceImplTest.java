package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.RolEntity;
import com.amaiku.users.exceptions.RolExistenteException;
import com.amaiku.users.exceptions.RolNoRegistradoException;
import com.amaiku.users.models.RolModel;
import com.amaiku.users.repositories.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RolesServiceImplTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolesServiceImpl rolesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarRol_deberiaDevolverDatosSiExiste() throws RolNoRegistradoException {

        RolEntity entity = new RolEntity();
        entity.setNombre("ROL");

        when(rolRepository.findByRol("ROL")).thenReturn(entity);

        RolModel model = rolesService.buscarRol("ROL");

        assertEquals("ROL", model.getNombre());

        verify(rolRepository, times(1)).findByRol("ROL");
    }

    @Test
    void buscarRol_deberiaLanzarExcepcionNoExiste() {

        // Arrange
        String rolInexistente = "ROL-INEXISTENTE";;

        when(rolRepository.findByRol(rolInexistente)).thenReturn(null);

        // Act & Assert
        assertThrows(RolNoRegistradoException.class, () -> {
            rolesService.buscarRol(rolInexistente);
        });

        verify(rolRepository, times(1)).findByRol(rolInexistente);
    }
}