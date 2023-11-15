package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto,String> {

    @Query("SELECT v.id FROM Voto v, Usuario u, Publicacion p WHERE u.id = :idUsuario " +
            "AND p.id = :idPublicacion")
    String obtenerIdVoto(@Param("idUsuario") String idUsuario,
                                @Param("idPublicacion") String idPublicacion);
}
