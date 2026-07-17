package com.siglet.core.persistence.domain;

import com.siglet.core.persistence.enumeration.EstadoMaquina;

public class MaquinaEtiquetado {
    private int idMaquina;
    private int capacidadMaxima;

    public void setOperario(Operario operario){

    }

    public void liberarMaquina(){

    }

    public boolean estaDisponible(){
        return false;
    }

    public void cambiarEstado(EstadoMaquina nuevoEstado){

    }
}
