package com.amaiku.users.repositories;

import com.amaiku.users.entities.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository  extends JpaRepository<CuentaEntity, Long> {

    @Query(
            value = "SELECT * FROM Cuenta c WHERE c.nombre = ?1",
            nativeQuery = true)
    CuentaEntity findByNombre(String nombre);

    @Query(
            value = "SELECT * FROM Cuenta c WHERE c.subdominio = ?1",
            nativeQuery = true)
    CuentaEntity findBySubdominio(String subdominio);
}
