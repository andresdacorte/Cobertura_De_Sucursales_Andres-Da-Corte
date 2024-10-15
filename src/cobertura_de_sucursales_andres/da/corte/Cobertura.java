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
import javax.swing.*;
import java.io.*;
import org.json.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;

public class Cobertura {
    
    private Set<String> paradasCubiertas;

    public Cobertura() {
        this.paradasCubiertas = new HashSet<>();
    }

    public void agregarParadaCubierta(String parada) {
        paradasCubiertas.add(parada);
    }

    public boolean estaCubierta(String parada) {
        return paradasCubiertas.contains(parada);
    }

    public Set<String> obtenerParadasCubiertas() {
        return paradasCubiertas;
    }

    public void limpiarCobertura() {
        paradasCubiertas.clear();
    }
}

