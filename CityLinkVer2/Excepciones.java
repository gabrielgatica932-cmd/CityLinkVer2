public class Excepciones {

    public static class ClienteNoEncontradoException extends Exception {
        public ClienteNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

    public static class CapacidadExcedidaException extends Exception {
        public CapacidadExcedidaException(String mensaje) {
            super(mensaje);
        }
    }

    public static class EstructuraInvalidaException extends Exception {
        public EstructuraInvalidaException(String mensaje) {
            super(mensaje);
        }
    }

    public static class EstadoInvalidoException extends RuntimeException {
        public EstadoInvalidoException(String mensaje) {
            super(mensaje);
        }
    }
}