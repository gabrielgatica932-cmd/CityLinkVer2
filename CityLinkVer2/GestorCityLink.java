import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;

public class GestorCityLink {

    private final TreeMap<Integer, Cliente> clientes = new TreeMap<>();
    private final ArrayList<Repartidor> repartidores = new ArrayList<>();
    private final ArrayList<Pedido> pedidos = new ArrayList<>();
    private final LinkedList<Pedido> colaDespacho = new LinkedList<>();
    private final ArrayDeque<Accion> historial = new ArrayDeque<>();

    // Agregar un cliente al sistema (TreeMap)
    public void agregarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        int codigo = cliente.getCodigo();
        
        if (clientes.containsKey(codigo)) {
            throw new IllegalArgumentException("El cliente con código " + codigo + " ya existe.");
        }

        clientes.put(codigo, cliente);
        historial.push(new Accion("Registrar cliente #" + codigo, () -> clientes.remove(codigo)));
    }

    public Cliente buscarCliente(int codigo) throws Excepciones.ClienteNoEncontradoException {
        Cliente cliente = clientes.get(codigo);
        if (cliente == null) {
            throw new Excepciones.ClienteNoEncontradoException("Cliente con código " + codigo + " no encontrado.");
        }
        return cliente;
    }

    public void eliminarCliente(int codigo) throws Excepciones.ClienteNoEncontradoException {
        Cliente cliente = buscarCliente(codigo);
        for (Pedido pedido : pedidos) {
            if (pedido.getCliente().getCodigo() == codigo) {
                throw new IllegalStateException("No se puede eliminar el cliente con pedidos asociados.");
            }
        }
        clientes.remove(codigo);
        historial.push(new Accion("Eliminar cliente #" + codigo, () -> clientes.put(codigo, cliente)));
    }

    public String listarClientes() {
        if (clientes.isEmpty()) {
            return "No hay clientes registrados.";
        }
        StringBuilder sb = new StringBuilder();
        for (Cliente cliente : clientes.values()) {
            sb.append(cliente.toString()).append("\n");
        }
        return sb.toString();
    }

    // Agregar un repartidor al sistema (ArrayList)
    public void agregarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        if (indiceRepartidor(repartidor.getLegajo()) != -1) {
            throw new IllegalArgumentException("El repartidor con legajo " + repartidor.getLegajo() + " ya existe.");
        }
        repartidores.add(repartidor);
        historial.push(new Accion("Registrar repartidor #" + repartidor.getLegajo(), () -> repartidores.remove(repartidor)));
    }

    public String listarRepartidores() {
        if (repartidores.isEmpty()) {
            return "No hay repartidores registrados.";
        }
        StringBuilder sb = new StringBuilder();
        for (Repartidor repartidor : repartidores) {
            sb.append(repartidor.toString()).append("\n");
        }
        return sb.toString();
    }

    private int indiceRepartidor(int legajo) {
        for (int i = 0; i < repartidores.size(); i++) {
            if (repartidores.get(i).getLegajo() == legajo) {
                return i;
            }
        }
        return -1;
    }

    private Repartidor buscarRepartidor(int Legajo) {
        int pos = indiceRepartidor(Legajo);
        if (pos == -1) {
            throw new IllegalArgumentException("Repartidor con legajo " + Legajo + " no encontrado.");
        }
        return repartidores.get(pos);
    }

    // Agregar un pedido al sistema (ArrayList y LinkedList)
    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }
        if (Algoritmos.busquedaBinariaPorCodigo(pedidos, pedido.getCodigo()) != -1) {
            throw new IllegalArgumentException("El pedido con código " + pedido.getCodigo() + " ya existe.");
        }
        insertarOrdenado(pedido);
        encolar(pedido);
        historial.push(new Accion("Registrar pedido #" + pedido.getCodigo(), () -> {
            pedidos.remove(pedido);
            colaDespacho.remove(pedido);
        }));
    }

    private void insertarOrdenado(Pedido pedido) {
        int i = 0;
        while (i < pedidos.size() && pedidos.get(i).getCodigo() < pedido.getCodigo()) {
            i++;
        }
        pedidos.add(i, pedido);
    }

    private Pedido buscarPedido(int codigo) {
        int pos = Algoritmos.busquedaBinariaPorCodigo(pedidos, codigo);
        if (pos == -1) {
            throw new IllegalArgumentException("Pedido con código " + codigo + " no encontrado.");
        }
        return pedidos.get(pos);
    }

    public String detallePedido(int codigo) {
        Pedido pedido = buscarPedido(codigo);
        return pedido + "\n" + pedido.obtenerEstado();
    }

    public String listarPedidos() {
        if (pedidos.isEmpty()) {
            return "No hay pedidos registrados.";
        }
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : pedidos) {
            sb.append(pedido.toString()).append("\n");
        }
        return sb.toString();
    }

    public String listarPedidosOrdenados(Comparator<Pedido> comparador, boolean usarMezcla) {
        if (pedidos.isEmpty()) {
            return "No hay pedidos registrados.";
        }
        ArrayList<Pedido> ordenados;
        String nombreAlgoritmo;
        if (usarMezcla) {
            ordenados = Algoritmos.ordenarPorMezcla(pedidos, comparador);
            nombreAlgoritmo = "Ordenamiento por Mezcla";
        } else {
            ordenados = new ArrayList<>(pedidos);
            Algoritmos.ordenarPorInsercion(ordenados, comparador);
            nombreAlgoritmo = "Ordenamiento por Inserción";
        }
        StringBuilder sb = new StringBuilder("Pedidos ordenados con " + nombreAlgoritmo + ":\n");
        for (Pedido pedido : ordenados) {
            sb.append(pedido).append("\n");
        }
        return sb.toString();
    }

    public String intercalarPedidosDe(int legajoA, int legajoB) {
        if (legajoA == legajoB) {
            throw new IllegalArgumentException("Los legajos deben ser diferentes.");
        }
        Repartidor repartidorA = buscarRepartidor(legajoA);
        Repartidor repartidorB = buscarRepartidor(legajoB);
        Comparator<Pedido> criterio = Comparadores.URGENCIA_DISTANCIA_CODIGO;

        ArrayList<Pedido> listaA = Algoritmos.ordenarPorMezcla(pedidosDe(repartidorA), criterio);
        ArrayList<Pedido> listaB = Algoritmos.ordenarPorMezcla(pedidosDe(repartidorB), criterio);
        ArrayList<Pedido> unidos = Algoritmos.intercalar(listaA, listaB, criterio);

        StringBuilder sb = new StringBuilder();
        sb.append("Pedidos de").append(repartidorA.getNombre()).append(" (ordenados):\n");
        agregarLista(sb, listaA);
        sb.append("Pedidos de ").append(repartidorB.getNombre()).append(" (ordenados):\\n");
        agregarLista(sb, listaB);
        sb.append("Intercalados (urgencia > distancia > código):\n");
        agregarLista(sb, unidos);
        return sb.toString();
    }

    private ArrayList<Pedido> pedidosDe(Repartidor repartidor) {
        ArrayList<Pedido> lista = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            if (pedido.getRepartidor() == repartidor) {
                lista.add(pedido);
            }
        }
        return lista;
    }

    private void agregarLista(StringBuilder sb, ArrayList<Pedido> lista) {
        if (lista.isEmpty()) {
            sb.append("  (sin pedidos)\n");
            return;
        }
        for (Pedido pedido : lista) {
            sb.append(" ").append(pedido).append("\n");
        }
    }

    private void encolar(Pedido pedido) {
        ListIterator<Pedido> it = colaDespacho.listIterator();
        while (it.hasNext()) {
            Pedido actual = it.next();
            boolean actualVaDespues = actual.getPrioridad() > pedido.getPrioridad()
                    || (actual.getPrioridad() == pedido.getPrioridad()
                        && actual.getCodigo() > pedido.getCodigo());
            if (actualVaDespues) {
                it.previous();
                it.add(pedido);
                return;
            }
        }
        colaDespacho.addLast(pedido);
    }

    public String verColaDespacho() {
        if (colaDespacho.isEmpty()) {
            return "La cola de despacho está vacía (no hay pedidos pendientes).";
        }
        StringBuilder sb = new StringBuilder("Cola de despacho (el primero es el próximo en salir):\n");
        int posicion = 1;
        for (Pedido pedido : colaDespacho) {
            sb.append(posicion).append(". ").append(pedido).append("\n");
        }
        return sb.toString();
    }

    public String despacharSiguiente(int legajo)
            throws Excepciones.EstructuraInvalidaException, Excepciones.CapacidadExcedidaException {
        Pedido siguiente = colaDespacho.peekFirst();
        if (siguiente == null) {
            throw new Excepciones.EstructuraInvalidaException("La cola de despacho está vacía.");
        }
        Repartidor repartidor = buscarRepartidor(legajo);
        siguiente.asignarRepartidor(repartidor);
        colaDespacho.pollFirst();
        apilarDesasignacion(siguiente);
        return "Pedido despacho " + siguiente.obtenerEstado();
    }

    public void asignarPedido(int codPedido, int legajo) throws Excepciones.CapacidadExcedidaException {
        Pedido pedido = buscarPedido(codPedido);
        Repartidor repartidor = buscarRepartidor(legajo);
        pedido.asignarRepartidor(repartidor);
        colaDespacho.remove(pedido);
        apilarDesasignacion(pedido);
    }

    private void apilarDesasignacion(Pedido pedido) {
        historial.push(new Accion("Asignar pedido #" + pedido.getCodigo(), () -> {
            pedido.desasignar();
            encolar(pedido);
        }));
    }

    public void avanzarEstado(int codPedido) {
        Pedido pedido = buscarPedido(codPedido);
        pedido.avanzarEstado();
        historial.push(new Accion("Avanzar estado del pedido #" + codPedido,
                () -> pedido.retrocederEstado()));
    }

    public String rastrearPedido(int codPedido) {
        Rastreable rastreable = buscarPedido(codPedido);
        return rastreable.obtenerEstado();
    }

    public String facturarPedido(int codPedido) {
        Pedido pedido = buscarPedido(codPedido);
        Facturable facturable = pedido;
        double total = facturable.calcularTotal();

        Repartidor repartidor = pedido.getRepartidor();
        Vehiculo vehiculo = repartidor.getVehiculo();
        double minutos = vehiculo.calcularTiempoHoras(pedido.getDistanciaKm()) * 60;

        StringBuilder sb = new StringBuilder();
        sb.append("========== FACTURA ==========\n");
        sb.append("Pedido:      #").append(pedido.getCodigo())
            .append(" (").append(pedido.getTipo()).append(")\n");
        sb.append("Cliente:     ").append(pedido.getCliente().getNombre()).append("\n");
        sb.append("Repartidor:  ").append(repartidor.getNombre())
            .append(" - ").append(vehiculo.getTipo()).append("\n");
        sb.append(String.format("Distancia:   %.1f km%n", pedido.getDistanciaKm()));
        sb.append(String.format("Tiempo est.: %.0f min%n", minutos));
        sb.append(String.format("TOTAL:       $%.2f%n", total));
        sb.append(String.format("Comisión repartidor (10%%): $%.2f%n", repartidor.calcularComision(total)));
        sb.append("=============================");
        return sb.toString();
    }

    public String deshacerUltimaAccion() throws Excepciones.EstructuraInvalidaException {
        if (historial.isEmpty()) {
            throw new Excepciones.EstructuraInvalidaException("No hay acciones para deshacer.");
        }
        Accion accion = historial.pop();
        return accion.deshacer();
    }

    public String verHistorial() {
        if (historial.isEmpty()) {
            return "El historial está vacío (no hay acciones para deshacer).";
        }
        StringBuilder sb = new StringBuilder("Acciones que se pueden deshacer (la primera es la más reciente):\n");
        int numero = 1;
        for (Accion accion : historial) {
            sb.append(numero).append(". ").append(accion.getDescripcion()).append("\n");
            numero++;
        }
        return sb.toString();
    }

    public void limpiarHistorial() {
        historial.clear();
    }
}

class Accion {

    private final String descripcion;
    private final Runnable reversa;

    public Accion(String descripcion, Runnable reversa) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La acción necesita una descripción.");
        }
        if (reversa == null) {
            throw new IllegalArgumentException("La acción necesita saber cómo se revierte.");
        }
        this.descripcion = descripcion;
        this.reversa = reversa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String deshacer() {
        reversa.run();
        return descripcion;
    }
}