package com.Challenge.Literalura.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Libros")
@Getter @Setter @ToString
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ElementCollection
    private List<String> idiomas;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.idiomas = datosLibros.idiomas();
        this.autor = datosLibros.autor().stream().map(DatosAutor::toAutor).findFirst().orElse(null);
    }
}