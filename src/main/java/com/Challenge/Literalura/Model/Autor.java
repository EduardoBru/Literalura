package com.Challenge.Literalura.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "Autores")
@Getter @Setter @ToString
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private Integer añoNacimiento;
    private Integer añoMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datos) {
        this.nombre = datos.nombre();
        this.añoNacimiento = datos.fechaDeNacimiento();
        this.añoMuerte = datos.fechaDeFallecimiento();
    }
}

