package com.amaiku.users.services.Impl;

import com.amaiku.users.entities.CuentaEntity;
import com.amaiku.users.exceptions.*;
import com.amaiku.users.models.CuentaModel;
import com.amaiku.users.models.Estado;
import com.amaiku.users.repositories.CuentaRepository;
import com.amaiku.users.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository CuentaRepository;

    @Override
    public void crearCuenta(CuentaModel CuentaModel) throws CuentaExistenteException {

        if (CuentaRepository.findByNombre(CuentaModel.getNombre()) != null) {
            throw new CuentaExistenteException();
        }

        CuentaEntity Cuenta = new CuentaEntity(
                CuentaModel.getNombre(),
                CuentaModel.getSubdomino(),
                CuentaModel.getEmail(),
                CuentaModel.getTelefono(),
                Estado.PENDIENTE.getValor(),
                Date.from(Instant.now()),
                Date.from(Instant.now())
        );

        this.CuentaRepository.save(Cuenta);
    }


    @Override
    public void updateCuenta(CuentaModel CuentaModel) throws CuentaNoRegistradaException {

        CuentaEntity entity = getCuentaOrThrow(CuentaModel.getNombre());

        // Actualizar los campos
        entity.setEmail(CuentaModel.getEmail());
        entity.setTelefono(CuentaModel.getTelefono());
        entity.setEstado(CuentaModel.getEstado());
        entity.setSubdomino(CuentaModel.getSubdomino());
        entity.setFechaModif(Date.from(Instant.now()));

        // Guardar los cambios
        this.CuentaRepository.save(entity);

    }

    @Override
    public CuentaModel buscarCuentaByNombre(String nombre) throws CuentaNoRegistradaException {

        CuentaEntity entity = getCuentaOrThrow(nombre);

        return new CuentaModel(
                entity.getIdCuenta(),
                entity.getNombre(),
                entity.getSubdomino(),
                entity.getEmail(),
                entity.getTelefono(),
                entity.getEstado(),
                entity.getFechaAlta(),
                entity.getFechaModif()
        );
    }

    private CuentaEntity getCuentaOrThrow(String nombre) throws CuentaNoRegistradaException {
        return Optional.ofNullable(CuentaRepository.findByNombre(nombre))
                .orElseThrow(CuentaNoRegistradaException::new);
    }

    private CuentaEntity getCuentaBySubdominioOrThrow(String subdominio) throws CuentaNoRegistradaException {
        return Optional.ofNullable(CuentaRepository.findBySubdominio(subdominio))
                .orElseThrow(CuentaNoRegistradaException::new);
    }
}
