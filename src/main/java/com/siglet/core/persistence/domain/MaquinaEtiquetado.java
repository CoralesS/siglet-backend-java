package com.siglet.core.persistence.domain;

import com.siglet.core.persistence.enumeration.EstadoMaquina;
import com.siglet.core.persistence.enumeration.EstadoOperario;

public class MaquinaEtiquetado {
    private int idMaquina;
    private int capacidadMaxima;
    EstadoMaquina estadoMaquina;
    Operario operario;

    // Constructor
    public MaquinaEtiquetado(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.estadoMaquina = EstadoMaquina.OPERATIVO;
    }

    public MaquinaEtiquetado() {
    }

    public void setOperario(Operario operario){
        this.operario = operario;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void liberarMaquina(){
        this.operario = null;
    }

    public boolean estaDisponible(){
        return operario == null;
    }

    public void cambiarEstado(EstadoMaquina nuevoEstado){
        this.estadoMaquina = nuevoEstado;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public EstadoMaquina getEstadoMaquina() {
        return estadoMaquina;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setEstadoMaquina(EstadoMaquina estadoMaquina) {
        this.estadoMaquina = estadoMaquina;
    }
}

