/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cobertura_de_sucursales_andres.da.corte;

import java.util.*;

/**
 *
 * @author dacor
 */
public class Parada {
    private String nombre;
    private boolean esTransferencia;
    private boolean tieneSucursal;

    public Parada(String nombre, boolean esTransferencia) {
        this.nombre = nombre;
        this.esTransferencia = esTransferencia;
        this.tieneSucursal = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEsTransferencia() {
        return esTransferencia;
    }

    public boolean tieneSucursal() {
        return tieneSucursal;
    }

    public void setTieneSucursal(boolean tieneSucursal) {
        this.tieneSucursal = tieneSucursal;
    }
}
