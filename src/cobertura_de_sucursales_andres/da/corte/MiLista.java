/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
public class MiLista<T> {
    public Nodo<T> cabeza;

    public MiLista() {
        this.cabeza = null;
    }

    public void agregar(T valor) {
        Nodo<T> nuevoNodo = new Nodo<>(valor);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    public int longitud() {
        int contador = 0;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    public String coberturaToString() {
        StringBuilder resultado = new StringBuilder();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            resultado.append(actual.valor).append(", ");
            actual = actual.siguiente;
        }
        if (resultado.length() > 0) {
            resultado.setLength(resultado.length() - 2); // Eliminar la última coma y espacio
        }
        return resultado.toString();
    }
}