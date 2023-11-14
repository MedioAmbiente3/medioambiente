package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Campana;
import com.equipotres.medioambiente.Entidades.Subscripcion;
import com.equipotres.medioambiente.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscripcionRepositorio extends JpaRepository<Subscripcion, String> {
    @Query("SELECT s.id FROM Subscripcion s, Usuario u, Campana c WHERE u.id = :idUsuario " +
            "AND c.id = :idCampana")
    String obtenerIdSuscripcion(@Param("idUsuario") String idUsuario,
                                @Param("idCampana") String idCampana);


}
