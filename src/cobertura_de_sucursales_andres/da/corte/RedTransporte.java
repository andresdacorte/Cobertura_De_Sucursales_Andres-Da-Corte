/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;


import java.util.*;
import javax.swing.*;
import java.io.*;
import org.json.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Component;
/**
 *
 * @author dacor
 */

public class RedTransporte {
    private Grafo grafo;
    public Map<String, Parada> paradas;
    private List<Sucursal> sucursales;
    private int radioCobertura;

    public RedTransporte() {
        this.grafo = new Grafo();
        this.paradas = new HashMap<>();
        this.sucursales = new ArrayList<>();
        this.radioCobertura = 3; // Valor predeterminado
    }

    // Cargar red desde archivo JSON utilizando JFileChooser
    public void cargarRed() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea);
                }
                readJSON(contenido.toString());
                JOptionPane.showMessageDialog(null, "Red de transporte cargada correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar el archivo.");
            }
        }
    }

    // Leer archivo JSON y cargar la red de transporte
    
    private void readJSON(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray lineasArray = jsonObject.optJSONArray("Metro de Caracas");
            if (lineasArray == null) {
                lineasArray = jsonObject.optJSONArray("Transmilenio");
            }
            if (lineasArray == null) {
                throw new JSONException("Formato de JSON no reconocido");
            }

            for (int l = 0; l < lineasArray.length(); l++) {
                JSONObject lineaObj = lineasArray.getJSONObject(l);
                String linea = lineaObj.keys().next();
                JSONArray paradasArray = lineaObj.getJSONArray(linea);
                String paradaAnterior = null;

                for (int i = 0; i < paradasArray.length(); i++) {
                    Object paradaObj = paradasArray.get(i);
                    String parada;
                    boolean esTransferencia = false;

                    if (paradaObj instanceof JSONObject) {
                        JSONObject conexionPeatonal = (JSONObject) paradaObj;
                        parada = conexionPeatonal.keys().next();
                        esTransferencia = true;
                    } else if (paradaObj instanceof String) {
                        parada = (String) paradaObj;
                    } else {
                        throw new JSONException("Formato incorrecto en el JSON");
                    }

                    grafo.agregarParada(parada);
                    paradas.putIfAbsent(parada, new Parada(parada, esTransferencia));

                    if (paradaAnterior != null) {
                        grafo.agregarConexion(paradaAnterior, parada);
                    }
                    paradaAnterior = parada;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al procesar el archivo JSON: " + e.getMessage());
        }
    }


    // Mostrar el grafo
    public void mostrarGrafo() {
    System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Red de Transporte");

        for (String parada : grafo.obtenerParadas()) {
            Node node = graph.addNode(parada);
            node.setAttribute("ui.label", parada);
        }

        for (String origen : grafo.obtenerParadas()) {
            for (String destino : grafo.obtenerAdyacentes(origen)) {
                String edgeId = origen + "-" + destino;
                if (graph.getEdge(edgeId) == null && graph.getEdge(destino + "-" + origen) == null) {
                    Edge edge = graph.addEdge(edgeId, origen, destino);
                    edge.setAttribute("ui.label", origen + " - " + destino);
                }
            }
        }

        graph.setAttribute("ui.stylesheet", "graph { fill-color: white; padding: 100px; } node { size: 26px; fill-color: blue; text-alignment: center; text-size: 16; } edge { fill-color: gray; text-size: 12; }");
        graph.display();
    }


    // Establecer el valor de t
    public void establecerRadioCobertura(int nuevoRadio) {
        this.radioCobertura = nuevoRadio;
        JOptionPane.showMessageDialog(null, "Radio de cobertura actualizado a " + nuevoRadio);
    }

    // Colocar sucursal en una parada
    public void colocarSucursal(String nombreParada) {
        if (paradas.containsKey(nombreParada)) {
            Parada parada = paradas.get(nombreParada);
            parada.setTieneSucursal(true);
            Sucursal nuevaSucursal = new Sucursal(nombreParada, radioCobertura);
            sucursales.add(nuevaSucursal);
            // Recalcular cobertura total
        }
    }

    // Quitar sucursal de una parada
    public void quitarSucursal(String nombreParada) {
        if (paradas.containsKey(nombreParada)) {
            Parada parada = paradas.get(nombreParada);
            parada.setTieneSucursal(false);
            sucursales.removeIf(sucursal -> sucursal.getParada().equals(nombreParada));
            // Recalcular cobertura total
        }
    }

    // Revisar cobertura de una sucursal
    public void revisarCoberturaSucursal(String nombreParada) {
        // Implementar la revisión de cobertura utilizando BFS o DFS
    }

    // Revisar cobertura total
    public void revisarCoberturaTotal() {
        // Implementar la revisión de cobertura total de la ciudad
    }
}