package com.amaiku.users.repositories;

import com.amaiku.users.entities.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    @Query(
            value = "SELECT * FROM roles r WHERE r.rol = ?1 and r.aplicacion = ?2",
            nativeQuery = true)
    RolesEntity findByRol(String rol, String aplicacion);


}
