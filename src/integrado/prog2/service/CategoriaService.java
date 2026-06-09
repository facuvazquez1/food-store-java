package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.CampoObligatorioException;
import integrado.prog2.exception.CategoriaConProductosActivosException;
import integrado.prog2.exception.EntidadDuplicadaException;
import integrado.prog2.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.Objects;

public class CategoriaService {

    private ArrayList<Categoria> categorias;
    private Long nextId;
    private ProductoService productoService;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.nextId = 1L;
    }

    public void setProductoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public ArrayList<Categoria> listar() {
        ArrayList<Categoria> categoriasActivas = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                categoriasActivas.add(categoria);
            }
        }

        return categoriasActivas;
    }

    public Categoria crear(String nombre, String descripcion) {
        validarTextoObligatorio(nombre, "El nombre de la categoría es obligatorio.");
        validarTextoObligatorio(descripcion, "La descripción de la categoría es obligatoria.");
        validarNombreUnico(nombre, null);

        Categoria categoria = new Categoria(nombre.trim(), descripcion.trim());
        categoria.setId(nextId++);

        categorias.add(categoria);

        return categoria;
    }

    public Categoria editar(Long id, String nombre, String descripcion) {
        Categoria categoria = buscarPorId(id);

        if (nombre != null) {
            validarTextoObligatorio(nombre, "El nombre de la categoría no puede estar vacío.");
            validarNombreUnico(nombre, id);
            categoria.setNombre(nombre.trim());
        }

        if (descripcion != null) {
            validarTextoObligatorio(descripcion, "La descripción de la categoría no puede estar vacía.");
            categoria.setDescripcion(descripcion.trim());
        }

        return categoria;
    }

    public void eliminar(Long id) {
        Categoria categoria = buscarPorId(id);

        if (productoService != null && productoService.existeProductoActivoPorCategoria(id)) {
            throw new CategoriaConProductosActivosException(
                    "No se puede eliminar la categoría porque tiene productos activos asociados."
            );
        }

        categoria.setEliminado(true);
    }

    public Categoria buscarPorId(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de categoría válido.");
        }

        for (Categoria categoria : categorias) {
            if (Objects.equals(categoria.getId(), id) && !categoria.isEliminado()) {
                return categoria;
            }
        }

        throw new EntidadNoEncontradaException("Categoría no encontrada o eliminada.");
    }

    public Categoria buscarPorIdHistorico(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de categoría válido.");
        }

        for (Categoria categoria : categorias) {
            if (Objects.equals(categoria.getId(), id)) {
                return categoria;
            }
        }

        throw new EntidadNoEncontradaException("Categoría no encontrada.");
    }

    private void validarNombreUnico(String nombre, Long idIgnorado) {
        String nombreNormalizado = normalizar(nombre);

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()
                    && !Objects.equals(categoria.getId(), idIgnorado)
                    && normalizar(categoria.getNombre()).equals(nombreNormalizado)) {

                throw new EntidadDuplicadaException("Ya existe una categoría activa con ese nombre.");
            }
        }
    }

    private void validarTextoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new CampoObligatorioException(mensaje);
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.trim().toLowerCase();
    }
}
