package integrado.prog2.ui;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.service.UsuarioService;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioMenu {

    private final Scanner scanner;
    private final UsuarioService usuarioService;

    public UsuarioMenu(Scanner scanner, UsuarioService usuarioService) {
        this.scanner = scanner;
        this.usuarioService = usuarioService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println();
            System.out.println("=== USUARIOS ===");
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
        ArrayList<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }

        System.out.println();
        System.out.println("Listado de usuarios:");

        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private void crear() {
        try {
            String nombre = ConsolaUtils.leerTextoObligatorio(scanner, "Nombre: ");
            String apellido = ConsolaUtils.leerTextoObligatorio(scanner, "Apellido: ");
            String mail = ConsolaUtils.leerTextoObligatorio(scanner, "Mail: ");
            String celular = ConsolaUtils.leerTextoOpcional(scanner, "Celular: ");
            String contrasenia = ConsolaUtils.leerTextoOpcional(scanner, "Contrasenia: ");
            Rol rol = seleccionarRolObligatorio();

            Usuario usuario = usuarioService.crear(
                    nombre,
                    apellido,
                    mail,
                    celular,
                    contrasenia,
                    rol
            );

            System.out.println("Usuario creado correctamente. ID generado: " + usuario.getId());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID del usuario a editar: ");

            String nombre = ConsolaUtils.leerTextoOpcional(scanner, "Nuevo nombre (ENTER para conservar): ");
            String apellido = ConsolaUtils.leerTextoOpcional(scanner, "Nuevo apellido (ENTER para conservar): ");
            String mail = ConsolaUtils.leerTextoOpcional(scanner, "Nuevo mail (ENTER para conservar): ");
            String celular = ConsolaUtils.leerTextoOpcional(scanner, "Nuevo celular (ENTER para conservar): ");
            String contrasenia = ConsolaUtils.leerTextoOpcional(scanner, "Nueva contrasenia (ENTER para conservar): ");
            Rol rol = seleccionarRolOpcional();

            Usuario usuario = usuarioService.editar(
                    id,
                    nombre,
                    apellido,
                    mail,
                    celular,
                    contrasenia,
                    rol
            );

            System.out.println("Usuario actualizado correctamente:");
            System.out.println(usuario);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            listar();

            Long id = ConsolaUtils.leerLongObligatorio(scanner, "ID del usuario a eliminar: ");

            if (!ConsolaUtils.confirmar(scanner, "Confirma la eliminacion?")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            usuarioService.eliminar(id);

            System.out.println("Usuario eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Rol seleccionarRolObligatorio() {
        System.out.println("Roles:");
        System.out.println("1. ADMIN");
        System.out.println("2. USUARIO");

        int opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione rol: ", 1, 2);

        if (opcion == 1) {
            return Rol.ADMIN;
        }

        return Rol.USUARIO;
    }

    private Rol seleccionarRolOpcional() {
        System.out.println("Roles:");
        System.out.println("1. ADMIN");
        System.out.println("2. USUARIO");
        System.out.println("0. Conservar");

        int opcion = ConsolaUtils.leerEnteroEnRango(scanner, "Seleccione rol: ", 0, 2);

        if (opcion == 1) {
            return Rol.ADMIN;
        }

        if (opcion == 2) {
            return Rol.USUARIO;
        }

        return null;
    }
}
