package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Comentario;
import com.equipotres.medioambiente.Entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {

    /*No se define ningún método personalizado en este repositorio porque
todas las operaciones necesarias (crear, modificar, listar y eliminar campañas)
ya están cubiertas por los métodos proporcionados por JpaRepository*/
    @Query("SELECT c FROM Comentario c WHERE c.publicacion.id = ?1")
    List<Comentario> buscarComentarioPorPublicacion(String publicacionId);

}
