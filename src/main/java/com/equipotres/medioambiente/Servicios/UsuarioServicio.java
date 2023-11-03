package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.Rol;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    //Crear usuario
    @Transactional
    public void crearUsuario(String nombre, String email, String passwordA, String passwordB, MultipartFile imagen) throws MyException {

        //Validamos que los campos no esten vacios
        validar(nombre, email, passwordA, passwordB);
        //Instanciamos un objeto de la clase Usuario
        Usuario usuario = new Usuario();

        //seteamos los atributos
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(passwordA));
        usuario.setRol(Rol.USER);
        Imagen foto = imagenServicio.guardaImagen(imagen);
        usuario.setImagen(foto);
        usuarioRepositorio.save(usuario);

    }

    //Captura el id del Usuario
    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    //Http Sesion de usuario
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.findXMail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession sesion = attr.getRequest().getSession(true);

            sesion.setAttribute("usuariosesion", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }

    }

    //Modificar usuario
    @Transactional
    public void modificaUsuario(String id, String nombre, String email,
                                String passwordA, String passwordB) throws MyException {
        validar(nombre, email, passwordA, passwordB);

        //Verificar si el usuario existe en la base de datos
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);


            usuarioRepositorio.save(usuario);

        }

    }

    //Consultar usuario
    public Usuario consultarUsuarioxEmail(String email) {
        //Retornamos el usuario encontrado
        return usuarioRepositorio.findXMail(email);
    }



    //Validar campos vacios
    private void validar(String nombre, String email, String passwordA, String passwordB)
            throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre de usuario  no puede ser "
                    + "nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MyException("el email del usuario  no puede ser "
                    + "nulo o estar vacio");
        }

        if (passwordA.isEmpty() || passwordA == null) {
            throw new MyException("la contraseña del usuario  no puede "
                    + "ser nulo o estar vacio");
        }

        if (passwordB.isEmpty() || passwordB == null) {
            throw new MyException("la contraseña del usuario  no puede ser nulo o estar vacio");
        }

        if (passwordA.length() <= 5 || passwordB.length() <= 5) {
            throw new MyException("la contraseña debe contener mas de 6 caracteres ");
        }

        if (!passwordA.equals(passwordB)) {
            throw new MyException("las contraseñas ingresadas deben coincidir");
        }

    }

}
