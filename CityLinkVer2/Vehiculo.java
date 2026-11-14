public abstract class Vehiculo {
    private final double capacidadKg;
    private final double velocidadKmh;
    private final double costoPorKm;

    public Vehiculo(double capacidadKg, double velocidadKmh, double costoPorKm) {
        if(capacidadKg <= 0 || velocidadKmh <= 0 || costoPorKm <= 0){
            throw new
         IllegalArgumentException(" capacidad, velocidad y costo deben ser mayores que cero.");
        }
    }
    this.capacidadKg = capacidadKg;
    this.velocidadKmh = velocidadKmh;
    this.costoPorKm = costoPorKm;
}
public abstract String getTipo();

public abstract double calcularCostoViaje(double distanciaKm);

public double calcularTiempoHoras(double distanciaKm) {
    return distanciaKm / velocidadKmh;
}

public boolean puedeTransportar(double pesoKg) {
    return pesoKg <= capacidadKg;
}
 
public double getCapacidadKg() {
    return capacidadKg;
}

public double getVelocidadKmh() {
    return velocidadKmh;
}

public double getCostoPorKm() {
    return costoPorKm;
}

@Override
public String toString() {
    return getTipo()+ "(capacidadKg=" + capacidadKg + ", velocidad:" + velocidadKmh +" km/h,costo:$" + costoporKm + "/km)";
}


class Bicicleta extends Vehiculo {

    public Bicicleta(){
        super(5.0, 15.0,20.0);
    }

    @Override
    public String getTipo() {
        return "Bicicleta";
    }
    @Override 
    public double calcularCostoViaje(double distanciaKm) {
        return  getCostoPorKm()*distanciaKm;
    }
}

class Moto extends Vehiculo {

    public Moto(){
        super (20.0, 50.0, 80.0);
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
    @Override 
    public double calcularCostoViaje(double distanciaKm) {
        return  getCostoPorKm()*distanciaKm;
    }
}

class Camioneta extends Vehiculo {

    private static final double COSTO_FIJO = 500.0;

    public Camioneta(){
        super (500.0, 40.0, 200.0);
    }
    @Override 
    public String getTipo() {
        return "Camioneta";
    }
  
    @Override
    public double 
    calcularCostoViaje(double distanciaKm) {
        return  CARGO_FIJO +(getCostoPorKm()*distanciaKm);
    }
}


