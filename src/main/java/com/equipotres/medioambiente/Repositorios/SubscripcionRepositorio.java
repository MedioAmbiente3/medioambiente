package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Subscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscripcionRepositorio extends JpaRepository<Subscripcion, String> {

}
