package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.exception.CampoObligatorioException;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.PrecioInvalidoException;
import integrado.prog2.exception.StockInvalidoException;
import java.util.ArrayList;
import java.util.Objects;

public class ProductoService {

    private ArrayList<Producto> productos;
    private Long nextId;
    private CategoriaService categoriaService;

    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.nextId = 1L;
        this.categoriaService = categoriaService;
    }

    public ArrayList<Producto> listar() {
        ArrayList<Producto> productosActivos = new ArrayList<>();

        for (Producto producto : productos) {
            if (!producto.isEliminado()) {
                productosActivos.add(producto);
            }
        }

        return productosActivos;
    }

    public Producto crear(String nombre, String descripcion, Double precio, int stock,
                          String imagen, Boolean disponible, Long categoriaId) {

        validarTextoObligatorio(nombre, "El nombre del producto es obligatorio.");
        validarPrecio(precio);
        validarStock(stock);

        Categoria categoria = categoriaService.buscarPorId(categoriaId);

        Producto producto = new Producto(
                nombre.trim(),
                precio,
                descripcion,
                stock,
                imagen,
                disponible != null ? disponible : Boolean.TRUE,
                categoria
        );

        producto.setId(nextId++);
        productos.add(producto);

        return producto;
    }

    public Producto editar(Long id, String nombre, String descripcion, Double precio,
                           Integer stock, String imagen, Boolean disponible, Long categoriaId) {

        Producto producto = buscarPorId(id);

        if (nombre != null) {
            validarTextoObligatorio(nombre, "El nombre del producto no puede estar vacío.");
            producto.setNombre(nombre.trim());
        }

        if (descripcion != null) {
            producto.setDescripcion(descripcion);
        }

        if (precio != null) {
            validarPrecio(precio);
            producto.setPrecio(precio);
        }

        if (stock != null) {
            validarStock(stock);
            producto.setStock(stock);
        }

        if (imagen != null) {
            producto.setImagen(imagen);
        }

        if (disponible != null) {
            producto.setDisponible(disponible);
        }

        if (categoriaId != null) {
            Categoria categoria = categoriaService.buscarPorId(categoriaId);
            producto.setCategoria(categoria);
        }

        return producto;
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        producto.setEliminado(true);
    }

    public Producto buscarPorId(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de producto válido.");
        }

        for (Producto producto : productos) {
            if (Objects.equals(producto.getId(), id) && !producto.isEliminado()) {
                return producto;
            }
        }

        throw new EntidadNoEncontradaException("Producto no encontrado o eliminado.");
    }

    public Producto buscarPorIdHistorico(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de producto válido.");
        }

        for (Producto producto : productos) {
            if (Objects.equals(producto.getId(), id)) {
                return producto;
            }
        }

        throw new EntidadNoEncontradaException("Producto no encontrado.");
    }

    public boolean existeProductoActivoPorCategoria(Long categoriaId) {
        for (Producto producto : productos) {
            if (!producto.isEliminado()
                    && producto.getCategoria() != null
                    && producto.getCategoria().getId() != null
                    && producto.getCategoria().getId().equals(categoriaId)) {
                return true;
            }
        }

        return false;
    }

    private void validarPrecio(Double precio) {
        if (precio == null) {
            throw new PrecioInvalidoException("El precio del producto es obligatorio.");
        }

        if (precio < 0) {
            throw new PrecioInvalidoException("El precio del producto no puede ser negativo.");
        }
    }

    private void validarStock(Integer stock) {
        if (stock == null) {
            throw new StockInvalidoException("El stock del producto es obligatorio.");
        }

        if (stock < 0) {
            throw new StockInvalidoException("El stock del producto no puede ser negativo.");
        }
    }

    private void validarTextoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new CampoObligatorioException(mensaje);
        }
    }
}
