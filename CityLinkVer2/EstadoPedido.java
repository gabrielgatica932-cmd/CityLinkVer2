public enum EstadoPedido {

    PENDIENTE, ASIGNADO, EN_CAMINO, ENTREGADO;

    public EstadoPedido siguiente() {
        if (this == ENTREGADO) {
            throw new Excepciones.EstadoInvalidoException("No se puede avanzar más allá del estado ENTREGADO.");
        }
        return values()[ordinal() + 1];
    }

    public EstadoPedido anterior() {
        if (this == PENDIENTE) {
            throw new Excepciones.EstadoInvalidoException("No existe un estado anterior a PENDIENTE.");
        }
        return values()[this.ordinal() - 1];
    }
}
