package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Ganador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GanadorRepositorio extends JpaRepository<Ganador, String> {

    /*Las operaciones necesarias (crear, modificar, listar y eliminar campañas)
ya están cubiertas por los métodos proporcionados por JpaRepository*/

//Método que devuelve las filas de ganadores dada la empresa
@Query("SELECT g FROM Ganador g WHERE g.empresa.id = ?1")
public List<Ganador> selectGanadoresByEmpresaId(String empresaId);

//Métosdo que devuelve las filas de ganadores dada la empresa y campaña
@Query("SELECT g FROM Ganador g WHERE (g.empresa.id = ?1) AND (g.subscripcion.campana.id = ?2)" )
public List<Ganador> selectGanadoresByEmpresaIdAndCampanaId(String empresaId, String campanaId );

}
