package com.equipotres.medioambiente.Entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Noticia {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String titulo;

    private String contenido;

    private LocalDate fechaCreacion;
    @OneToOne
    private Imagen imagen;

   // @ManyToOne(fetch = FetchType.LAZY)
   // private Usuario usuario;

}
