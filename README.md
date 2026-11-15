# Árbol B+ en Java

**Autora:** Mónica Alvarado

## Descripción

Este proyecto consiste en la implementación de un árbol B+ en Java, una estructura de datos balanceada que permite insertar, buscar y eliminar claves de manera eficiente.

Los nodos internos permiten orientar las búsquedas, mientras que las hojas almacenan las claves y sus datos asociados. Además, las hojas se encuentran enlazadas, lo que facilita los recorridos ordenados y las consultas por rango.

## Funcionalidades implementadas

- Inserción de claves y datos asociados.
- Búsqueda de claves y recuperación de datos.
- Eliminación de claves.
- División, préstamo y fusión de nodos.
- Actualización de claves separadoras.
- Impresión de la estructura del árbol.
- Recorrido de hojas enlazadas.
- Recorrido por rango con recuperación de datos.

## Estructura del proyecto

- **`NodoArbolBMas.java`:** representa los nodos del árbol y almacena sus claves, datos y referencias.
- **`ArbolBMas.java`:** implementa las operaciones de inserción, búsqueda, eliminación y recorridos.
- **`Main.java`:** contiene ejemplos para comprobar el funcionamiento del árbol.

## Ejemplo de funcionamiento

Se crea un árbol B+ de orden 4 y se insertan algunas claves:

```java
ArbolBMas arbol = new ArbolBMas(4);

arbol.insertar(10, "Dato 10");
arbol.insertar(20, "Dato 20");
arbol.insertar(30, "Dato 30");
arbol.insertar(40, "Dato 40");
```

Las operaciones principales pueden ejecutarse de la siguiente manera:

```java
arbol.buscar(20);           // true
arbol.obtenerDato(20);      // Dato 20
arbol.recorrerRango(15, 35);
arbol.eliminar(20);
arbol.imprimirArbol();
arbol.imprimirHojas();
```

El recorrido por rango muestra las claves comprendidas entre los límites indicados, junto con sus respectivos datos.

## Pruebas realizadas

Se realizaron pruebas con árboles de órdenes 3, 4 y 5, incluyendo inserciones, búsquedas, eliminaciones, reorganización de nodos y recorridos por rango.

También se comprobó el funcionamiento de las hojas enlazadas y la recuperación de datos después de eliminar claves. Los casos ejecutados produjeron los resultados esperados.

## Tecnologías utilizadas

- Java 21.
- IntelliJ IDEA.
- Git y GitHub.

## Ejecución

1. Clonar o descargar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA con JDK 21.
3. Ejecutar la clase `Main.java` para visualizar los resultados en consola.

## Contexto académico

Proyecto desarrollado para el curso de Estructuras de Datos, con el propósito de comprender el funcionamiento de los árboles B+ y las operaciones necesarias para mantener su estructura balanceada.