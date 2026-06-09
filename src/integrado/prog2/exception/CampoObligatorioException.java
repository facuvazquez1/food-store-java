package integrado.prog2.exception;

public class CampoObligatorioException extends RuntimeException {

    public CampoObligatorioException(String mensaje) {
        super(mensaje);
    }
}
