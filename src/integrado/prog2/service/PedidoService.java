package integrado.prog2.service;

import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.CampoObligatorioException;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.OperacionInvalidaException;
import integrado.prog2.exception.PedidoSinUsuarioException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class PedidoService {

    private ArrayList<Pedido> pedidos;
    private Long nextId;
    private Long nextDetalleId;
    private UsuarioService usuarioService;
    private ProductoService productoService;

    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new ArrayList<>();
        this.nextId = 1L;
        this.nextDetalleId = 1L;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    public ArrayList<Pedido> listar() {
        ArrayList<Pedido> pedidosActivos = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                pedidosActivos.add(pedido);
            }
        }

        return pedidosActivos;
    }

    public Pedido crear(Long usuarioId, FormaPago formaPago, ArrayList<DetallePedidoRequest> detalles) {
        if (usuarioId == null) {
            throw new PedidoSinUsuarioException("No se puede crear un pedido sin usuario.");
        }

        if (formaPago == null) {
            throw new CampoObligatorioException("La forma de pago es obligatoria.");
        }

        if (detalles == null || detalles.isEmpty()) {
            throw new OperacionInvalidaException("El pedido debe tener al menos un detalle.");
        }

        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Pedido pedido = new Pedido(
                LocalDate.now(),
                Estado.PENDIENTE,
                0.0,
                formaPago,
                usuario
        );

        for (DetallePedidoRequest detalleRequest : detalles) {
            if (detalleRequest == null) {
                throw new OperacionInvalidaException("El detalle del pedido no puede ser nulo.");
            }

            Producto producto = productoService.buscarPorId(detalleRequest.getProductoId());

            pedido.addDetallePedido(
                    detalleRequest.getCantidad(),
                    producto.getPrecio(),
                    producto
            );
        }

        pedido.calcularTotal();

        pedido.setId(nextId++);

        for (DetallePedido detallePedido : pedido.getDetallePedidos()) {
            detallePedido.setId(nextDetalleId++);
        }

        pedidos.add(pedido);

        return pedido;
    }

    public Pedido actualizarEstadoFormaPago(Long id, Estado estado, FormaPago formaPago) {
        Pedido pedido = buscarPorId(id);

        if (estado == null && formaPago == null) {
            throw new OperacionInvalidaException("Debe indicar un estado o una forma de pago para actualizar.");
        }

        if (estado != null) {
            pedido.setEstado(estado);
        }

        if (formaPago != null) {
            pedido.setFormaPago(formaPago);
        }

        return pedido;
    }

    public void eliminar(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setEliminado(true);
    }

    public Pedido buscarPorId(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de pedido válido.");
        }

        for (Pedido pedido : pedidos) {
            if (Objects.equals(pedido.getId(), id) && !pedido.isEliminado()) {
                return pedido;
            }
        }

        throw new EntidadNoEncontradaException("Pedido no encontrado o eliminado.");
    }

    public Pedido buscarPorIdHistorico(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de pedido válido.");
        }

        for (Pedido pedido : pedidos) {
            if (Objects.equals(pedido.getId(), id)) {
                return pedido;
            }
        }

        throw new EntidadNoEncontradaException("Pedido no encontrado.");
    }

    public static class DetallePedidoRequest {

        private Long productoId;
        private int cantidad;

        public DetallePedidoRequest(Long productoId, int cantidad) {
            this.productoId = productoId;
            this.cantidad = cantidad;
        }

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }
}