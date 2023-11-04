package com.equipotres.medioambiente.Entidades;

import com.equipotres.medioambiente.Enumeraciones.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String email;
    private String password;

    @OneToOne
    private Imagen imagen;


    //@ManyToOne
    //private Rol rol;
    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @OneToMany
    private List<Noticia> noticias;
}
