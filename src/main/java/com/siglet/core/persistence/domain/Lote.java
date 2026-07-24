package com.siglet.core.persistence.domain;

import com.siglet.core.persistence.enumeration.EstadoOperario;
import com.siglet.core.persistence.enumeration.PrioridadLote;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Lote {
    private String codigo;
    private int cantidad;
    private LocalDateTime inicioProceso;
    private LocalDateTime finProceso;
    private PrioridadLote prioridadLote;
    private MaquinaEtiquetado maquinaEtiquetado;

    // constructor
    public Lote(String codigo, int cantidad){
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    // Metodos
    public void iniciarProceso() {
        this.inicioProceso = LocalDateTime.now();
    }

    public void finalizarProceso() {
        this.finProceso = LocalDateTime.now();
    }

    public void cambiarPrioridad(PrioridadLote prioridadLote){
        this.prioridadLote = prioridadLote;

    }

    public boolean esUrgente() {
        return PrioridadLote.URGENTE.equals(this.prioridadLote);
    }

    // Diferencia de tiempo fin e inicio
    public Duration calcularTiempoTotal() {

        Duration duracion = Duration.between(this.inicioProceso, this.finProceso);

        return duracion;
    }

    public void setMaquina(MaquinaEtiquetado maquina) {
        this.maquinaEtiquetado = maquina;
    }


}


