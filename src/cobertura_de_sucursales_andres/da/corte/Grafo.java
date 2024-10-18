/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
public class Grafo {
    private MiMapa<String, MiLista<String>> adyacencias;

    public Grafo() {
        this.adyacencias = new MiMapa<>();
    }

    public void agregarParada(String parada) {
        if (!adyacencias.contieneClave(parada)) {
            adyacencias.poner(parada, new MiLista<>());
        }
    }

    public void agregarConexion(String origen, String destino) {
        adyacencias.obtener(origen).agregar(destino);
        adyacencias.obtener(destino).agregar(origen);
    }

    public MiLista<String> obtenerAdyacentes(String parada) {
        return adyacencias.obtener(parada) != null ? adyacencias.obtener(parada) : new MiLista<>();
    }

    public MiConjunto<String> obtenerParadas() {
        return adyacencias.obtenerClaves();
    }
}