package com.amaiku.users.repositories;

import com.amaiku.users.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query(
            value = "SELECT * FROM usuario u WHERE u.mail = ?1 and u.estado = ?2",
            nativeQuery = true)
    UsuarioEntity findUsuario(String mail, String estado);

    @Query(
            value = "SELECT * FROM usuario u WHERE u.mail = ?1",
            nativeQuery = true)
    UsuarioEntity findUsuarioSinEstado(String mail);

}
