package com.Challenge.Literalura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Challenge.Literalura.Model.Libro;

public interface LibroRepository extends JpaRepository<Libro,Long>{

}