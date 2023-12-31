package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Rol;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, String> {

    @Query("SELECT r FROM Rol r WHERE r.nombre = ?1")
    Optional<Rol> findByNombre(RolEnum rolEnum);
}
