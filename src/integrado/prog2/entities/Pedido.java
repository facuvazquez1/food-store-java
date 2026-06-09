package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.CantidadInvalidaException;
import integrado.prog2.exception.OperacionInvalidaException;
import integrado.prog2.exception.PrecioInvalidoException;
import integrado.prog2.exception.StockInvalidoException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private ArrayList<DetallePedido> detallePedidos;

    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.total = 0.0;
        this.detallePedidos = new ArrayList<>();
    }

    public Pedido(LocalDate fecha, Estado estado, Double total,
                  FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detallePedidos = new ArrayList<>();
    }

    public Pedido(Long id, LocalDate fecha, Estado estado, Double total,
                  FormaPago formaPago, Usuario usuario) {
        super();
        this.setId(id);
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detallePedidos = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(ArrayList<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        if (producto == null) {
            throw new OperacionInvalidaException("Debe seleccionar un producto válido.");
        }

        if (producto.isEliminado()) {
            throw new OperacionInvalidaException("No se puede agregar un producto eliminado al pedido.");
        }

        if (cantidad <= 0) {
            throw new CantidadInvalidaException("La cantidad del detalle debe ser mayor a cero.");
        }

        if (precio == null || precio < 0) {
            throw new PrecioInvalidoException("El precio del detalle no puede ser nulo ni negativo.");
        }

        if (cantidad > producto.getStock()) {
            throw new StockInvalidoException(
                    "No hay stock suficiente para el producto: " + producto.getNombre()
            );
        }

        Double subtotal = cantidad * precio;
        DetallePedido detallePedido = new DetallePedido(cantidad, subtotal, producto);

        this.detallePedidos.add(detallePedido);
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            return null;
        }

        for (DetallePedido detalle : detallePedidos) {
            if (detalle.getProducto() != null && detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }

        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalleEncontrado = findeDetallePedidoByProducto(producto);

        if (detalleEncontrado != null) {
            detallePedidos.remove(detalleEncontrado);
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        Double acumulador = 0.0;

        for (DetallePedido detalle : detallePedidos) {
            acumulador += detalle.getSubtotal();
        }

        this.total = acumulador;
    }

    @Override
    public String toString() {
        String nombreUsuario = usuario != null
                ? usuario.getNombre() + " " + usuario.getApellido()
                : "Sin usuario";

        return "Pedido{" +
                "id=" + getId() +
                ", fecha=" + fecha +
                ", usuario=" + nombreUsuario +
                ", estado=" + estado +
                ", formaPago=" + formaPago +
                ", total=" + total +
                ", cantidadDetalles=" + detallePedidos.size() +
                '}';
    }
}
