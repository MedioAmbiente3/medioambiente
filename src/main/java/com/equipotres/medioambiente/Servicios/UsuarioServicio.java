package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.Role;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //Crear usuario
    @Transactional
    public void crearUsuario(String nombre, String email, String password) throws MyException {

        //Validamos que los campos no esten vacios
        validar(nombre, email, password);
        //Instanciamos un objeto de la clase Usuario
        Usuario usuario = new Usuario();

        //seteamos los atributos
        usuario.setNombre(nombre);
        usuario.setCorreo(email);
        usuario.setPassword(password);
        usuario.setRol(Role.USER);


        usuarioRepositorio.save(usuario);

    }

    //Modificar usuario
    @Transactional
    public void modificaUsuario(String id_usuario, String nombre, String email,
                                String password) throws MyException {
        validar(nombre, email, password);

        //Verificar si el usuario existe en la base de datos
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);
        if (respuesta.isPresent()) {

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setCorreo(email);
            usuario.setPassword(password);

            usuarioRepositorio.save(usuario);

        }

    }

    //Consultar usuario
    public Usuario consultarUsuarioxEmail(String email) {
        //Retornamos el usuario encontrado
        return usuarioRepositorio.findXMail(email);
    }



    //Validar campos vacios
    private void validar(String nombre, String email, String password)
            throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre de usuario  no puede ser "
                    + "nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MyException("el email del usuario  no puede ser "
                    + "nulo o estar vacio");
        }

        if (password.isEmpty() || password == null) {
            throw new MyException("la contraseña del usuario  no puede "
                    + "ser nulo o estar vacio");
        }

    }

}
