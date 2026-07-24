package com.siglet.core.persistence.domain;

import com.siglet.core.persistence.enumeration.EstadoOperario;

public class Operario {
    //Atributos
    private String dNI;
    private String nombre;
    private String apellido;
    private EstadoOperario estadoOperario;

    //Constructor
    public Operario(String dNI, String nombre, String apellido){
        this.dNI = dNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estadoOperario = EstadoOperario.DISPONIBLE;
    }


    public void cambiarEstado(EstadoOperario nuevoEstado){
        this.estadoOperario = nuevoEstado;

    }

    public boolean estaDisponible(){
        return EstadoOperario.DISPONIBLE.equals(this.estadoOperario);
    }
}
