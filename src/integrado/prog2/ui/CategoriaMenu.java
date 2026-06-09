package integrado.prog2.ui;

import integrado.prog2.entities.Categoria;
import integrado.prog2.service.CategoriaService;
import java.util.ArrayList;
import java.util.Scanner;

public class CategoriaMenu {

    private final Scanner scanner;
    private final CategoriaService categoriaService;

    public CategoriaMenu(Scanner scanner, CategoriaService categoriaService) {
        this.scanner = scanner;
        this.categoriaService = categoriaService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== CATEGORIAS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");

            opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione: ", 0, 4);

            switch (opcion) {
                case 1:
                    listar();
                    break;
                case 2:
                    crear();
                    break;
                case 3:
                    editar();
                    break;
                case 4:
                    eliminar();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

        } while (opcion != 0);
    }

    private void listar() {
        ArrayList<Categoria> categorias = categoriaService.listar();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }

        System.out.println();
        System.out.println("Listado de categorias:");

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private void crear() {
        try {
            String nombre = ConsolaUtils.leerTextoObligatorio(scanner, "Nombre: ");
            String descripcion = ConsolaUtils.leerTextoObligatorio(scanner, "Descripcion: ");

            Categoria categoria = categoriaService.crear(nombre, descripcion);

            System.out.println("Categoria creada correctamente. ID generado: " + categoria.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID de la categoria a editar: ");
            String nombre = ConsolaUtils.leerTextoOpcional(scanner, "Nuevo nombre (ENTER para conservar): ");
            String descripcion = ConsolaUtils.leerTextoOpcional(scanner, "Nueva descripcion (ENTER para conservar): ");

            Categoria categoria = categoriaService.editar(id, nombre, descripcion);

            System.out.println("Categoria actualizada correctamente:");
            System.out.println(categoria);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID de la categoria a eliminar: ");

            if (!ConsolaUtils.confirmar(scanner, "Confirma la eliminacion?")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            categoriaService.eliminar(id);

            System.out.println("Categoria eliminada correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}