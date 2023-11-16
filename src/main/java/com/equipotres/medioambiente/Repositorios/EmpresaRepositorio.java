package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, String> {
/*No se define ningún método personalizado en este repositorio porque
todas las operaciones necesarias (crear, modificar, listar y eliminar campañas)
ya están cubiertas por los métodos proporcionados por JpaRepository*/
}
