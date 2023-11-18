package com.equipotres.medioambiente.Repositorios;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
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

/*se define un metodo personalizado findXRol(String rol) para buscar
un usuario por su rol */
    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = :rol")
    public List<Usuario> findXRol(@Param("rol")RolEnum rol);

}
