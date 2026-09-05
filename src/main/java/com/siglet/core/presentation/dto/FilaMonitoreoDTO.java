package com.siglet.core.presentation.dto;

public class FilaMonitoreoDTO {

    private String codigoLote;
    private boolean esUrgente;
    private String nombreMaquina;
    private String nombreOperario;

    public FilaMonitoreoDTO(String codigoLote, boolean esUrgente, String nombreMaquina, String nombreOperario) {
        this.codigoLote = codigoLote;
        this.esUrgente = esUrgente;
        this.nombreMaquina = nombreMaquina;
        this.nombreOperario = nombreOperario;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public String getNombreOperario() {
        return nombreOperario;
    }

    public void setNombreOperario(String nombreOperario) {
        this.nombreOperario = nombreOperario;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public boolean isEsUrgente() {
        return esUrgente;
    }

    public void setEsUrgente(boolean esUrgente) {
        this.esUrgente = esUrgente;
    }
}
