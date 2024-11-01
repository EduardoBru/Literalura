package com.Challenge.Literalura.Vista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.Challenge.Literalura.Model.Datos;
import com.Challenge.Literalura.Model.DatosLibros;
import com.Challenge.Literalura.Model.Libro;
import com.Challenge.Literalura.Repository.LibroRepository;
import com.Challenge.Literalura.Service.ConsumoApi;
import com.Challenge.Literalura.Service.ConversorDatosJson;

public class Menu {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConversorDatosJson conversor = new ConversorDatosJson();
    private Scanner teclado = new Scanner(System.in);

    private LibroRepository menuRepository;
    public Menu(LibroRepository repository) {
        this.menuRepository = repository;
    }

   private List<DatosLibros> datosLibros = new ArrayList<>();

    // public void mostrarMenu (){
        
    //     var json = consumoApi.obtenerDatos(URL_BASE);

    //     System.out.println(json);

    //      var datos = conversor.obtenerDatos(json,Datos.class);

    //    System.out.println("\n " + datos);
    //     //Busqueda de libros por nombre
    //     System.out.println("Ingrese el nombre del libro que desea buscar");

    //     var tituloLibro = teclado.nextLine();

    //     json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        
    //     var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

    //     Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
    //             .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
    //             .findFirst();

    //     if(libroBuscado.isPresent()){
    //         System.out.println("Libro Encontrado ");
    //         System.out.println(libroBuscado.get());
    //     }else {
    //         System.out.println("Libro no encontrado");
    //     };

      
    // }

    public void iniciar() {
     //   private Scanner scanner = new Scanner(System.in);
     var opcion = -1;
            while (opcion != 0) {
                System.out.println("\nOpciones:");
                System.out.println("1. Buscar y registrar libro");
                System.out.println("2. Listar libros registrados");
                System.out.println("3. Listar autores registrados");
                System.out.println("4. Listar autores vivos en un año específico");
                System.out.println("5. Listar libros por idioma");
                System.out.println("6. Salir");
                System.out.print("Selecciona una opción: ");

                int valor;
                try {
                    valor = Integer.parseInt(teclado.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: La opción debe ser un número entre 1 y 6.");
                    continue;
                }

                switch (valor) {
                    case 1:
                        buscarLibro();
                    case 2:
                       // libroService.listarLibros();
                        break;
                    case 3:
                       // libroService.listarAutores();
                        break;
                    case 4:
                        System.out.print("Introduce el año: ");
                    case 5:
                        System.out.print("Introduce el idioma (ES, EN, FR, PT): ");
                       
                    case 6:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Error: Opción no válida. Selecciona un número entre 1 y 6.");
                }
            }
        }
        
        private DatosLibros getDatosLibro() {
            System.out.println("Escribe el nombre del libro que deseas buscar");
            var nombreLibro = teclado.nextLine();
           
            var json = consumoApi.obtenerDatos(URL_BASE);
            json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ","+"));
            System.out.println(json);
            DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
            return datos;
        }

        private void buscarLibro() {
            DatosLibros datos = getDatosLibro();
            Libro libro = new Libro(datos);
            menuRepository.save(libro);
            System.out.println(datos);
        }

}
