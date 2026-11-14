class Cliente{

    private final int codigo;
    private String nombre;
    private String telefono;
    private String zona;

    public Cliente(int codigo, String nombre, String telefono, String zona){
        if(codigo <= 0){
            throw new
            illegalArgumentException("El codigo debe ser mayor que cero.");
        }
        this.codigo = codigo;
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
        }

        public int getCodigo(){
            return codigo;
        }
        public String getNombre(){
            return nombre;
        }

        public void setNombre(String nombre){
            if(nombre == null || nombre.trim().isEmpty()){
                throw new 
                illegalArgumentException("El nombre no puede ser nulo  o vacio");
            }
            this.nombre = nombre.thrim();
        }

        public String getTelefono(){
            return telefono;
        }

        public  void setTelefono(string telefono){
            if(telefono == null || telefono.trim().isEmpty()){
                throw new illegalArgumentException("El teelfono no puede ser nulo o vacio.");
            }
            this.telefono = telefono.trim();
        }

        public String getZona(){
            return zona;
        }

        public  void setZona(String zona){
            if(zona == null || zona.trim().isEmpty()){
                throw new 
                illegalArgumentException("La zona no puede ser nula o vacia.");
            }
            this.zona = zona.trim();
        }

        @Override 
        public string toString(){
            return "cliente #" + codigo + "|" + nombre + " |tel: " + telefono + " | Zona: " + zona;
        }
    }

    class Repartidor{
        
        private static final double 
        PORCENTAJE_COMISION = 0.10;

        private final int legajo;
        private String nombre;
        private final Vehiculo vehiculo;

        public Repartidor(int legajo, String nombre, Vehiculo vehiculo){
            if(legajo <= 0){
                throw new 
                illegalArgumentException("El legajo debe ser mayor que cero.");
            }
            if( vehiculo == null){
                throw new 
                illegalArgumentException("El repartidor necesita un vehiculo");
            }
            this.legajo = legajo;
            this.vehiculo = vehiculo;
            setNombre(nombre);
        }

        public  int getLegajo(){
            return legajo;
        }

        public String  getNOmbre(){
            return nombre;
        }
        
        public void setNombre(string nombre){
            if( nombre == null || nombre.trim().isEmpty()){
                throw new 
                illegalArgumentException("El nombre no puede ser nulo o vacio.");
            }
            this.nombre = nombre.trim();
        }

        public  Vehiculo getVehiculo(){
            return Vehiculo;
        }

        public double 
        calcularComision(double montoEnvio)
        {
            return montoEnvio * PORCENTAJE_COMISION;
        }

        @Override 
        public String toString(){
            return "Repartidor #" + legajo + "|" + nombre + "|" + vehiculo.getTipo();
        }
    }