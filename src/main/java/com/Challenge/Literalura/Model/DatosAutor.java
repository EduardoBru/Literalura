package com.Challenge.Literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaDeNacimiento,
        @JsonAlias("death_year") Integer fechaDeFallecimiento
) {
    // Method to convert DatosAutor to Autor
    public Autor toAutor() {
        Autor autor = new Autor();
        autor.setNombre(this.nombre);
        autor.setAñoNacimiento(this.fechaDeNacimiento);
        autor.setAñoMuerte(this.fechaDeFallecimiento);
        return autor;
    }
}

