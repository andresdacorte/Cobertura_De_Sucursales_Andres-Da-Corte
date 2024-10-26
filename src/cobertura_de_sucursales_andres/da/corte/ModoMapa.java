/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */

// Clase tipo parametro para la clase MiMapa.

class NodoMapa<K, V> {
    K clave;
    V valor;

    NodoMapa(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }
}
