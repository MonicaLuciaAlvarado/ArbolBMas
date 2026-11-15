public class Main {

    public static void main(String[] args) {

        // Crear un árbol B+ de orden 4
        ArbolBMas arbol = new ArbolBMas(4);

        // Inserción de claves y datos
        arbol.insertar(10, "Dato 10");
        arbol.insertar(20, "Dato 20");
        arbol.insertar(5, "Dato 5");
        arbol.insertar(6, "Dato 6");
        arbol.insertar(15, "Dato 15");
        arbol.insertar(30, "Dato 30");
        arbol.insertar(25, "Dato 25");
        arbol.insertar(40, "Dato 40");
        arbol.insertar(45, "Dato 45");
        arbol.insertar(50, "Dato 50");

        // Mostrar la estructura del árbol
        System.out.println("ÁRBOL B+ ORIGINAL:");
        arbol.imprimirArbol();

        // Mostrar las hojas enlazadas
        System.out.println("\nHOJAS ENLAZADAS:");
        arbol.imprimirHojas();

        // Búsqueda de claves
        System.out.println("\nBÚSQUEDA DE CLAVES:");
        System.out.println("Clave 25: " + arbol.buscar(25));
        System.out.println("Clave 35: " + arbol.buscar(35));

        // Recuperación de datos
        System.out.println("\nRECUPERACIÓN DE DATOS:");
        System.out.println("Clave 20: " + arbol.obtenerDato(20));
        System.out.println("Clave 35: " + arbol.obtenerDato(35));

        // Recorrido por rango
        System.out.println("\nRECORRIDO POR RANGO [15, 40]:");
        arbol.recorrerRango(15, 40);

        // Eliminación de claves
        System.out.println("\nELIMINACIÓN DE CLAVES:");
        arbol.eliminar(20);
        arbol.eliminar(30);

        // Mostrar el árbol después de eliminar
        System.out.println("\nÁRBOL DESPUÉS DE ELIMINAR:");
        arbol.imprimirArbol();

        // Mostrar las hojas después de eliminar
        System.out.println("\nHOJAS DESPUÉS DE ELIMINAR:");
        arbol.imprimirHojas();

        // Recorrido por rango después de eliminar
        System.out.println("\nRECORRIDO POR RANGO [15, 40]:");
        arbol.recorrerRango(15, 40);
    }
}

