package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    /*se extiende JpaRepository por tanto ya contiene métodos para operaciones
    de CRUD estándar*/
    
    /*se define un método personalizado findXMail(String correo) para buscar
    un usuario por su correo electrónico.*/
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario findXMail(@Param("email") String email);

    /*se define un método personalizado findByRolId(String rol_id) para buscar
    un usuario por su correo electrónico.*/
    @Query("SELECT u FROM Usuario u WHERE u.rol.id = :rol_id")
    public List<Usuario> findByRolId(@Param("rol_id") String rol_id);

}
