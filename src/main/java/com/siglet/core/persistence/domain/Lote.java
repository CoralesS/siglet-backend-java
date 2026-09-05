package com.siglet.core.persistence.domain;

import com.siglet.core.persistence.enumeration.EstadoOperario;
import com.siglet.core.persistence.enumeration.PrioridadLote;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Lote {
    private int codigo;
    private int cantidad;
    private LocalDateTime inicioProceso;
    private LocalDateTime finProceso;
    private PrioridadLote prioridadLote;
    private MaquinaEtiquetado maquinaEtiquetado;

    // constructor
    public Lote(int cantidad){
        this.cantidad = cantidad;
        this.prioridadLote = PrioridadLote.NORMAL;
    }
    public Lote(){}

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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public MaquinaEtiquetado getMaquinaEtiquetado() {
        return maquinaEtiquetado;
    }

    public void setMaquinaEtiquetado(MaquinaEtiquetado maquinaEtiquetado) {
        this.maquinaEtiquetado = maquinaEtiquetado;
    }

    public PrioridadLote getPrioridadLote() {
        return prioridadLote;
    }

    public void setPrioridadLote(PrioridadLote prioridadLote) {
        this.prioridadLote = prioridadLote;
    }

    public LocalDateTime getFinProceso() {
        return finProceso;
    }

    public void setFinProceso(LocalDateTime finProceso) {
        this.finProceso = finProceso;
    }

    public LocalDateTime getInicioProceso() {
        return inicioProceso;
    }

    public void setInicioProceso(LocalDateTime inicioProceso) {
        this.inicioProceso = inicioProceso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}


