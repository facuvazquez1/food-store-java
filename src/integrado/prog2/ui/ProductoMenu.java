package integrado.prog2.ui;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.ProductoService;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductoMenu {

    private final Scanner scanner;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoMenu(Scanner scanner,
                        ProductoService productoService,
                        CategoriaService categoriaService) {
        this.scanner = scanner;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== PRODUCTOS ===");
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
        ArrayList<Producto> productos = productoService.listar();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println();
        System.out.println("Listado de productos:");

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private void listarCategorias() {
        ArrayList<Categoria> categorias = categoriaService.listar();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias cargadas.");
            return;
        }

        System.out.println();
        System.out.println("Categorias disponibles:");

        for (Categoria categoria : categorias) {
            System.out.println(categoria);
        }
    }

    private void crear() {
        try {
            if (categoriaService.listar().isEmpty()) {
                System.out.println("Debe crear al menos una categoria antes de crear productos.");
                return;
            }

            listarCategorias();

            String nombre = ConsolaUtils.leerTextoObligatorio(scanner, "Nombre: ");
            String descripcion = ConsolaUtils.leerTextoOpcional(scanner, "Descripcion: ");
            Double precio = ConsolaUtils.leerDoubleObligatorio(scanner, "Precio: ");
            int stock = ConsolaUtils.leerIntObligatorio(scanner, "Stock: ");
            String imagen = ConsolaUtils.leerTextoOpcional(scanner, "Imagen: ");
            Boolean disponible = ConsolaUtils.leerBooleanObligatorio(scanner, "Disponible?");
            Long categoriaId = ConsolaUtils.leerLongObligatorio(scanner, "ID de categoria: ");

            Producto producto = productoService.crear(
                    nombre,
                    descripcion,
                    precio,
                    stock,
                    imagen,
                    disponible,
                    categoriaId
            );

            System.out.println("Producto creado correctamente. ID generado: " + producto.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID del producto a editar: ");

            String nombre = ConsolaUtils.leerTextoOpcional(scanner, "Nuevo nombre (ENTER para conservar): ");
            String descripcion = ConsolaUtils.leerTextoOpcional(scanner, "Nueva descripcion (ENTER para conservar): ");
            Double precio = ConsolaUtils.leerDoubleOpcional(scanner, "Nuevo precio (ENTER para conservar): ");
            Integer stock = ConsolaUtils.leerIntOpcional(scanner, "Nuevo stock (ENTER para conservar): ");
            String imagen = ConsolaUtils.leerTextoOpcional(scanner, "Nueva imagen (ENTER para conservar): ");
            Boolean disponible = ConsolaUtils.leerBooleanOpcional(scanner, "Disponible?");

            Long categoriaId = null;

            if (ConsolaUtils.confirmar(scanner, "Desea cambiar la categoria?")) {
                listarCategorias();
                categoriaId = ConsolaUtils.leerLongObligatorio(scanner, "Nuevo ID de categoria: ");
            }

            Producto producto = productoService.editar(
                    id,
                    nombre,
                    descripcion,
                    precio,
                    stock,
                    imagen,
                    disponible,
                    categoriaId
            );

            System.out.println("Producto actualizado correctamente:");
            System.out.println(producto);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID del producto a eliminar: ");

            if (!ConsolaUtils.confirmar(scanner, "Confirma la eliminacion?")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            productoService.eliminar(id);

            System.out.println("Producto eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
