package com.amaiku.users.repositories;

import com.amaiku.users.entities.UsuarioCuentaRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioCuentaRolRepository extends JpaRepository<UsuarioCuentaRolEntity, Long> {

    @Query(
            value = "SELECT * FROM usuario_Cuenta_rol ucr " +
                    "WHERE ucr.id_usuario = ?1 AND ucr.id_Cuenta = ?2 AND ucr.id_rol = ?3",
            nativeQuery = true)
    UsuarioCuentaRolEntity findByUsuarioAndCuentaAndRol(Long idUsuario, Long idCuenta, Long idRol);


}
