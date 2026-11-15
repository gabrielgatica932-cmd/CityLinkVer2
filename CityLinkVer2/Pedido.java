public abstract class Pedido
implements Rastreable, Facturable{

    private final int codigo;
    private final Cliente cliente;
    private final double distanciaKm;
    private final double pesoKg;
    private EstadoPedido estado;
    private Repartidor repartidor;

    public Pedido(int codigo, Cliente cliente, double distanciaKm, double pesoKg){
        if (codigo <= 0){
            throw new IllegalArgumentException("El código debe ser mayor que cero.");
        }
        if(cliente == null){
            throw new IllegalArgumentException("El pedido necesita un cliente.");
        }
        if (distanciaKm <= 0 || pesoKg <= 0){
            throw new IllegalArgumentException("Distancia y peso deben ser mayores que cero.");
        }
        this.codigo = codigo;
        this.cliente = cliente;
        this.distanciaKm = distanciaKm;
        this.pesoKg = pesoKg;
        this.estado = EstadoPedido.PENDIENTE;
    }
    public abstract String getTipo();

    public abstract double calcularCosto(Vehiculo vehiculo);

    public abstract int getPrioridad();

    public void
    asignarRepartidor(Repartidor repartidor ) throws Excepciones.CapacidadExcedidaException{
        if(repartidor == null){
            throw new
            IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        if(estado != EstadoPedido.PENDIENTE){
            throw new Excepciones.EstadoInvalidoException("Solo se puede asignar un pedido pendiente (estado actual: " + estado + ").");
        }
        Vehiculo vehiculo = repartidor.getVehiculo();
        if(!vehiculo.puedeTransportar(pesoKg)){
            throw new Excepciones.CapacidadExcedidaException("La " + vehiculo.getTipo() + "no puede llevar" + pesoKg + " kg (maximo " + vehiculo.getCapacidadKg() + "Kg).");
        }
        this.repartidor = repartidor;
        this.estado = EstadoPedido.ASIGNADO;
    }
    public void avanzarEstado(){
        if(estado == EstadoPedido.PENDIENTE){
            throw new Excepciones.EstadoInvalidoException("El pedido #" + codigo + "debe asignarse a un repartidor antes de avanzar.");
        }
        estado = estado.siguiente();
    }

    public void desasignar(){
        if(estado != EstadoPedido.ASIGNADO){
            throw new Excepciones.EstadoInvalidoException("Solo se puede desasignar un pedido ASIGNADO (estado actual: " + estado + ").");
        }
        this.repartidor = null;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void retrocederEstado(){
        if(estado == EstadoPedido.PENDIENTE || estado == EstadoPedido.ASIGNADO){
            throw new Excepciones.EstadoInvalidoException("El pedido #" + codigo + "no puede retroceder desde " + estado + ".");
        }
        estado = estado.anterior();
        }

        @Override
        public String obtenerEstado(){
            String texto = getTipo() + " #" + codigo + ":" + estado;
            if(repartidor != null){
                texto += "( repartidor: " + repartidor.getNombre() + ")";
            }
            return texto;
        }

        @Override
        public double calcularTotal(){
            if(repartidor == null){
                throw new Excepciones.EstadoInvalidoException("El pedido #" + codigo + " no tiene repartidor asignado, no se puede facturar.");
            }
            return
            calcularCosto(repartidor.getVehiculo());
        }
        public int getCodigo() { return codigo;}
        public Cliente getCLiente() { return cliente;}
        public double getDistanciaKm() { return distanciaKm;}
        public double getPEsoKg(){ return pesoKg;}
        public Repartidor getRepartidor(){ return repartidor;}

        @Override
        public String toString(){
            return getTipo() + "#" + codigo + "|" + cliente.getNombre() + "|" + distanciaKm + " km | " + pesoKg + " kg | " + estado;
        } 
    }

    class PedidoNormal extends Pedido {

        public PedidoNormal(int codigo, Cliente cliente, double distanciaKm,double pesoKg){
            super(codigo,cliente,distanciaKm,pesoKg);
        }
        @Override 
        public String getTipo(){
            return "Normal";
        }

        @Override
        public double
        calcularCosto(Vehiculo vehiculo){
            return
            vehiculo.calcularCostoViaje(getDistanciaKm());
        }

        @Override
        public  int getPrioridad(){
            return 3;
        }
    }
    class PedidoUrgente extends Pedido {

        public PedidoUrgente(int codigo,Cliente cliente,double distanciaKm,double pesoKg)
{
    super(codigo , cliente, distanciaKm,pesoKg);
}
@Override
public String getTipo(){
    return "Urgente";
}

@Override
public double
calcularCosto(Vehiculo vehiculo){
    return
    vehiculo.calcularCostoViaje(getDistanciaKm()) * 1.5;
}

@Override 
public int getPrioridad(){
    return 1;
}
    }
    class PedidoFragil extends Pedido{

        public PedidoFragil(int codigo, Cliente cliente,double distanciaKm,double pesoKg){
            super(codigo,cliente,distanciaKm, pesoKg);
        }
        @Override
        public String getTipo(){
            return "Fragil";
        }

        @Override
        public double 
        calcularCosto(Vehiculo vehiculo){
            return
            vehiculo.calcularCostoViaje(getDistanciaKm()) + 200;
        }

        @Override
        public int getPrioridad(){
            return 2;
        }
    }


        
    
