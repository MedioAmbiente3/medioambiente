package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Auspiciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuspicianteRepositorio extends JpaRepository<Auspiciante, String> {
}
