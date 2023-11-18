package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Empresa;
import com.equipotres.medioambiente.Entidades.Imagen;
import com.equipotres.medioambiente.Entidades.Rol;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.EmpresaRepositorio;
import com.equipotres.medioambiente.Repositorios.RolRepositorio;
import com.equipotres.medioambiente.Repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EmpresaServicio {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Transactional
    public void crearEmpresa(String nombre,
                             String email,
                             String passwordA,
                             String passwordB) throws MyException {

        validar(nombre, email, passwordA, passwordB);

        Usuario usuario = new Usuario();

        // TODO: Buscar la forma de mostrar el mensaje en el front
        Optional<Rol> userRolOptional = rolRepositorio.findByNombre(RolEnum.EMPRESA);
        if (userRolOptional.isEmpty()) {
            throw new MyException("No se encontro el rol USER en la base de datos");
        }

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(passwordA));
        usuario.setRol(userRolOptional.get());
        usuarioRepositorio.save(usuario);
        Empresa empresa = new Empresa();
        empresa.setUsuario(usuario);
        empresaRepositorio.save(empresa);


    }

    //Obtener ID de la empresa
    public Empresa getOne(String id){
        return empresaRepositorio.getOne(id);
    }

    //Modificar usuario Empresa
    @Transactional
    public void modificaEmpresa(String id,
                                String nombre,
                                String email,
                                String passwordA,
                                String passwordB
                                )
            throws MyException
    {

        validar(nombre, email, passwordA, passwordB);

        //Verificar si el usuario existe en la base de datos
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent())
        {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);

            usuario.setPassword(new BCryptPasswordEncoder().encode(passwordA));
            usuarioRepositorio.save(usuario);
        }
    }


    //Validar campos vacios
    private void validar(String nombre, String email, String passwordA, String passwordB)
            throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre de la empresa  no puede ser "
                    + "nulo o estar vacio");
        }

        if (email.isEmpty() || email == null) {
            throw new MyException("el email de la empresa  no puede ser "
                    + "nulo o estar vacio");
        }

        if (passwordA.isEmpty() || passwordA == null) {
            throw new MyException("la contraseña de la empresa  no puede "
                    + "ser nulo o estar vacio");
        }

        if (passwordB.isEmpty() || passwordB == null) {
            throw new MyException("la contraseña de la empresa  no puede ser nulo o estar vacio");
        }

        if (passwordA.length() <= 5 || passwordB.length() <= 5) {
            throw new MyException("la contraseña debe contener mas de 6 caracteres ");
        }

        if (!passwordA.equals(passwordB)) {
            throw new MyException("las contraseñas ingresadas deben coincidir");
        }

    }

}
