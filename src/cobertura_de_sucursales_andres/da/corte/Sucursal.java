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
public class Sucursal {
    private String parada;
    private int radioCobertura;

    public Sucursal(String parada, int radioCobertura) {
        this.parada = parada;
        this.radioCobertura = radioCobertura;
    }

    public String getParada() {
        return parada;
    }

    public int getRadioCobertura() {
        return radioCobertura;
    }
}

