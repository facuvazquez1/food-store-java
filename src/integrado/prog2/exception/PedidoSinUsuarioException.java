package integrado.prog2.exception;

public class PedidoSinUsuarioException extends RuntimeException {

    public PedidoSinUsuarioException(String mensaje) {
        super(mensaje);
    }
}
