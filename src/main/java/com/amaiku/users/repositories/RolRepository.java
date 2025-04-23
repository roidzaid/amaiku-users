package com.amaiku.users.repositories;

import com.amaiku.users.entities.RolEntity;
import com.amaiku.users.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {

    @Query(
            value = "SELECT * FROM rol r WHERE r.nombre = ?1",
            nativeQuery = true)
    RolEntity findByRol(String nombre);

}
