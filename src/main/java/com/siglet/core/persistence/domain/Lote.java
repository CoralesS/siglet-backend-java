package com.siglet.core.persistence.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Lote {
    private String codigo;
    private int cantidad;
    private LocalDateTime inicioProceso;
    private LocalDateTime finProceso;

    // constructor


    // Metodos
    public void iniciarProceso() {
        this.inicioProceso = LocalDateTime.now();
    }

    public void finalizarProceso() {

    }

    public void elevarPrioridadUrgente(){

    }

    public void elevarPrioridadNormal(){

    }

    public boolean esUrgente() {
        return true;
    }

    public LocalTime calcularTiempoTotal() {
        return LocalTime.now();
    }

    public void setMaquina(MaquinaEtiquetado maquina) {
    }


}


