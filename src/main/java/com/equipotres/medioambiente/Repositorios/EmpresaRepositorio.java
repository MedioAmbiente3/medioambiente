package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, String> {

}
