import java.util.Comparator;

public final class Comparadores{
    
    private Comparadores() {
        
    }
    public static final Comparator<Pedido> URGENCIA_DISTANCIA_CODIGO = (a,b) -> {
        int resultado = Integer.compare(a.getPrioridad(), b.getPrioridad());
        if (resultado!= 0) {
            return resultado;
        }
        resultado = Double.compare(a.getDistanciaKm(), b.getDistanciaKm());
        if (resultado != 0) {
            return resultado;
        }
        return Integer.compare(a.getCodigo(), b.getCodigo());
    };


    public static final Comparator<Pedido> PESO_DESC_CODIGO= (a,b) -> {
        int resultado = Double.compare(b.getPesoKg(), a.getPesoKg());
        if (resultado != 0) {
            return resultado;
        }
        return Integer.compare(a.getCodigo(), b.getCodigo());
    };

    public static final Comparator<Pedido> CLIENTE_CODIGO = (a, b) -> {
        int resultado = a.getCliente().getNombre()
                .compareToIgnoreCase(b.getCliente().getNombre());
        if (resultado != 0) {
            return resultado;
        }
        return Integer.compare(a.getCodigo(), b.getCodigo());
    };

}
