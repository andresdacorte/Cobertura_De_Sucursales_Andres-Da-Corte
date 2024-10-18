/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;


import javax.swing.*;
import java.io.*;
import org.json.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.ViewerPipe;



public class RedTransporte {
    private Grafo grafo;
    public MiMapa<String, Parada> paradas;
    public MiLista<Sucursal> sucursales;
    private int radioCobertura;
    private Viewer viewer;


    public RedTransporte() {
        this.grafo = new Grafo();
        this.paradas = new MiMapa<>();
        this.sucursales = new MiLista<>();
        this.radioCobertura = 3; // Valor predeterminado
        this.viewer = null;
    }

    // Métodos de la clase adaptados para trabajar sin java.util
    public void colocarSucursal(String nombreParada) {
        if (paradas.contieneClave(nombreParada)) {
            Parada parada = paradas.obtener(nombreParada);
            parada.setTieneSucursal(true);
            Sucursal nuevaSucursal = new Sucursal(nombreParada, radioCobertura);
            sucursales.agregar(nuevaSucursal);
        }
    }

    public void quitarSucursal(String nombreParada) {
        if (paradas.contieneClave(nombreParada)) {
            Parada parada = paradas.obtener(nombreParada);
            parada.setTieneSucursal(false);
            Nodo<Sucursal> actual = sucursales.cabeza;
            Nodo<Sucursal> previo = null;
            while (actual != null) {
                if (actual.valor.getParada().equals(nombreParada)) {
                    if (previo == null) {
                        sucursales.cabeza = actual.siguiente;
                    } else {
                        previo.siguiente = actual.siguiente;
                    }
                    break;
                }
                previo = actual;
                actual = actual.siguiente;
            }
        }
    }

    public void agregarLinea(String nombreLinea, MiLista<String> paradasLinea) {
        String paradaAnterior = null;

        Nodo<String> nodoActual = paradasLinea.cabeza;
        while (nodoActual != null) {
            String parada = nodoActual.valor;
            if (!paradas.contieneClave(parada)) {
                grafo.agregarParada(parada);
                paradas.poner(parada, new Parada(parada, false));
            }

            if (paradaAnterior != null) {
                grafo.agregarConexion(paradaAnterior, parada);
            }
            paradaAnterior = parada;
            nodoActual = nodoActual.siguiente;
        }
        System.out.println("Línea " + nombreLinea + " agregada correctamente.");
    }
    
    public void cargarDesdeArchivo() {
        limpiarDatosAnteriores();
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea);
                }
                readJSON(contenido.toString());
            } catch (java.io.IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }
        }
    }

    // Método para leer y procesar JSON
    private void readJSON(String jsonData) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonData);
            org.json.JSONArray lineasArray = jsonObject.optJSONArray("Metro de Caracas");
            if (lineasArray == null) {
                lineasArray = jsonObject.optJSONArray("Transmilenio");
            }
            if (lineasArray == null) {
                throw new org.json.JSONException("Formato de JSON no reconocido");
            }

            for (int l = 0; l < lineasArray.length(); l++) {
                org.json.JSONObject lineaObj = lineasArray.getJSONObject(l);
                String linea = lineaObj.keys().next();
                org.json.JSONArray paradasArray = lineaObj.getJSONArray(linea);
                String paradaAnterior = null;

                for (int i = 0; i < paradasArray.length(); i++) {
                    Object paradaObj = paradasArray.get(i);
                    String parada;
                    boolean esTransferencia = false;

                    if (paradaObj instanceof org.json.JSONObject) {
                        org.json.JSONObject conexionPeatonal = (org.json.JSONObject) paradaObj;
                        parada = conexionPeatonal.keys().next();
                        esTransferencia = true;
                    } else if (paradaObj instanceof String) {
                        parada = (String) paradaObj;
                    } else {
                        throw new org.json.JSONException("Formato incorrecto en el JSON");
                    }

                    grafo.agregarParada(parada);
                    if (!paradas.contieneClave(parada)) {
                        paradas.poner(parada, new Parada(parada, esTransferencia));
                    }

                    if (paradaAnterior != null) {
                        grafo.agregarConexion(paradaAnterior, parada);
                    }
                    paradaAnterior = parada;
                }
            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error al procesar el archivo JSON: " + e.getMessage());
        }
    }


    public void mostrarGrafo() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Red de Transporte");

        Nodo<String> paradaNodo = grafo.obtenerParadas().obtenerElementos().cabeza;
        while (paradaNodo != null) {
            String parada = paradaNodo.valor;
            Node node = graph.addNode(parada);
            node.setAttribute("ui.label", parada);
            paradaNodo = paradaNodo.siguiente;
        }

        Nodo<String> origenNodo = grafo.obtenerParadas().obtenerElementos().cabeza;
        while (origenNodo != null) {
            String origen = origenNodo.valor;
            Nodo<String> destinoNodo = grafo.obtenerAdyacentes(origen).cabeza;
            while (destinoNodo != null) {
                String destino = destinoNodo.valor;
                String edgeId = origen + "-" + destino;
                if (graph.getEdge(edgeId) == null && graph.getEdge(destino + "-" + origen) == null) {
                    Edge edge = graph.addEdge(edgeId, origen, destino);
                    edge.setAttribute("ui.label", origen + " - " + destino);
                }
                destinoNodo = destinoNodo.siguiente;
            }
            origenNodo = origenNodo.siguiente;
        }

        graph.setAttribute("ui.stylesheet", "graph { fill-color: white; padding: 200px; } " +
                "node { size: 40px; fill-color: blue; text-alignment: above; text-size: 18; } " +
                "edge { fill-color: gray; text-size: 14; arrow-size: 10px, 5px; }" +
                "edge:clicked { fill-color: red; }" +
                "node:clicked { fill-color: red; }");

        viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }

    // Método para limpiar el grafo cuando se cierra la ventana del grafo
    private void limpiarGrafo() {
        if (viewer != null) {
            viewer.close();
            viewer = null;
        }
    }
    
    private void limpiarDatosAnteriores() {
        grafo = new Grafo();
        paradas = new MiMapa<>();
        sucursales = new MiLista<>();
        limpiarGrafo();
    }
    
    public void establecerRadioCobertura(int nuevoRadio) {
        this.radioCobertura = nuevoRadio;
    }
    
    public void revisarCoberturaSucursal(String nombreParada, String metodoBusqueda) {
        if (!paradas.contieneClave(nombreParada)) {
            JOptionPane.showMessageDialog(null, "La parada seleccionada no existe.");
            return;
        }

        MiConjunto<String> visitados = new MiConjunto<>();
        MiLista<String> cobertura = new MiLista<>();
        int limite = radioCobertura;

        if (metodoBusqueda.equalsIgnoreCase("BFS")) {
            MiLista<String> cola = new MiLista<>();
            cola.agregar(nombreParada);
            visitados.agregar(nombreParada);

            while (cola.longitud() > 0 && limite > 0) {
                int nivelSize = cola.longitud();
                for (int i = 0; i < nivelSize; i++) {
                    String actual = cola.cabeza.valor;
                    cola.cabeza = cola.cabeza.siguiente;
                    cobertura.agregar(actual);
                    Nodo<String> adyacentes = grafo.obtenerAdyacentes(actual).cabeza;
                    while (adyacentes != null) {
                        String adyacente = adyacentes.valor;
                        if (!visitados.contiene(adyacente)) {
                            visitados.agregar(adyacente);
                            cola.agregar(adyacente);
                        }
                        adyacentes = adyacentes.siguiente;
                    }
                }
                limite--;
            }
        } else if (metodoBusqueda.equalsIgnoreCase("DFS")) {
            MiLista<String> pila = new MiLista<>();
            pila.agregar(nombreParada);
            visitados.agregar(nombreParada);

            while (pila.longitud() > 0 && limite > 0) {
                String actual = pila.cabeza.valor;
                pila.cabeza = pila.cabeza.siguiente;
                cobertura.agregar(actual);
                Nodo<String> adyacentes = grafo.obtenerAdyacentes(actual).cabeza;
                while (adyacentes != null) {
                    String adyacente = adyacentes.valor;
                    if (!visitados.contiene(adyacente)) {
                        visitados.agregar(adyacente);
                        pila.agregar(adyacente);
                    }
                    adyacentes = adyacentes.siguiente;
                }
                limite--;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Método de búsqueda no reconocido. Use BFS o DFS.");
            return;
        }
        JOptionPane.showMessageDialog(null, "Cobertura desde la sucursal en " + nombreParada + ":\n" + cobertura.coberturaToString());
    }
    
    public void revisarCoberturaTotal() {
        MiConjunto<String> cubiertas = new MiConjunto<>();

        // Obtener todas las paradas cubiertas por cada sucursal
        Nodo<Sucursal> actualSucursal = sucursales.cabeza;
        while (actualSucursal != null) {
            String paradaInicial = actualSucursal.valor.getParada();
            int limite = radioCobertura;
            MiConjunto<String> visitados = new MiConjunto<>();
            MiLista<String> cola = new MiLista<>();
            cola.agregar(paradaInicial);
            visitados.agregar(paradaInicial);

            while (cola.longitud() > 0 && limite > 0) {
                int nivelSize = cola.longitud();
                for (int i = 0; i < nivelSize; i++) {
                    String actual = cola.cabeza.valor;
                    cola.cabeza = cola.cabeza.siguiente;
                    cubiertas.agregar(actual);
                    Nodo<String> adyacentes = grafo.obtenerAdyacentes(actual).cabeza;
                    while (adyacentes != null) {
                        String adyacente = adyacentes.valor;
                        if (!visitados.contiene(adyacente)) {
                            visitados.agregar(adyacente);
                            cola.agregar(adyacente);
                        }
                        adyacentes = adyacentes.siguiente;
                    }
                }
                limite--;
            }
            actualSucursal = actualSucursal.siguiente;
        }

        // Verificar si todas las paradas están cubiertas
        MiConjunto<String> paradasTotales = grafo.obtenerParadas();
        if (cubiertas.contieneTodos(paradasTotales)) {
            JOptionPane.showMessageDialog(null, "Todas las paradas están cubiertas por las sucursales existentes.");
        } else {
            // Sugerir paradas para colocar nuevas sucursales usando un enfoque eficiente
            MiConjunto<String> noCubiertas = new MiConjunto<>();
            Nodo<String> paradaNodo = paradasTotales.obtenerElementos().cabeza;
            while (paradaNodo != null) {
                if (!cubiertas.contiene(paradaNodo.valor)) {
                    noCubiertas.agregar(paradaNodo.valor);
                }
                paradaNodo = paradaNodo.siguiente;
            }

            MiLista<String> sugerencias = new MiLista<>();
            while (noCubiertas.longitud() > 0) {
                String mejorParada = null;
                int maxCobertura = 0;

                // Encontrar la parada que cubra la mayor cantidad de paradas no cubiertas
                Nodo<String> noCubiertaNodo = noCubiertas.obtenerElementos().cabeza;
                while (noCubiertaNodo != null) {
                    String parada = noCubiertaNodo.valor;
                    MiConjunto<String> cobertura = obtenerCoberturaDesdeParada(parada);
                    int coberturaCount = 0;
                    Nodo<String> coberturaNodo = cobertura.obtenerElementos().cabeza;
                    while (coberturaNodo != null) {
                        if (noCubiertas.contiene(coberturaNodo.valor)) {
                            coberturaCount++;
                        }
                        coberturaNodo = coberturaNodo.siguiente;
                    }
                    if (coberturaCount > maxCobertura) {
                        maxCobertura = coberturaCount;
                        mejorParada = parada;
                    }
                    noCubiertaNodo = noCubiertaNodo.siguiente;
                }

                if (mejorParada != null) {
                    sugerencias.agregar(mejorParada);
                    MiConjunto<String> cobertura = obtenerCoberturaDesdeParada(mejorParada);
                    Nodo<String> coberturaNodo = cobertura.obtenerElementos().cabeza;
                    while (coberturaNodo != null) {
                        noCubiertas.eliminar(coberturaNodo.valor);
                        coberturaNodo = coberturaNodo.siguiente;
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Se sugiere colocar sucursales en las siguientes paradas para lograr la cobertura total: " + sugerencias.coberturaToString());
        }
    }

    private MiConjunto<String> obtenerCoberturaDesdeParada(String paradaInicial) {
        MiConjunto<String> cobertura = new MiConjunto<>();
        MiLista<String> cola = new MiLista<>();
        MiConjunto<String> visitados = new MiConjunto<>();
        int limite = radioCobertura;

        cola.agregar(paradaInicial);
        visitados.agregar(paradaInicial);

        while (cola.longitud() > 0 && limite > 0) {
            int nivelSize = cola.longitud();
            for (int i = 0; i < nivelSize; i++) {
                String actual = cola.cabeza.valor;
                cola.cabeza = cola.cabeza.siguiente;
                cobertura.agregar(actual);
                Nodo<String> adyacentes = grafo.obtenerAdyacentes(actual).cabeza;
                while (adyacentes != null) {
                    String adyacente = adyacentes.valor;
                    if (!visitados.contiene(adyacente)) {
                        visitados.agregar(adyacente);
                        cola.agregar(adyacente);
                    }
                    adyacentes = adyacentes.siguiente;
                }
            }
            limite--;
        }

        return cobertura;
    }

}