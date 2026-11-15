import java.util.Comparator;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final GestorCityLink gestor = new GestorCityLink();

    private static void precargarDatos()
            throws Excepciones.ClienteNoEncontradoException, Excepciones.CapacidadExcedidaException {

        gestor.agregarCliente(new Cliente(1, "Ana Pérez", "2657-111111", "Centro"));
        gestor.agregarCliente(new Cliente(2, "Luis Gómez", "2657-222222", "Norte"));
        gestor.agregarCliente(new Cliente(3, "María Díaz", "2657-333333", "Sur"));
        gestor.agregarCliente(new Cliente(4, "Carlos Ruiz", "2657-444444", "Este"));
        gestor.agregarCliente(new Cliente(5, "Sofía Mena", "2657-555555", "Oeste"));

        gestor.agregarRepartidor(new Repartidor(101, "Juan Torres", new Bicicleta()));
        gestor.agregarRepartidor(new Repartidor(102, "Paula Vega", new Moto()));
        gestor.agregarRepartidor(new Repartidor(103, "Diego Luna", new Camioneta()));

        gestor.agregarPedido(new PedidoNormal(1001, gestor.buscarCliente(1), 2.5, 1.0));
        gestor.agregarPedido(new PedidoNormal(1002, gestor.buscarCliente(2), 4.0, 3.5));
        gestor.agregarPedido(new PedidoUrgente(1003, gestor.buscarCliente(3), 6.0, 2.0));
        gestor.agregarPedido(new PedidoUrgente(1004, gestor.buscarCliente(4), 3.0, 1.5));
        gestor.agregarPedido(new PedidoFragil(1005, gestor.buscarCliente(5), 5.0, 4.0));
        gestor.agregarPedido(new PedidoFragil(1006, gestor.buscarCliente(1), 7.5, 6.0));
        gestor.agregarPedido(new PedidoNormal(1007, gestor.buscarCliente(3), 1.5, 0.5));
        gestor.agregarPedido(new PedidoUrgente(1008, gestor.buscarCliente(2), 8.0, 10.0));

        gestor.asignarPedido(1001, 101);
        gestor.asignarPedido(1007, 101);
        gestor.asignarPedido(1003, 102);
        gestor.asignarPedido(1005, 102);
        gestor.asignarPedido(1006, 103);
        gestor.avanzarEstado(1003);

        gestor.limpiarHistorial();
    }

    public static void main(String[] args) {
        try {
            precargarDatos();
            System.out.println("Datos iniciales cargados (5 clientes, 3 repartidores, 8 pedidos).");
        } catch (Excepciones.ClienteNoEncontradoException | Excepciones.CapacidadExcedidaException e) {
            System.out.println("Error en la precarga: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error en la precarga: " + e.getMessage());
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            try {
                ejecutarOpcion(opcion);
            } catch (Excepciones.ClienteNoEncontradoException e) {
                System.out.println("[Cliente no encontrado] " + e.getMessage());
            } catch (Excepciones.CapacidadExcedidaException e) {
                System.out.println("[Capacidad excedida] " + e.getMessage());
            } catch (Excepciones.EstructuraInvalidaException e) {
                System.out.println("[Estructura vacía] " + e.getMessage());
            } catch (Excepciones.EstadoInvalidoException e) {
                System.out.println("[Estado inválido] " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[Dato inválido] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[Error inesperado] " + e.getMessage());
            }
        } while (opcion != 0);

        System.out.println("¡Hasta luego!");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("============== CITYLINK ==============");
        System.out.println(" 1. Registrar cliente");
        System.out.println(" 2. Registrar repartidor");
        System.out.println(" 3. Registrar pedido");
        System.out.println(" 4. Asignar pedido a repartidor");
        System.out.println(" 5. Avanzar estado de un pedido");
        System.out.println(" 6. Listar clientes");
        System.out.println(" 7. Listar repartidores");
        System.out.println(" 8. Listar pedidos");
        System.out.println(" 9. Buscar cliente por código");
        System.out.println("10. Eliminar cliente");
        System.out.println("11. Rastrear pedido");
        System.out.println("12. Facturar pedido");
        System.out.println("--------- Estructuras y algoritmos ---------");
        System.out.println("13. Buscar clientes por nombre (búsqueda lineal)");
        System.out.println("14. Buscar pedido por código (búsqueda binaria)");
        System.out.println("15. Ver cola de despacho");
        System.out.println("16. Despachar siguiente pedido de la cola");
        System.out.println("17. Ordenar pedidos (inserción / mezcla)");
        System.out.println("18. Intercalar pedidos de dos repartidores");
        System.out.println("19. Deshacer última acción");
        System.out.println("20. Ver historial de acciones");
        System.out.println(" 0. Salir");
        System.out.println("======================================");
    }

    private static void ejecutarOpcion(int opcion)
            throws Excepciones.ClienteNoEncontradoException, Excepciones.CapacidadExcedidaException, Excepciones.EstructuraInvalidaException {
        switch (opcion) {
            case 1:  registrarCliente(); break;
            case 2:  registrarRepartidor(); break;
            case 3:  registrarPedido(); break;
            case 4:  asignarPedido(); break;
            case 5:  avanzarEstado(); break;
            case 6:  listarClientes(); break;
            case 7:  listarRepartidores(); break;
            case 8:  listarPedidos(); break;
            case 9:  buscarCliente(); break;
            case 10: eliminarCliente(); break;
            case 11: rastrearPedido(); break;
            case 12: facturarPedido(); break;
            case 13: buscarClientesPorNombre(); break;
            case 14: buscarPedidoBinaria(); break;
            case 15: verColaDespacho(); break;
            case 16: despacharSiguiente(); break;
            case 17: ordenarPedidos(); break;
            case 18: intercalarPedidos(); break;
            case 19: deshacerUltimaAccion(); break;
            case 20: verHistorial(); break;
            case 0:  break;
            default: System.out.println("Opción inexistente.");
        }
    }

    private static void registrarCliente() {
        System.out.println("\n===== Registrar cliente =====");
        int codigo = leerEntero("Código: ");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        String zona = leerTexto("Zona: ");

        gestor.agregarCliente(new Cliente(codigo, nombre, telefono, zona));
        System.out.println("Cliente registrado.");
    }

    private static void registrarRepartidor() {
        System.out.println("\n===== Registrar repartidor =====");
        int legajo = leerEntero("Legajo: ");
        String nombre = leerTexto("Nombre: ");

        System.out.println("Vehículo: 1) Bicicleta  2) Moto  3) Camioneta");
        int tipo = leerEntero("Tipo: ");

        Vehiculo vehiculo;
        switch (tipo) {
            case 1:  vehiculo = new Bicicleta(); break;
            case 2:  vehiculo = new Moto(); break;
            case 3:  vehiculo = new Camioneta(); break;
            default:
                System.out.println("Tipo de vehículo inválido.");
                return;
        }

        gestor.agregarRepartidor(new Repartidor(legajo, nombre, vehiculo));
        System.out.println("Repartidor registrado.");
    }

    private static void registrarPedido() throws Excepciones.ClienteNoEncontradoException {
        System.out.println("\n===== Registrar pedido =====");
        int codigo = leerEntero("Código de pedido: ");
        Cliente cliente = gestor.buscarCliente(leerEntero("Código de cliente: "));
        double distancia = leerDoublePositivo("Distancia (km): ");
        double peso = leerDoublePositivo("Peso (kg): ");

        System.out.println("Tipo: 1) Normal  2) Urgente  3) Frágil");
        int tipo = leerEntero("Tipo: ");

        Pedido pedido;
        switch (tipo) {
            case 1:  pedido = new PedidoNormal(codigo, cliente, distancia, peso); break;
            case 2:  pedido = new PedidoUrgente(codigo, cliente, distancia, peso); break;
            case 3:  pedido = new PedidoFragil(codigo, cliente, distancia, peso); break;
            default:
                System.out.println("Tipo de pedido inválido.");
                return;
        }
        gestor.agregarPedido(pedido);
        System.out.println("Pedido registrado y agregado a la cola de despacho.");
    }

    private static void asignarPedido() throws Excepciones.CapacidadExcedidaException {
        System.out.println("\n===== Asignar pedido =====");
        int codPedido = leerEntero("Código de pedido: ");
        int legajo = leerEntero("Legajo del repartidor: ");
        gestor.asignarPedido(codPedido, legajo);
        System.out.println("Pedido asignado.");
    }

    private static void avanzarEstado() {
        System.out.println("\n===== Avanzar estado =====");
        int codPedido = leerEntero("Código de pedido: ");
        gestor.avanzarEstado(codPedido);
        System.out.println("Estado actualizado.");
    }

    private static void listarClientes() {
        System.out.println(gestor.listarClientes());
    }

    private static void listarRepartidores() {
        System.out.println(gestor.listarRepartidores());
    }

    private static void listarPedidos() {
        System.out.println(gestor.listarPedidos());
    }

    private static void buscarCliente() throws Excepciones.ClienteNoEncontradoException {
        int codigo = leerEntero("Código de cliente: ");
        System.out.println(gestor.buscarCliente(codigo));
    }

    private static void eliminarCliente() throws Excepciones.ClienteNoEncontradoException {
        int codigo = leerEntero("Código de cliente: ");
        gestor.eliminarCliente(codigo);
        System.out.println("Cliente eliminado.");
    }

    private static void rastrearPedido() {
        System.out.println("\n===== Rastrear pedido =====");
        int codPedido = leerEntero("Código de pedido: ");
        System.out.println(gestor.rastrearPedido(codPedido));
    }

    private static void facturarPedido() {
        System.out.println("\n===== Facturar pedido =====");
        int codPedido = leerEntero("Código de pedido: ");
        System.out.println(gestor.facturarPedido(codPedido));
    }

    private static void buscarClientesPorNombre() {
        System.out.println("\n===== Buscar clientes por nombre =====");
        String texto = leerTexto("Nombre (o parte del nombre): ");
        System.out.println(gestor.buscarClientesPorNombre(texto));
    }

    private static void buscarPedidoBinaria() {
        System.out.println("\n===== Buscar pedido por código =====");
        int codigo = leerEntero("Código de pedido: ");
        System.out.println(gestor.detallePedido(codigo));
    }

    private static void verColaDespacho() {
        System.out.println(gestor.verColaDespacho());
    }

    private static void despacharSiguiente()
            throws Excepciones.EstructuraInvalidaException, Excepciones.CapacidadExcedidaException {
        System.out.println("\n===== Despachar siguiente pedido =====");
        System.out.println("Próximo en la cola: " + gestor.proximoDespacho());
        int legajo = leerEntero("Legajo del repartidor: ");
        System.out.println(gestor.despacharSiguiente(legajo));
    }

    private static void ordenarPedidos() {
        System.out.println("\n===== Ordenar pedidos =====");
        System.out.println("Criterio:");
        System.out.println(" 1) Urgencia > distancia > código");
        System.out.println(" 2) Peso (mayor a menor) > código");
        System.out.println(" 3) Cliente (A-Z) > código");
        int criterio = leerEntero("Criterio: ");

        Comparator<Pedido> comparador;
        switch (criterio) {
            case 1:  comparador = Comparadores.URGENCIA_DISTANCIA_CODIGO; break;
            case 2:  comparador = Comparadores.PESO_DESC_CODIGO; break;
            case 3:  comparador = Comparadores.CLIENTE_CODIGO; break;
            default:
                System.out.println("Criterio inválido.");
                return;
        }

        System.out.println("Algoritmo: 1) Inserción (elemental)  2) Mezcla / merge sort (avanzado)");
        int algoritmo = leerEntero("Algoritmo: ");
        if (algoritmo != 1 && algoritmo != 2) {
            System.out.println("Algoritmo inválido.");
            return;
        }
        System.out.println(gestor.listarPedidosOrdenados(comparador, algoritmo == 2));
    }

    private static void intercalarPedidos() {
        System.out.println("\n===== Intercalar pedidos de dos repartidores =====");
        int legajoA = leerEntero("Legajo del primer repartidor: ");
        int legajoB = leerEntero("Legajo del segundo repartidor: ");
        System.out.println(gestor.intercalarPedidosDe(legajoA, legajoB));
    }

    private static void deshacerUltimaAccion() throws Excepciones.EstructuraInvalidaException {
        String descripcion = gestor.deshacerUltimaAccion();
        System.out.println("Se deshizo: " + descripcion);
    }

    private static void verHistorial() {
        System.out.println(gestor.verHistorial());
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    private static double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(linea);
                if (valor > 0) {
                    return valor;
                }
                System.out.println("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");
            }
        }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("El campo no puede estar vacío.");
        }
    }
}