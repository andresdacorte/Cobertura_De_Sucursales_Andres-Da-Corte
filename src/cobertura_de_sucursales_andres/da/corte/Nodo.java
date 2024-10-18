/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
class Nodo<T> {
    T valor;
    Nodo<T> siguiente;

    Nodo(T valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
