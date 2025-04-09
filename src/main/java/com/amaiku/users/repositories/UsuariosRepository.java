package com.amaiku.users.repositories;

import com.amaiku.users.entities.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {

    @Query(
            value = "SELECT * FROM usuarios u WHERE u.usuario = ?1",
            nativeQuery = true)
    UsuariosEntity findByUsuario(String usuario);

    @Query(
            value = "SELECT * FROM usuarios u WHERE u.usuario = ?1 and u.aplicacion = ?2",
            nativeQuery = true)
    UsuariosEntity findByUsuarioYAplicacion(String usuario, String aplicacion);


}
