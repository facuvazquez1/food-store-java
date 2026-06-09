package integrado.prog2.ui;

import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import java.util.ArrayList;
import java.util.Scanner;

public class PedidoMenu {

    private final Scanner scanner;
    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    public PedidoMenu(Scanner scanner,
                      PedidoService pedidoService,
                      UsuarioService usuarioService,
                      ProductoService productoService) {
        this.scanner = scanner;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== PEDIDOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar estado / forma de pago");
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
                    editarEstadoFormaPago();
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
        ArrayList<Pedido> pedidos = pedidoService.listar();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }

        System.out.println();
        System.out.println("Listado de pedidos:");

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);

            if (pedido.getDetallePedidos().isEmpty()) {
                System.out.println("  Sin detalles.");
            } else {
                for (DetallePedido detalle : pedido.getDetallePedidos()) {
                    System.out.println("  " + detalle);
                }
            }
        }
    }

    private void listarUsuarios() {
        ArrayList<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        System.out.println();
        System.out.println("Usuarios disponibles:");

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void listarProductos() {
        ArrayList<Producto> productos = productoService.listar();

        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }

        System.out.println();
        System.out.println("Productos disponibles:");

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private void crear() {
        try {
            if (usuarioService.listar().isEmpty()) {
                System.out.println("Debe crear al menos un usuario antes de crear pedidos.");
                return;
            }

            if (productoService.listar().isEmpty()) {
                System.out.println("Debe crear al menos un producto antes de crear pedidos.");
                return;
            }

            listarUsuarios();
            Long usuarioId = ConsolaUtils.leerLongObligatorio(scanner, "ID de usuario: ");

            FormaPago formaPago = seleccionarFormaPagoObligatoria();

            ArrayList<PedidoService.DetallePedidoRequest> detalles = new ArrayList<>();

            boolean agregarOtro;

            do {
                listarProductos();

                Long productoId = ConsolaUtils.leerLongObligatorio(scanner, "ID de producto: ");
                int cantidad = ConsolaUtils.leerIntObligatorio(scanner, "Cantidad: ");

                detalles.add(new PedidoService.DetallePedidoRequest(productoId, cantidad));

                agregarOtro = ConsolaUtils.confirmar(scanner, "Desea agregar otro producto al pedido?");
            } while (agregarOtro);

            Pedido pedido = pedidoService.crear(usuarioId, formaPago, detalles);

            System.out.println("Pedido creado correctamente. ID generado: " + pedido.getId());
            System.out.println("Total: " + pedido.getTotal());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("La creacion del pedido fue cancelada.");
        }
    }

    private void editarEstadoFormaPago() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID del pedido a editar: ");

            Estado estado = seleccionarEstadoOpcional();
            FormaPago formaPago = seleccionarFormaPagoOpcional();

            Pedido pedido = pedidoService.actualizarEstadoFormaPago(id, estado, formaPago);

            System.out.println("Pedido actualizado correctamente:");
            System.out.println(pedido);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID del pedido a eliminar: ");

            if (!ConsolaUtils.confirmar(scanner, "Confirma la eliminacion?")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            pedidoService.eliminar(id);

            System.out.println("Pedido eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private FormaPago seleccionarFormaPagoObligatoria() {
        System.out.println("Formas de pago:");
        System.out.println("1. TARJETA");
        System.out.println("2. TRANSFERENCIA");
        System.out.println("3. EFECTIVO");

        int opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione forma de pago: ", 1, 3);

        switch (opcion) {
            case 1:
                return FormaPago.TARJETA;
            case 2:
                return FormaPago.TRANSFERENCIA;
            case 3:
                return FormaPago.EFECTIVO;
            default:
                return FormaPago.EFECTIVO;
        }
    }

    private FormaPago seleccionarFormaPagoOpcional() {
        System.out.println("Formas de pago:");
        System.out.println("1. TARJETA");
        System.out.println("2. TRANSFERENCIA");
        System.out.println("3. EFECTIVO");
        System.out.println("0. Conservar");

        int opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione forma de pago: ", 0, 3);

        switch (opcion) {
            case 1:
                return FormaPago.TARJETA;
            case 2:
                return FormaPago.TRANSFERENCIA;
            case 3:
                return FormaPago.EFECTIVO;
            default:
                return null;
        }
    }

    private Estado seleccionarEstadoOpcional() {
        System.out.println("Estados:");
        System.out.println("1. PENDIENTE");
        System.out.println("2. CONFIRMADO");
        System.out.println("3. TERMINADO");
        System.out.println("4. CANCELADO");
        System.out.println("0. Conservar");

        int opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione estado: ", 0, 4);

        switch (opcion) {
            case 1:
                return Estado.PENDIENTE;
            case 2:
                return Estado.CONFIRMADO;
            case 3:
                return Estado.TERMINADO;
            case 4:
                return Estado.CANCELADO;
            default:
                return null;
        }
    }
}
