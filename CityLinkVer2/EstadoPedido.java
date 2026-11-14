public EstadoPedido 
siguiente(){
    if(this == ENTREGADO){
        throw new 
        EstadoInvalidoException("No se puede avanzar más alla del estado ENTREGADO.");
    }
    return values()[this.ordinal() + 1];
}
public EstadoPedido anterior(){
    if(this == PENDIENTE){
        throw new
        EstadoInvalidoException("No existe un estado anterior a PENDIENTE.");
    }
    return values()[this.ordinal() - 1];
}