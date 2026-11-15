import java.util.ArrayList;

public class ArbolBMas {

    // Atributos
    private final int orden;
    private NodoArbolBMas raiz;

    // Métodos
    // Constructor
    public ArbolBMas(int orden) {
        this.orden = orden;
        raiz = new NodoArbolBMas(true);
    }

    // Inserción de una llave junto con un dato, en el árbol B+
    public void insertar(int clave, String dato) {
        // Se verifica si la clave ya existe en el árbol
        if (buscar(clave)) {
            System.out.println("La clave " + clave + " ya existe en el árbol.");
            return;
        }
        // Se inserta primero de forma recursiva
        // Encuentra la hoja correspondiente e inserta la clave y su dato
        insertarRecursivo(this.raiz, clave, dato); // this.raiz corresponde al nodo desde el cual comienza la inserción

        // Se verifica si la raíz alcanzó la cantidad de claves que provoca un desbordamiento
        if(this.raiz.getClaves().size() == orden) {
            NodoArbolBMas nuevaRaiz = new NodoArbolBMas(false); // Se crea un nuevo nodo que funcionará como raíz
            nuevaRaiz.getHijos().add(this.raiz); // Se crea una nueva raíz vacía y se coloca la raíz anterior como su primer hijo
            dividirHijo(nuevaRaiz, 0); // nuevaRaiz: nodo padre que recibirá la clave separadora
            this.raiz = nuevaRaiz; // Se actualiza la referencia de la raíz del árbol
        }
    }

    // Inserción recursiva de una llave
    // Permite que los nodos se desborden temporalmente y los divide al regresar de la recursión
    private void insertarRecursivo(NodoArbolBMas nodo, int llave, String dato) {
        // Este método comienza desde la raíz

        int i = nodo.getClaves().size() - 1; // Se obtiene el índice de la última clave del nodo que estamos analizando

        if(nodo.esHoja()) { // Se ejecuta si ya llegamos a la hoja
            // Se busca la posición correspondiente para insertar la clave
            while (i >= 0 && llave < nodo.getClaves().get(i)) { // Se obtiene el índice mayor a 0, donde la llave por insertar sea mayor que la clave del nodo en el índice analizado
                i--;
            }

            // Se inserta la clave en la posición correspondiente
            nodo.getClaves().add(i + 1, llave); // como el índice que se obtuvo es la posición de la llave menor, se suma 1

            // Se inserta el dato en la misma posición que su clave
            nodo.getDatos().add(i + 1, dato);
        } else { // Se ejecuta si aún no hemos llegado a una hoja
            while(i >= 0 && llave < nodo.getClaves().get(i)) { //Decidimos por cuál hijo bajar comparando con las llaves del nodo
                i--;
            }
            i++; // Cuando encontramos la llave del hijo por donde bajar, debemos de sumar 1
            NodoArbolBMas hijo = nodo.getHijos().get(i); // Guardamos el nuevo hijo para volver a insertarRecursivo

            // Llamada recursiva para insertar la llave primero en el hijo correspondiente
            insertarRecursivo(hijo, llave, dato);

            // Evaluación de desbordamiento al retornar de la recursividad
            if(hijo.getClaves().size() == orden) {
                dividirHijo(nodo, i);
            }
        }
    }

    // Dividir un nodo que está lleno
    private void dividirHijo(NodoArbolBMas padre, int indice) {
        NodoArbolBMas nodoLleno = padre.getHijos().get(indice);
        NodoArbolBMas nuevoNodo = new NodoArbolBMas(nodoLleno.esHoja());
        int mitad = orden / 2;

        if (nodoLleno.esHoja()) {
            // En nodos hoja, se mantiene la llave media en ambos lados
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Se distribuyen los datos manteniendo su asociación con las claves
            nuevoNodo.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(mitad, nodoLleno.getDatos().size())));

            nodoLleno.setDatos(new ArrayList<>(nodoLleno.getDatos().subList(0, mitad)));

            // El nuevo nodo apunta a la hoja que seguía al nodo original
            nuevoNodo.setSiguiente(nodoLleno.getSiguiente());

            // El nodo original apunta al nuevo nodo
            nodoLleno.setSiguiente(nuevoNodo);

            // El padre recibe como separador la primera llave del nuevo nodo
            int llaveMedia = nuevoNodo.getClaves().getFirst();
            padre.getClaves().add(indice, llaveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
        else {
            // En nodos internos, la llave media sube y se elimina del hijo
            int claveMedia = nodoLleno.getClaves().get(mitad);
            nuevoNodo.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(mitad + 1, nodoLleno.getClaves().size())));
            nodoLleno.setClaves(new ArrayList<>(nodoLleno.getClaves().subList(0, mitad)));

            // Mover los hijos del nodo dividido
            nuevoNodo.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(mitad + 1, nodoLleno.getHijos().size())));
            nodoLleno.setHijos(new ArrayList<>(nodoLleno.getHijos().subList(0, mitad + 1)));

            padre.getClaves().add(indice, claveMedia);
            padre.getHijos().add(indice + 1, nuevoNodo);
        }
    }

    // Impresión del árbol como parte de la interfaz pública del árbol
    public void imprimirArbol() {
        imprimirNodo(raiz, "", true);
    }

    // Impresión recursiva privada de los nodos a partir de uno inicial
    private void imprimirNodo(NodoArbolBMas nodo, String indentacion, boolean esUltimo) {
        System.out.println(indentacion + "+- " + (nodo.esHoja() ? "Hoja > " : "Interno > ") + nodo.getClaves());
        indentacion += esUltimo ? "   " : "|  ";
        for(int i = 0; i < nodo.getHijos().size(); i++) {
            imprimirNodo(nodo.getHijos().get(i), indentacion, i == (nodo.getHijos().size() - 1));
        }
    }

    // Búsqueda de un nodo como parte de la interfaz pública del árbol
    public boolean buscar(int llave) {
        return buscarNodo(raiz, llave);
    }

    // Búsqueda recursiva de una clave en las hojas del árbol B+
    private boolean buscarNodo(NodoArbolBMas nodo, int llave) {

        // Se inicia la búsqueda desde la primera clave
        int i = 0;

        // Se busca la posición correspondiente a la clave
        while (i < nodo.getClaves().size()
                && llave >= nodo.getClaves().get(i)) {
            i++;
        }

        // Si es una hoja, se verifica si contiene la clave
        if (nodo.esHoja()) {
            return nodo.getClaves().contains(llave);
        }

        // Si es un nodo interno, se continúa la búsqueda en el hijo correspondiente
        return buscarNodo(nodo.getHijos().get(i), llave);
    }


    // Obtiene el dato asociado a una clave
    public String obtenerDato(int llave) {

        NodoArbolBMas nodo = raiz;

        // Se desciende hasta la hoja correspondiente
        while (!nodo.esHoja()) {

            int i = 0;

            while (i < nodo.getClaves().size()
                    && llave >= nodo.getClaves().get(i)) {
                i++;
            }

            nodo = nodo.getHijos().get(i);
        }

        // Se busca la posición de la clave en la hoja
        int posicion = nodo.getClaves().indexOf(llave);

        // Si la clave no existe, se retorna null
        if (posicion == -1) {
            return null;
        }

        // Se retorna el dato asociado a la clave
        return nodo.getDatos().get(posicion);
    }

    // Eliminación de una clave del árbol B+
    public void eliminar(int clave) {

        // Se verifica si la clave existe en el árbol
        if (!buscar(clave)) {
            System.out.println("La clave " + clave + " no existe en el árbol.");
            return;
        }

        // Se inicia la eliminación desde la raíz
        eliminarRecursivo(raiz, clave);

        // Se verifica si la raíz es interna y quedó sin claves
        if (!raiz.esHoja() && raiz.getClaves().isEmpty()) {

            // El único hijo se convierte en la nueva raíz
            raiz = raiz.getHijos().get(0);
        }

        // Se actualizan las claves separadoras del árbol
        actualizarSeparadores(raiz);
    }

    // Eliminación recursiva de una clave
    private void eliminarRecursivo(NodoArbolBMas nodo, int clave) {

        // Si el nodo es una hoja, se elimina la clave y su dato
        if (nodo.esHoja()) {

            // Se obtiene la posición (índice) de la clave que se busca,
            // por ejemplo, si se busca 50 en el nodo con claves [30,40,50], devuelve 2
            int indice = nodo.getClaves().indexOf(clave);

            // Se elimina la clave
            nodo.getClaves().remove(indice);

            // Se elimina el dato asociado
            nodo.getDatos().remove(indice);

            return;
        }

        // Se inicia la búsqueda del hijo correspondiente
        int i = 0;

        // Se determina en cuál hijo se encuentra la clave
        while (i < nodo.getClaves().size()
                && clave >= nodo.getClaves().get(i)) {
            i++;
        }

        // Se continúa la eliminación en el hijo seleccionado
        eliminarRecursivo(nodo.getHijos().get(i), clave);

        // Se obtiene el hijo donde se realizó la eliminación
        NodoArbolBMas hijo = nodo.getHijos().get(i);

        // Se calcula el mínimo de claves según el tipo de nodo
        int minimoClaves;

        if (hijo.esHoja()) {

            // Mínimo de claves para una hoja
            minimoClaves = orden / 2;

        } else {

            // Mínimo de claves para un nodo interno
            minimoClaves = (orden + 1) / 2 - 1;
        }

        // Se verifica si la hoja quedó con menos claves del mínimo
        if (hijo.esHoja() && hijo.getClaves().size() < minimoClaves) {
            // Se redistribuyen o fusionan las hojas según corresponda
            rebalancearHoja(nodo, i, minimoClaves);
        }

        // Se verifica si un nodo interno quedó con menos claves del mínimo
        if (!hijo.esHoja() && hijo.getClaves().size() < minimoClaves) {

            // Se redistribuye o fusiona el nodo interno según corresponda
            rebalancearInterno(nodo, i, minimoClaves);
        }
    }

    // Se rebalancea una hoja que quedó con menos claves del mínimo
    private void rebalancearHoja(NodoArbolBMas nodo, int i, int minimoClaves) {

        // Se obtiene la hoja que necesita rebalancearse
        NodoArbolBMas hijo = nodo.getHijos().get(i);

        // Se verifica si existe una hermana a la derecha
        if (i + 1 < nodo.getHijos().size()) {// i es la posición de la hoja donde eliminamos una clave

            // Se obtiene la hermana derecha
            NodoArbolBMas hermanaDerecha = nodo.getHijos().get(i + 1);

            // Se verifica si la hermana puede prestar una clave
            if (hermanaDerecha.getClaves().size() > minimoClaves) {

                // Se traslada la primera clave de la hermana derecha al final del hijo
                hijo.getClaves().add(hermanaDerecha.getClaves().remove(0));

                // Se traslada el dato asociado a la clave
                hijo.getDatos().add(hermanaDerecha.getDatos().remove(0));

                // Se actualiza la clave separadora del padre
                nodo.getClaves().set(i, hermanaDerecha.getClaves().getFirst());

                // Se termina el rebalanceo de esta hoja
                return;
            }
        }

        // Se verifica si existe una hermana a la izquierda
        if (i > 0) {

            // Se obtiene la hermana izquierda
            NodoArbolBMas hermanaIzquierda = nodo.getHijos().get(i - 1);

            // Se verifica si la hermana puede prestar una clave
            if (hermanaIzquierda.getClaves().size() > minimoClaves) {

                // Se obtiene el índice de la última clave de la hermana
                int ultimoIndice = hermanaIzquierda.getClaves().size() - 1;

                // Se traslada la última clave de la hermana al inicio del hijo
                hijo.getClaves().add(0, hermanaIzquierda.getClaves().remove(ultimoIndice));

                // Se traslada también el dato asociado
                hijo.getDatos().add(0, hermanaIzquierda.getDatos().remove(ultimoIndice));

                // Se actualiza la clave separadora del padre
                nodo.getClaves().set(i - 1, hijo.getClaves().getFirst());

                // Se termina el rebalanceo de esta hoja
                return;
            }
        }

        // Se verifica si existe una hermana a la derecha para fusionar
        if (i + 1 < nodo.getHijos().size()) {

            // Se obtiene la hermana derecha
            NodoArbolBMas hermanaDerecha = nodo.getHijos().get(i + 1);

            // Se agregan las claves de la hermana derecha al hijo
            hijo.getClaves().addAll(hermanaDerecha.getClaves());

            // Se agregan los datos asociados
            hijo.getDatos().addAll(hermanaDerecha.getDatos());

            // Se actualiza el enlace a la siguiente hoja
            hijo.setSiguiente(hermanaDerecha.getSiguiente());

            // Se elimina la referencia a la hermana derecha del padre
            nodo.getHijos().remove(i + 1);

            // Se elimina la clave separadora correspondiente
            nodo.getClaves().remove(i);
        }

        // Si no existe una hermana derecha, se fusiona con la izquierda
        else if (i > 0) {

            // Se obtiene la hermana izquierda
            NodoArbolBMas hermanaIzquierda = nodo.getHijos().get(i - 1);

            // Se trasladan las claves del hijo a la hermana izquierda
            hermanaIzquierda.getClaves().addAll(hijo.getClaves());

            // Se trasladan los datos asociados
            hermanaIzquierda.getDatos().addAll(hijo.getDatos());

            // Se actualiza el enlace a la siguiente hoja
            hermanaIzquierda.setSiguiente(hijo.getSiguiente());

            // Se elimina la referencia al hijo que fue fusionado
            nodo.getHijos().remove(i);

            // Se elimina la clave separadora correspondiente
            nodo.getClaves().remove(i - 1);
        }
    }

    // Se rebalancea un nodo interno que quedó con menos claves del mínimo
    private void rebalancearInterno(NodoArbolBMas nodo, int i, int minimoClaves) {

        // Se obtiene el nodo interno que necesita rebalancearse
        NodoArbolBMas hijo = nodo.getHijos().get(i);

        // Se verifica si existe una hermana a la derecha
        if (i + 1 < nodo.getHijos().size()) {

            // Se obtiene la hermana derecha
            NodoArbolBMas hermanaDerecha = nodo.getHijos().get(i + 1);

            // Se verifica si la hermana puede prestar una clave
            if (hermanaDerecha.getClaves().size() > minimoClaves) {

                // Se baja la clave separadora del padre al hijo
                hijo.getClaves().add(nodo.getClaves().get(i));

                // Se traslada el primer hijo de la hermana derecha
                hijo.getHijos().add(hermanaDerecha.getHijos().remove(0));

                // Se sube la primera clave de la hermana al padre
                nodo.getClaves().set(i, hermanaDerecha.getClaves().remove(0));

                return;
            }
        }

        // Se verifica si existe una hermana a la izquierda
        if (i > 0) {

            // Se obtiene la hermana izquierda
            NodoArbolBMas hermanaIzquierda = nodo.getHijos().get(i - 1);

            // Se verifica si la hermana puede prestar una clave
            if (hermanaIzquierda.getClaves().size() > minimoClaves) {

                // Se baja la clave separadora del padre al inicio del hijo
                hijo.getClaves().add(0, nodo.getClaves().get(i - 1));

                // Se traslada el último hijo de la hermana izquierda
                int ultimoHijo = hermanaIzquierda.getHijos().size() - 1;

                hijo.getHijos().add(0,
                        hermanaIzquierda.getHijos().remove(ultimoHijo));

                // Se sube la última clave de la hermana al padre
                int ultimaClave = hermanaIzquierda.getClaves().size() - 1;

                nodo.getClaves().set(i - 1,
                        hermanaIzquierda.getClaves().remove(ultimaClave));

                return;
            }
        }

        // Se verifica si existe una hermana a la derecha para fusionar
        if (i + 1 < nodo.getHijos().size()) {

            // Se obtiene la hermana derecha
            NodoArbolBMas hermanaDerecha = nodo.getHijos().get(i + 1);

            // Se baja la clave separadora del padre al hijo
            hijo.getClaves().add(nodo.getClaves().remove(i));

            // Se agregan las claves de la hermana derecha
            hijo.getClaves().addAll(hermanaDerecha.getClaves());

            // Se agregan los hijos de la hermana derecha
            hijo.getHijos().addAll(hermanaDerecha.getHijos());

            // Se elimina la referencia a la hermana derecha del padre
            nodo.getHijos().remove(i + 1);
        }

        // Si no existe una hermana derecha, se fusiona con la izquierda
        else if (i > 0) {

            // Se obtiene la hermana izquierda
            NodoArbolBMas hermanaIzquierda = nodo.getHijos().get(i - 1);

            // Se baja la clave separadora del padre a la hermana izquierda
            hermanaIzquierda.getClaves().add(nodo.getClaves().remove(i - 1));

            // Se agregan las claves del hijo a la hermana izquierda
            hermanaIzquierda.getClaves().addAll(hijo.getClaves());

            // Se agregan los hijos del nodo que se está fusionando
            hermanaIzquierda.getHijos().addAll(hijo.getHijos());

            // Se elimina la referencia al hijo fusionado
            nodo.getHijos().remove(i);
        }
    }

    // Se actualizan las claves separadoras de los nodos internos
    private void actualizarSeparadores(NodoArbolBMas nodo) {

        // Si el nodo es una hoja, no tiene separadores
        if (nodo.esHoja()) {
            return;
        }

        // Se recorren los hijos para actualizar sus separadores
        for (NodoArbolBMas hijo : nodo.getHijos()) {
            actualizarSeparadores(hijo);
        }

        // Se actualizan las claves según el primer valor de cada subárbol derecho
        for (int i = 1; i < nodo.getHijos().size(); i++) {

            NodoArbolBMas hijoDerecho = nodo.getHijos().get(i);

            // Se busca la primera clave del subárbol derecho
            while (!hijoDerecho.esHoja()) {
                hijoDerecho = hijoDerecho.getHijos().get(0);
            }

            nodo.getClaves().set(i - 1, hijoDerecho.getClaves().getFirst());
        }
    }

    // Se imprimen las hojas utilizando sus enlaces
    public void imprimirHojas() {

        NodoArbolBMas actual = raiz;

        // Se busca la hoja más a la izquierda
        while (!actual.esHoja()) {
            actual = actual.getHijos().get(0);
        }

        // Se recorren las hojas mediante siguiente
        while (actual != null) {

            System.out.print(actual.getClaves());

            actual = actual.getSiguiente();

            if (actual != null) {
                System.out.print(" -> ");
            }
        }

        System.out.println();
    }


    // Recorrido por rango
    // Muestra las claves y sus datos dentro del rango indicado
    public void recorrerRango(int inicio, int fin) {

        // Se verifica que el rango sea válido
        if (inicio > fin) {
            System.out.println("El rango indicado no es válido.");
            return;
        }

        // Se comienza desde la raíz
        NodoArbolBMas nodo = raiz;

        // Se busca la hoja donde debería encontrarse el inicio
        while (!nodo.esHoja()) {

            int i = 0;

            while (i < nodo.getClaves().size()
                    && inicio >= nodo.getClaves().get(i)) {
                i++;
            }

            nodo = nodo.getHijos().get(i);
        }

        // Se recorren las hojas enlazadas
        while (nodo != null) {

            // Se recorren las claves de la hoja actual
            for (int i = 0; i < nodo.getClaves().size(); i++) {

                int clave = nodo.getClaves().get(i);

                // Si se supera el límite superior, termina el recorrido
                if (clave > fin) {
                    return;
                }

                // Se muestran las claves dentro del rango
                if (clave >= inicio) {
                    System.out.println("Clave: " + clave
                            + " - Dato: " + nodo.getDatos().get(i));
                }
            }

            // Se continúa con la siguiente hoja
            nodo = nodo.getSiguiente();
        }
    }

}