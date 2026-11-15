import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class Algoritmos {
    private Algoritmos() {
    }

    public static ArrayList<Cliente> busquedaLinealPorNombre(Collection<Cliente> clientes, String texto){
        ArrayList<Cliente> encontrados=new ArrayList<>();
        String buscado=texto.trim().toLowerCase();
        for(Cliente cliente: clientes){
            if(cliente.getNombre().toLowerCase().contains(buscado)){
                encontrados.add(cliente);
            }
        }
        return encontrados;
    }


    public static int busquedaBinariaPorCodigo(List<Pedido> pedidosOrdenados, int codigo) {
        int inicio = 0;
        int fin = pedidosOrdenados.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int codigoMedio = pedidosOrdenados.get(medio).getCodigo();
            if (codigoMedio == codigo) {
                return medio;
            }
            if (codigoMedio < codigo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
    }
    return -1;
    }

    public static void ordenarPorInsercion(List<Pedido>lista,Comparator<Pedido>comparador){
        for (int i=1; i<lista.size(); i++){
            Pedido actual=lista.get(i);
            int j=i-1;
            while(j>=0 && comparador.compare(lista.get(j),actual)>0){
                lista.set(j+1,lista.get(j));
                j--;
            }
            lista.set(j+1,actual);
        }
    }

    public static ArrayList<Pedido> ordenarPorMezcla(List<Pedido> lista, Comparator<Pedido> comparador){
        if(lista.size() <= 1) {
            return new 
            ArrayList<>(lista);
        }
        int medio= lista.size() / 2;
        ArrayList<Pedido> izquierda = ordenarPorMezcla(lista.subList(0, medio), comparador);
        ArrayList<Pedido> derecha = ordenarPorMezcla(lista.subList(medio,lista.size()), comparador);
        return intercalar(izquierda, derecha, comparador);
    }

    public static ArrayList<Pedido> intercalar(List<Pedido>a, List<Pedido>b, Comparator<Pedido> comparador){

        ArrayList<Pedido> resultado= new ArrayList<>();
        int i=0;
        int j=0;
        while (i < a.size() && j < b.size()) {
            if(comparador.compare(a.get(i), b.get(j)) <= 0){
                resultado.add(a.get(i));
                i++;
        } else {
            resultado.add(b.get(j));
            j++;
            }
        }
    while (i < a.size()) {
        resultado.add(a.get(i));
        i++;
    }
    while (j < b.size()) {
        resultado.add(b.get(j));
        j++;
    }
    return resultado;
    }   
}
