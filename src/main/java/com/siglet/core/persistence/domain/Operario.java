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

    public Operario() {
    }

    public void cambiarEstado(EstadoOperario nuevoEstado){
        this.estadoOperario = nuevoEstado;

    }

    public boolean estaDisponible(){
        return EstadoOperario.DISPONIBLE.equals(this.estadoOperario);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public EstadoOperario getEstadoOperario() {
        return estadoOperario;
    }

    public void setdNI(String dNI) {
        this.dNI = dNI;
    }

    public void setEstadoOperario(EstadoOperario estadoOperario) {
        this.estadoOperario = estadoOperario;
    }

    public String getdNI() {
        return dNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
