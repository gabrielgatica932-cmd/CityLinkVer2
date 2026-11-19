# CityLink: declaración de uso de inteligencia artificial

Materia: Programación I (2026) - Proyecto Integrador CityLink
Integrantes: Walter Gabriel Gatica, Axel Farías, Alexis Becerra, Tomas Litardo
Fecha: 19/11/2026

## 1. Herramienta utilizada

- Nombre: Claude, de Anthropic.
- Tipo de IA: asistente conversacional de inteligencia artificial generativa, basado en un modelo de lenguaje grande (LLM). Recibe texto, capturas de pantalla y archivos, y responde con explicaciones y código.
- Modalidad de uso: chat. No estuvo conectada al editor ni al repositorio: el equipo le enviaba consultas, capturas y archivos del proyecto, y después copiaba, adaptaba y probaba lo que respondía.
- Versión: Claude Sonnet 5.5.
- No se utilizaron otras herramientas de IA.

## 2. Para qué se usó

### 2.1 Recordar fundamentos

Se usó para repasar y entender, con ejemplos del propio proyecto:

- Colecciones de java.util: qué hace cada una y cuándo conviene usarla (ArrayList, ArrayDeque, LinkedList y TreeMap), con sus métodos principales (add, get, put, containsKey, push, pop, peekFirst, pollFirst).
- Pila (LIFO) y cola (FIFO), y por qué deshacer se resuelve con una pila.
- Genéricos y la diferencia con los arreglos fijos del Entregable 1.
- Algoritmos: búsqueda lineal y binaria, ordenamiento por inserción (elemental) y por mezcla (avanzado), e intercalación de dos listas ya ordenadas.
- Comparator multicriterio: cómo desempatar con varios criterios.
- Lambdas y Runnable: cómo guardar un bloque de código para ejecutarlo después (la reversión de cada acción).
- Reglas de Java sobre archivos: una sola clase pública por archivo y clases estáticas anidadas (las excepciones dentro de Excepciones).
- Repaso de los fundamentos del Entregable 1: herencia, abstracción, interfaces y polimorfismo puro.

### 2.2 Estructuras y organización del proyecto

- Cómo migrar de arreglos fijos a colecciones dinámicas sin romper el modelo.
- Qué estructura usar en cada caso: TreeMap para clientes por código, ArrayList para repartidores y pedidos, LinkedList para la cola de despacho y ArrayDeque para la pila de deshacer.
- Cómo mantener la lista de pedidos siempre ordenada por código para poder usar búsqueda binaria.
- Cómo implementar la cola con prioridad con un ListIterator, usando una prioridad que cada tipo de pedido define por polimorfismo y desempatando por código para que deshacer restaure el lugar original.
- Cómo estructurar las acciones reversibles y qué estructuras toca cada una.
- Cómo agrupar clases en menos archivos y en qué orden crear cada uno.

### 2.3 Corrección de errores

Se usó para revisar el código del equipo y encontrar problemas, entre ellos:

- La numeración de la cola de despacho no avanzaba (todas las líneas salían con el mismo número).
- Eliminar un cliente con pedidos mostraba "Error inesperado", porque se lanzaba una excepción que Main no clasificaba.
- Un salto de línea escrito de forma incorrecta en la salida de la intercalación, y espacios faltantes en varios mensajes.
- Diferencias de texto entre el menú y el código (por ejemplo, "Frágil" y "Fragil").
- Un nombre de excepción que no describía bien su uso (estructura inválida en lugar de estructura vacía).

### 2.4 Inspiración

- La idea de guardar en cada acción una descripción y su reversión, para armar la pila de deshacer sin preguntar el tipo de acción.
- La idea de una cola de despacho que ordena por prioridad y desempata por código.
- Los criterios de ordenamiento propuestos: urgencia, distancia y código; peso y código; y nombre del cliente y código.
- La ampliación del menú a 20 opciones y una precarga con pedidos en distintos estados para poder demostrar la cola, el deshacer y la intercalación.

### 2.5 Propuestas de código

La IA redactó propuestas de código para las partes nuevas (gestor con colecciones, algoritmos, comparadores y la clase de acciones). Los algoritmos de búsqueda, ordenamiento e intercalación están escritos a mano, sin Collections.sort, Arrays.sort ni binarySearch. Ese código fue revisado, adaptado y probado por el equipo.

## 3. Cómo se validó el código

- Se compiló todo el proyecto con javac *.java.
- Se ejecutó el programa con la precarga y se probó cada una de las 20 opciones del menú.
- Se probaron los casos borde: lista vacía, cola vacía, deshacer con la pila vacía, datos numéricos inválidos o vacíos, códigos duplicados, eliminar un cliente con pedidos, capacidad excedida al despachar (el pedido debe seguir en la cola) e intercalar con el mismo legajo dos veces.
- Se comprobó que deshacer una asignación devuelva el pedido exactamente a su lugar en la cola, y que la búsqueda binaria funcione aunque se registren pedidos con códigos fuera de orden.
- Se comparó el resultado de los dos ordenamientos (inserción y mezcla), que debe ser idéntico para cada criterio.
- Se verificó que el modelo, el gestor y los algoritmos no usen Scanner ni System.out.
- Se revisó el código línea por línea entre los integrantes.
- Además, la IA compiló y ejecutó una versión del código en su propio entorno de pruebas. Eso no reemplaza las pruebas del equipo.

## 4. Límites de la IA y responsabilidad del equipo

- La IA cometió errores y dejó detalles sin resolver, por eso todo se revisó y se probó.
- Las decisiones finales y la integración de los archivos las tomó el equipo.
- Todo el código fue leído y entendido por los integrantes, quienes pueden explicar cualquier parte en la defensa oral.
