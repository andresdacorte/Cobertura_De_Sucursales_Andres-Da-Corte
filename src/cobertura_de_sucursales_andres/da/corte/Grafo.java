/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
import java.util.*;

// Clase Grafo
public class Grafo {
    private Map<String, List<String>> adyacencias;

    public Grafo() {
        this.adyacencias = new HashMap<>();
    }

    public void agregarParada(String parada) {
        adyacencias.putIfAbsent(parada, new ArrayList<>());
    }

    public void agregarConexion(String origen, String destino) {
        adyacencias.get(origen).add(destino);
        adyacencias.get(destino).add(origen);
    }

    public List<String> obtenerAdyacentes(String parada) {
        return adyacencias.getOrDefault(parada, new ArrayList<>());
    }

    public Set<String> obtenerParadas() {
        return adyacencias.keySet();
    }
}