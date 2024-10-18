/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
class MiMapa<K, V> {
    private MiLista<NodoMapa<K, V>> elementos;

    public MiMapa() {
        this.elementos = new MiLista<>();
    }

    public void poner(K clave, V valor) {
        NodoMapa<K, V> nodoExistente = obtenerNodo(clave);
        if (nodoExistente != null) {
            nodoExistente.valor = valor;
        } else {
            elementos.agregar(new NodoMapa<>(clave, valor));
        }
    }

    public V obtener(K clave) {
        NodoMapa<K, V> nodo = obtenerNodo(clave);
        return nodo != null ? nodo.valor : null;
    }

    public boolean contieneClave(K clave) {
        return obtenerNodo(clave) != null;
    }

    public MiConjunto<K> obtenerClaves() {
        MiConjunto<K> claves = new MiConjunto<>();
        Nodo<NodoMapa<K, V>> actual = elementos.cabeza;
        while (actual != null) {
            claves.agregar(actual.valor.clave);
            actual = actual.siguiente;
        }
        return claves;
    }

    private NodoMapa<K, V> obtenerNodo(K clave) {
        Nodo<NodoMapa<K, V>> actual = elementos.cabeza;
        while (actual != null) {
            if (actual.valor.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }
}