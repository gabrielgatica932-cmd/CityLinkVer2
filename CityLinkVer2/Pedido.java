public abstract class pedido
implements Rastreable, Facturable{

    private final int codigo;
    private final Cliente cliente;
    private final double distanciaKm;
    private final double pesoKg;
    private EstadoPedido estado;
    private Repartidor repartidor;

    public Pedido(int codigo, Cliente cliente, double distanciaKm, double pesoKg){
        if (Codigo <= 0){
            throw new
            illegalArgumentException("el codigo debe ser mayor que cero.");
        }
        if(cliente == null){
            throw new
            illegalArgumentException("El pedido necesita un cliente.");
        }
        if (distanciaKm <= 0 || pesoKg <= 0){
            throw new 
            illegalArgumentException("Distancia y paso deben ser mayores que cero.");
        }
        this.codigo = codigo;
        this.cliente = cliente;
        this.distanciaKm = distanciaKm;
        this.pesoKg = pesokg;
        this.estado = EstadoPedido.PENDIENTE;
    }
    public abstract String getTipo();

    public abstract double calcularCosto(Vehiculo vehiculo);

    public abstract int getPrioridad();

    public void
    asignarRepartidor(Repartidor repartidor ) throws
    CapacidadExcedidaException{
        if(repartidor == null){
            throw new
            illegalArgumentException("El repartidor no puede ser nulo.");
        }
        if(estado != EstadoPedido.PENDIENTE){
            throw new
            EstadoinvalidoException("Solo se puede asignar un pedido pendiente (estado actual: " + estado + ").");
        }
        Vehiculo vehiculo = repartidor.getVehiculo();
        if(!vehiculo.puedeTransportar(pesoKg)){
            throw new
            CapacidadExcedidaException("La " + vehicull.getTipo() + "no puede llevar" + pesoKg + " kg (maximo " + vehiculo.getCapacidadKg() + "Kg).");
        }
        this.repartidor = repartidor;
        this.estado = EstadoPedido.ASIGNADO;
    }
    public void avanzarEstado(){
        if(estado == EstadoPedido.PENDIENTE){
            throw new
            EstadoinvalidoException("El pedido #" + codigo + "debe asignarse a un repartidor antes de avanzar.");
        }
        estado = estado.siguiente();
    }

    public void desasignar(){
        if(estado != EstadoPedido.ASIGNADO){
            throw new
            EstadoinvalidoException("Solo se puede desasignar un pedido ASIGNADO (estado actual: " + estado + ").");
        }
        this.repartidor = null;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void retrocederEstado(){
        if(estado == EstadoPedido.PENDIENTE || estado == EstadoPedido.ASIGNADO){
            throw new
            EstadoinvalidoException("El pedido #" + codigo + "no puede retroceder desde " + estado + ".");
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
                throw new
                EstadoinvalidoException("el pedido #" + codigo + "no tiene repartidor asignado, no se puede facturar.");
            }
            return
            calcularCosto(repartidor.getVehiculo());
        }
        public int getCodigo() { return codigo;}
        public CLiente getCLiente() { return cliente;}
        public double getDistanciaKm() { return distanciaKm;}
        public double getPEsoKg(){ return pesoKg;}
        public Estado Repartidor getRepartidor(){ return repartidor;}

        @Override
        public String toString(){
            return getTipo() + "#" + codigo + "|" + cliente.getnombre() + "|" + distanciaKm + " km | " + pesoKg + " kg | " + estado;
        } 
    }

    class PedidoNormal extends pedido{

        public PedidoNormal(int codigo, Cliente cliente, double distanciaKm,double pesoKg){
            super(codigo,cliente,distanciaKm,pesoKg);
        }
        @Override 
        public String getTipo(){
            return "Normal";
        }

        @Override
        public double
        calcularCosto(vehiculo vehiculo){
            return
            vehiculo.calcularCostoViaje(getDistanciaKg());
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
public String getTIpo(){
    return "Urgente";
}

@Override
public double
calcularCosto(Vehiculo vehiculo){
    return
    vehiculo.calcularCostoViaje(getViaje(get distanciaKm()) * 1,5;
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
        public String getTIpo(){
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




        
    
