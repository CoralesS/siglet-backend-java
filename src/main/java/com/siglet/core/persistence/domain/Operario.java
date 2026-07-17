package com.siglet.core.persistence.domain;

import com.siglet.core.persistence.enumeration.EstadoMaquina;

public class Operario {
    private String dNI;
    private String nombre;
    private String apellido;

    public void cambiarEstado(EstadoMaquina nuevoEstado){

    }

    public boolean estaDisponible(){
        return false;
    }
}
