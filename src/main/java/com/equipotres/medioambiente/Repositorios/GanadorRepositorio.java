package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Ganador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GanadorRepositorio extends JpaRepository<Ganador, String> {

    /*No se define ningún método personalizado en este repositorio porque
todas las operaciones necesarias (crear, modificar, listar y eliminar campañas)
ya están cubiertas por los métodos proporcionados por JpaRepository*/
    @Query("SELECT g FROM Ganador g WHERE g.empresa.id = ?1")
    List<Ganador> selectGanadoresbyEmpresaId(String empresaId);

}
