import java.util.ArrayList;
import java.util.List;

public class NodoArbolBMas {

    // Atributos
    private boolean esHoja; // Indica si el nodo es una hoja o un nodo interno
    private List<Integer> claves; // Almacena las claves del nodo en orden
    private List<NodoArbolBMas> hijos; // Almacena los nodos hijos (Vacía si es una hoja)
    private List<String> datos; // Almacena los datos asociados a las claves (Debe ser null si es un nodo interno)
    private NodoArbolBMas siguiente; // Referencia a la siguiente hoja (Debe ser null si es un nodo interno)

    // Métodos
    // Constructor
    public NodoArbolBMas(boolean esHoja) {
        this.esHoja = esHoja;
        this.claves = new ArrayList<>();
        this.hijos = new ArrayList<>();

        if (esHoja) {
            this.datos = new ArrayList<>();
        } else {
            this.datos = null;
        }

        this.siguiente = null;
    }

    // Getters
    public boolean esHoja() {
        return esHoja;
    }

    public List<Integer> getClaves() {
        return claves;
    }

    public List<NodoArbolBMas> getHijos() {
        return hijos;
    }

    public List<String> getDatos() { return datos; }

    public NodoArbolBMas getSiguiente() { return siguiente; }

    // Setters
    public void setClaves(List<Integer> claves) {
        this.claves = claves;
    }

    public void setHijos(List<NodoArbolBMas> hijos) {
        this.hijos = hijos;
    }

    public void setDatos(List<String> datos) { this.datos = datos; }

    public void setSiguiente(NodoArbolBMas siguiente) { this.siguiente = siguiente; }
}
