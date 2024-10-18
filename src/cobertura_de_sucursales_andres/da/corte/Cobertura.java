/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

/**
 *
 * @author dacor
 */
public class Cobertura {
    private MiConjunto<String> paradasCubiertas;

    public Cobertura() {
        this.paradasCubiertas = new MiConjunto<>();
    }

    public void agregarParadaCubierta(String parada) {
        paradasCubiertas.agregar(parada);
    }

    public boolean estaCubierta(String parada) {
        return paradasCubiertas.contiene(parada);
    }

    public MiConjunto<String> obtenerParadasCubiertas() {
        return paradasCubiertas;
    }

    public void limpiarCobertura() {
        paradasCubiertas.limpiar();
    }
    
    public String coberturaToString() {
        return paradasCubiertas.obtenerElementos().coberturaToString();
    }
}