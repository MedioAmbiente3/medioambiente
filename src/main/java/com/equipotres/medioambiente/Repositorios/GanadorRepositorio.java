package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Ganador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GanadorRepositorio extends JpaRepository<Ganador, String> {


}
