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

    public void setMaquina(MaquinaEtiquetado maquina) {
        this.maquinaEtiquetado = maquina;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getInicioProceso() {
        return inicioProceso;
    }

    public LocalDateTime getFinProceso() {
        return finProceso;
    }

    public PrioridadLote getPrioridadLote() {
        return prioridadLote;
    }

    public MaquinaEtiquetado getMaquinaEtiquetado() {
        return maquinaEtiquetado;
    }

    public void setInicioProceso(LocalDateTime inicioProceso) {
        this.inicioProceso = inicioProceso;
    }

    public void setFinProceso(LocalDateTime finProceso) {
        this.finProceso = finProceso;
    }

    public void setPrioridadLote(PrioridadLote prioridadLote) {
        this.prioridadLote = prioridadLote;
    }

    public void setMaquinaEtiquetado(MaquinaEtiquetado maquinaEtiquetado) {
        this.maquinaEtiquetado = maquinaEtiquetado;
    }
}


