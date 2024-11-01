package com.Challenge.Literalura.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class Libro {

    private String titulo;
    private List<String> idioma;
    private List<DatosAutor> autor;
    

    public Libro() {
    }

    public Libro (DatosLibros l){
        this.titulo = l.titulo();
        this.autor = l.autor();
        this.idioma = l.idiomas();
    }
}
