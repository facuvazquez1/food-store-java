package integrado.prog2;

import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import integrado.prog2.ui.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService(categoriaService);
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService(usuarioService, productoService);

        categoriaService.setProductoService(productoService);

        MenuPrincipal menuPrincipal = new MenuPrincipal(
                categoriaService,
                productoService,
                usuarioService,
                pedidoService
        );

        menuPrincipal.iniciar();
    }
}
