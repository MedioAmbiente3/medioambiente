package com.equipotres.medioambiente.Entidades;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Foto {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;
    private String nombre;

    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;


}
