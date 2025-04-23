package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.CuentaEntity;
import com.amaiku.users.exceptions.CuentaExistenteException;
import com.amaiku.users.exceptions.CuentaNoRegistradaException;
import com.amaiku.users.models.CuentaModel;
import com.amaiku.users.models.Estado;
import com.amaiku.users.repositories.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceImplTest {

    @Mock
    private CuentaRepository CuentaRepository;

    @InjectMocks
    private CuentaServiceImpl CuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCuenta_deberiaCrearSiNoExiste() throws CuentaExistenteException {
        // Arrange
        CuentaModel model = new CuentaModel();
        model.setNombre("Cuenta Test");
        model.setSubdomino("Cuenta-test");
        model.setEmail("test@mail.com");
        model.setTelefono("123456");
        model.setEstado(Estado.ACTIVO.getValor());
        model.setFechaAlta(Date.from(Instant.now()));

        when(CuentaRepository.findByNombre("Cuenta Test")).thenReturn(null);

        // Act
        CuentaService.crearCuenta(model);

        // Assert
        verify(CuentaRepository, times(1)).save(any(CuentaEntity.class));
    }

    @Test
    void crearCuenta_deberiaLanzarExcepcionSiExiste() {
        // Arrange
        CuentaModel model = new CuentaModel();
        model.setNombre("Cuenta Existente");

        when(CuentaRepository.findByNombre("Cuenta Existente"))
                .thenReturn(new CuentaEntity());

        // Act & Assert
        assertThrows(CuentaExistenteException.class, () -> {
            CuentaService.crearCuenta(model);
        });

        verify(CuentaRepository, never()).save(any());
    }



    @Test
    void updateCuenta_deberiaModificarSiExiste() throws CuentaNoRegistradaException {

        // Arrange
        String nombre = "Cuenta Uno";
        CuentaModel model = new CuentaModel();
        model.setNombre(nombre);
        model.setEmail("nuevo@email.com");
        model.setTelefono("123456789");
        model.setEstado(Estado.PENDIENTE.getValor());
        model.setSubdomino("nuevo-sub");

        CuentaEntity entity = new CuentaEntity();
        entity.setNombre(nombre);
        entity.setEmail("viejo@email.com");
        entity.setTelefono("000");
        entity.setEstado(Estado.ACTIVO.getValor());
        entity.setSubdomino("viejo-sub");

        when(CuentaRepository.findByNombre(nombre)).thenReturn(entity);

        // Act
        CuentaService.updateCuenta(model);

        // Assert
        assertEquals("nuevo@email.com", entity.getEmail());
        assertEquals("123456789", entity.getTelefono());
        assertEquals("nuevo-sub", entity.getSubdomino());
        assertFalse(entity.getEstado().equals(Estado.ACTIVO.getValor()));

        verify(CuentaRepository, times(1)).save(entity);
    }

    @Test
    void upadteCuenta_deberiaLanzarExcepcionNoExiste() {

        // Arrange
        String nombre = "Cuenta no existe";
        CuentaModel model = new CuentaModel();
        model.setNombre(nombre);

        when(CuentaRepository.findByNombre("Cuenta no existe")).thenReturn(null);

        // Act & Assert
        assertThrows(CuentaNoRegistradaException.class, () -> {
            CuentaService.updateCuenta(model);
        });

        verify(CuentaRepository, never()).save(any());
    }

    @Test
    void buscarCuentaByNombre_DeberiaDevolverDatosSiExiste() throws CuentaNoRegistradaException {

        CuentaEntity entity = new CuentaEntity();
        entity.setNombre("Cuenta Uno");
        entity.setEmail("viejo@email.com");
        entity.setTelefono("000");
        entity.setEstado(Estado.ACTIVO.getValor());
        entity.setSubdomino("viejo-sub");

        when(CuentaRepository.findByNombre("Cuenta Uno")).thenReturn(entity);

        CuentaModel model = CuentaService.buscarCuentaByNombre("Cuenta Uno");

        assertEquals("Cuenta Uno", model.getNombre());
        assertEquals("viejo@email.com", model.getEmail());
        assertEquals("000", model.getTelefono());
        assertTrue(model.getEstado().equals(Estado.ACTIVO.getValor()));
        assertEquals("viejo-sub", model.getSubdomino());

        verify(CuentaRepository, times(1)).findByNombre("Cuenta Uno");
    }

    @Test
    void buscarCuentaByNombre_deberiaLanzarExcepcionNoExiste() {

        // Arrange
        String nombreInexistente = "nombre-no-existe";;

        when(CuentaRepository.findBySubdominio(nombreInexistente)).thenReturn(null);

        // Act & Assert
        assertThrows(CuentaNoRegistradaException.class, () -> {
            CuentaService.buscarCuentaByNombre(nombreInexistente);
        });

        verify(CuentaRepository, times(1)).findByNombre(nombreInexistente);
    }
}