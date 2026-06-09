package integrado.prog2.ui;

import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner;
    private final CategoriaMenu categoriaMenu;
    private final ProductoMenu productoMenu;
    private final UsuarioMenu usuarioMenu;
    private final PedidoMenu pedidoMenu;

    public MenuPrincipal(CategoriaService categoriaService,
                         ProductoService productoService,
                         UsuarioService usuarioService,
                         PedidoService pedidoService) {

        this.scanner = new Scanner(System.in);
        this.categoriaMenu = new CategoriaMenu(scanner, categoriaService);
        this.productoMenu = new ProductoMenu(scanner, productoService, categoriaService);
        this.usuarioMenu = new UsuarioMenu(scanner, usuarioService);
        this.pedidoMenu = new PedidoMenu(scanner, pedidoService, usuarioService, productoService);
    }

    public void iniciar() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");

            opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione: ", 0, 4);

            switch (opcion) {
                case 1:
                    categoriaMenu.mostrar();
                    break;
                case 2:
                    productoMenu.mostrar();
                    break;
                case 3:
                    usuarioMenu.mostrar();
                    break;
                case 4:
                    pedidoMenu.mostrar();
                    break;
                case 0:
                    System.out.println("Sistema finalizado.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }

        } while (opcion != 0);
    }
}
