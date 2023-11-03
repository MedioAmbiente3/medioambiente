package com.equipotres.medioambiente.Entidades;

import com.equipotres.medioambiente.Enumeraciones.Rol;
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

    @ManyToOne
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany
    private <Noticias> noticiaList;
}
