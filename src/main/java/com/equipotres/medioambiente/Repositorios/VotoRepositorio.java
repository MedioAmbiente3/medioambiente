package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto,String> {

}
