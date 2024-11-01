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
    @Column(unique = true)
    private String nombre;

    private Integer añoNacimiento;
    private Integer añoMuerte;

    @Transient
    private List<Libro> libros;


    public Autor() {}

    public Autor(DatosAutor a) {
        this.nombre = a.nombre();
        this.añoNacimiento = a.fechaDeNacimiento();
        this.añoMuerte = a.fechaDeFallecimiento();
    }
}
