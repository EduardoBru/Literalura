package com.Challenge.Literalura.Vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.Challenge.Literalura.Model.Autor;
import com.Challenge.Literalura.Model.Datos;
import com.Challenge.Literalura.Model.DatosAutor;
import com.Challenge.Literalura.Model.DatosLibros;
import com.Challenge.Literalura.Model.Libro;
import com.Challenge.Literalura.Repository.AutorRepository;
import com.Challenge.Literalura.Repository.LibroRepository;
import com.Challenge.Literalura.Service.ConsumoApi;
import com.Challenge.Literalura.Service.ConversorDatosJson;

public class Menu {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConversorDatosJson conversor = new ConversorDatosJson();
    private final Scanner teclado = new Scanner(System.in);
    private final LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public Menu(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 6) {
            System.out.println("\nOpciones:");
            System.out.println("1. Buscar y registrar libro");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año específico");
            System.out.println("5. Listar libros por idioma");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: La opción debe ser un número entre 1 y 6.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> System.out.println("Saliendo...");
                default -> System.out.println("Error: Opción no válida. Selecciona un número entre 1 y 6.");
            }
        }
    }

    private Datos getDatosLibros(String nombreLibro) {
        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        return conversor.obtenerDatos(json, Datos.class);
    }
    
    private void buscarLibro() {
        System.out.print("Escribe el nombre del libro que deseas buscar: ");
        String nombreLibro = teclado.nextLine().trim();
    
        // Obtener resultados de la API y filtrar por título
        Datos datosBusqueda = getDatosLibros(nombreLibro);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
            .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
            .findFirst();
    
        // Verificar si el libro fue encontrado y guardarlo
        if (libroBuscado.isPresent()) {
            Libro libro = new Libro(libroBuscado.get());
            libroRepository.save(libro);
            System.out.println("Libro registrado: " + libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }
    

    private void listarLibros() {
        libroRepository.findAll().forEach(System.out::println);
    }

    private void listarAutores() {
        autorRepository.findAll().forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.print("Introduce el año: ");
        int year = Integer.parseInt(teclado.nextLine());
    
        autorRepository.findAll().stream()
            .filter(autor -> autor.getAñoNacimiento() <= year && 
                             (autor.getAñoMuerte() == null || autor.getAñoMuerte() >= year))
            .forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Introduce el idioma (ES, EN, FR, PT): ");
        String idioma = teclado.nextLine().trim().toUpperCase();
    
        libroRepository.findAll().stream()
            .filter(libro -> libro.getIdiomas().contains(idioma))
            .forEach(System.out::println);
    }
    

    public void guardarLibro(DatosLibros datosLibros) {
        // Obtener o guardar el autor
        Autor autor = datosLibros.autor().stream()
            .map(this::obtenerOGuardarAutor)
            .findFirst() // Supongamos que el libro tiene un solo autor
            .orElseThrow(() -> new RuntimeException("No se encontró un autor"));

        // Crear y guardar el libro
        Libro libro = new Libro(datosLibros);
        libro.setAutor(autor); // Establecer el autor ya guardado
        libroRepository.save(libro);
    }

    private Autor obtenerOGuardarAutor(DatosAutor datosAutor) {
        return autorRepository.findByNombre(datosAutor.nombre())
            .orElseGet(() -> {
                Autor autor = new Autor(datosAutor);
                return autorRepository.save(autor); // Guarda el autor en la base de datos
            });
}

}

