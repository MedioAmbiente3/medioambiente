package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Empresa;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServicio {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public void crearEmpresa(String nombre,
                             String email,
                             String passwordA,
                             String passwordB) throws MyException {

        validar(nombre, email, passwordA, passwordB);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(passwordA));
        usuario.setRol(RolEnum.EMPRESA);

        Empresa empresa = new Empresa();
        empresa.setUsuario(usuario);

        empresaRepositorio.save(empresa);


    }

    //Obtener ID de la empresa
    public Empresa getOne(String id){
        return empresaRepositorio.getOne(id);
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
