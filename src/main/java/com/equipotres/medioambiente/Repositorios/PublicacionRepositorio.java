package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, String> {

    /*No se define ningún método personalizado en este repositorio porque
todas las operaciones necesarias (crear, modificar, listar y eliminar campañas)
ya están cubiertas por los métodos proporcionados por JpaRepository*/
    @Query("SELECT p FROM Publicacion p WHERE p.subscripcion.campana.id = ?1")
    public List<Publicacion> findPublicacionesByCampana(String campanaId);



}
