/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
public class MiConjunto<T> {
    private MiLista<T> elementos;

    public MiConjunto() {
        this.elementos = new MiLista<>();
    }

    public void agregar(T valor) {
        if (!contiene(valor)) {
            elementos.agregar(valor);
        }
    }

    public boolean contiene(T valor) {
        Nodo<T> actual = elementos.cabeza;
        while (actual != null) {
            if (actual.valor.equals(valor)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void eliminar(T valor) {
        Nodo<T> actual = elementos.cabeza;
        Nodo<T> previo = null;
        while (actual != null) {
            if (actual.valor.equals(valor)) {
                if (previo == null) {
                    elementos.cabeza = actual.siguiente;
                } else {
                    previo.siguiente = actual.siguiente;
                }
                return;
            }
            previo = actual;
            actual = actual.siguiente;
        }
    }

    public void limpiar() {
        elementos = new MiLista<>();
    }

    public MiLista<T> obtenerElementos() {
        return elementos;
    }

    public boolean contieneTodos(MiConjunto<T> otroConjunto) {
        Nodo<T> actual = otroConjunto.obtenerElementos().cabeza;
        while (actual != null) {
            if (!contiene(actual.valor)) {
                return false;
            }
            actual = actual.siguiente;
        }
        return true;
    }
    
    public int longitud() {
        return elementos.longitud();
    }
}