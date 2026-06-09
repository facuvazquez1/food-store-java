package integrado.prog2.service;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.CampoObligatorioException;
import integrado.prog2.exception.EntidadDuplicadaException;
import integrado.prog2.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.Objects;

public class UsuarioService {

    private ArrayList<Usuario> usuarios;
    private Long nextId;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.nextId = 1L;
    }

    public ArrayList<Usuario> listar() {
        ArrayList<Usuario> usuariosActivos = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()) {
                usuariosActivos.add(usuario);
            }
        }

        return usuariosActivos;
    }

    public Usuario crear(String nombre, String apellido, String mail,
                         String celular, String contraseña, Rol rol) {

        validarMail(mail);
        validarMailUnico(mail, null);

        Usuario usuario = new Usuario(
                limpiar(nombre),
                limpiar(apellido),
                mail.trim(),
                limpiar(celular),
                limpiar(contraseña),
                rol != null ? rol : Rol.USUARIO
        );

        usuario.setId(nextId++);
        usuarios.add(usuario);

        return usuario;
    }

    public Usuario editar(Long id, String nombre, String apellido, String mail,
                          String celular, String contraseña, Rol rol) {

        Usuario usuario = buscarPorId(id);

        if (nombre != null) {
            usuario.setNombre(limpiar(nombre));
        }

        if (apellido != null) {
            usuario.setApellido(limpiar(apellido));
        }

        if (mail != null) {
            validarMail(mail);
            validarMailUnico(mail, id);
            usuario.setMail(mail.trim());
        }

        if (celular != null) {
            usuario.setCelular(limpiar(celular));
        }

        if (contraseña != null) {
            usuario.setContraseña(limpiar(contraseña));
        }

        if (rol != null) {
            usuario.setRol(rol);
        }

        return usuario;
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setEliminado(true);
    }

    public Usuario buscarPorId(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de usuario válido.");
        }

        for (Usuario usuario : usuarios) {
            if (Objects.equals(usuario.getId(), id) && !usuario.isEliminado()) {
                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("Usuario no encontrado o eliminado.");
    }

    public Usuario buscarPorIdHistorico(Long id) {
        if (id == null) {
            throw new EntidadNoEncontradaException("Debe ingresar un id de usuario válido.");
        }

        for (Usuario usuario : usuarios) {
            if (Objects.equals(usuario.getId(), id)) {
                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("Usuario no encontrado.");
    }

    private void validarMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new CampoObligatorioException("El mail del usuario es obligatorio.");
        }
    }

    private void validarMailUnico(String mail, Long idIgnorado) {
        String mailNormalizado = normalizar(mail);

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()
                    && !Objects.equals(usuario.getId(), idIgnorado)
                    && normalizar(usuario.getMail()).equals(mailNormalizado)) {

                throw new EntidadDuplicadaException("Ya existe un usuario activo con ese mail.");
            }
        }
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.trim().toLowerCase();
    }

    private String limpiar(String valor) {
        if (valor == null) {
            return null;
        }

        return valor.trim();
    }
}
