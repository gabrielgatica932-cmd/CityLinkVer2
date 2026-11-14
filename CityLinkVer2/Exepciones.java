class ClienteNoEncontradoException extends Exception {
    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    class CapacidadExcedidaException extends Exception {
        public CapacidadExcedidaException(String mensaje) {
            super(mensaje);
        }
    }
    class EstructuraInvalidaException extends Exception {
        public EstructuraInvalidaException(String mensaje) {
            super(mensaje);
        }
    }
    class EstadoInvalidoException extends RuntimeException {
        public EstadoInvalidoException(String mensaje) {
            super(mensaje);
        }
    }
}
