package integrado.prog2.ui;

import java.util.Scanner;

public final class ConsolaUtils {

    private ConsolaUtils() {
    }

    public static int leerEnteroEnRango(Scanner scanner, String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);

            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

                System.out.println("Opcion fuera de rango. Ingrese un numero entre " + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.");
            }
        }
    }

    public static Long leerLongObligatorio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero valido.");
            }
        }
    }

    public static Long leerLongOpcional(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                return null;
            }

            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero valido o dejar vacio.");
            }
        }
    }

    public static int leerIntObligatorio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero valido.");
            }
        }
    }

    public static Integer leerIntOpcional(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                return null;
            }

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero valido o dejar vacio.");
            }
        }
    }

    public static Double leerDoubleObligatorio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);

            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero decimal valido. Use punto para decimales.");
            }
        }
    }

    public static Double leerDoubleOpcional(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                return null;
            }

            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero decimal valido o dejar vacio.");
            }
        }
    }

    public static String leerTextoObligatorio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("El campo no puede estar vacio.");
        }
    }

    public static String leerTextoOpcional(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine().trim();

        if (texto.isEmpty()) {
            return null;
        }

        return texto;
    }

    public static Boolean leerBooleanObligatorio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("S")) {
                return true;
            }

            if (entrada.equals("N")) {
                return false;
            }

            System.out.println("Entrada invalida. Ingrese S o N.");
        }
    }

    public static Boolean leerBooleanOpcional(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N o ENTER para conservar): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.isEmpty()) {
                return null;
            }

            if (entrada.equals("S")) {
                return true;
            }

            if (entrada.equals("N")) {
                return false;
            }

            System.out.println("Entrada invalida. Ingrese S, N o ENTER.");
        }
    }

    public static boolean confirmar(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.equals("S")) {
                return true;
            }

            if (entrada.equals("N")) {
                return false;
            }

            System.out.println("Entrada invalida. Ingrese S o N.");
        }
    }
}
