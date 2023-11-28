package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Publicacion;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Entidades.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto,String> {
/*Las operaciones necesarias (crear, modificar, listar y eliminar campañas)
ya están cubiertas por los métodos proporcionados por JpaRepository*/
   
//Se define el método personalizado para hallar las publicaciones + votadas de campana
  @Query("SELECT v.Publicacion FROM Voto v WHERE v.Publicacion IN ?1 GROUP BY v.Publicacion ORDER BY COUNT(v) DESC")
  public List<Publicacion> findPublicacionesMasVotadas(List<Publicacion> publicacionesDeCampana);

//Se define el método personalizado para hallar los usuarios + votados de campana
  @Query("SELECT v.Publicacion.subscripcion.usuario FROM Voto v WHERE v.Publicacion IN ?1 " +
         "GROUP BY v.Publicacion ORDER BY COUNT(v) DESC")
  public List<Usuario> findUsuariosMasVotados(List<Publicacion> publicacionesMasVotadas);
    
}
