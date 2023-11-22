package com.equipotres.medioambiente.Servicios;

import com.equipotres.medioambiente.Entidades.Auspiciante;
import com.equipotres.medioambiente.Entidades.Empresa;
import com.equipotres.medioambiente.Entidades.Usuario;
import com.equipotres.medioambiente.Excepciones.MyException;
import com.equipotres.medioambiente.Repositorios.AuspicianteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuspicianteServicio {

    @Autowired
    private AuspicianteRepositorio auspicianteRepositorio;

    //Crear auspiciante
    public void crearAuspiciante(Auspiciante auspiciante) throws MyException
    {
        auspicianteRepositorio.save(auspiciante);
    }

    //Consultar todas las Auspiciantes
    public List<Auspiciante> listarAuspiciantes()
    {
        List<Auspiciante> auspiciantes = auspicianteRepositorio.findAll();
        return auspiciantes;
    }
    
    public List<Auspiciante> listarAuspiciantesDeCampana(String idCampana)
    {

        List<Auspiciante> auspiciantesDeCampana = new ArrayList<>();
        for (Auspiciante aus :listarAuspiciantes())
        {
            if(aus.getCampana().getId().equals(idCampana))
            { auspiciantesDeCampana.add(aus); }
        }
        return auspiciantesDeCampana;
    }
    
    public String obtenerIdAuspiciante(String idEmpresa, String idCampana)
    {
        String idAuspiciante = new String();
        for (Auspiciante aus:listarAuspiciantesDeCampana(idCampana))
        {   
            Empresa empresa = aus.getEmpresa();
            Usuario usuario = empresa.getUsuario();
            
            if(usuario.getId().equals(idEmpresa))
            {
              idAuspiciante = aus.getId();
            }
        }
        return idAuspiciante;
    }

    //Obtener auspiciante dado el Id
    public Auspiciante getOne(String id){
        return auspicianteRepositorio.getOne(id);
    }

}
