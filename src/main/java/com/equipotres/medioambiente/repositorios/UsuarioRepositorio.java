package com.equipotres.medioambiente.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//Creado por davidlugodev
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
    /*se extiende JpaRepository por tanto ya contiene métodos para operaciones de CRUD estándar*/
    /*se define un método personalizado findXMail(String correo) para buscar un usuario
    por su correo electrónico.*/
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    public Usuario findXMail(@Param("correo")String correo);
}
