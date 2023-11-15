package com.equipotres.medioambiente.Entidades;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private Rol rol;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;

    }

    @OneToMany(
            mappedBy = "usuario",
            fetch = FetchType.LAZY
    )
    private List<Noticia> noticias;


}
