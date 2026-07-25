package com.siglet.core.persistence.domain;

import java.util.List;

public class Ticket {
    private int codigo;
    private Lote lote;
    private Operario operario;
    private  MaquinaEtiquetado maquinaEtiquetado;

    // constructor
    public Ticket(Lote lote, Operario operario,  MaquinaEtiquetado maquinaEtiquetado) {
        this.lote = lote;
        this.operario = operario;
        this.maquinaEtiquetado = maquinaEtiquetado;
    }

    // metodos
    public String getCodigo() {
        return "A-" + String.format("%05d", this.codigo);
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public MaquinaEtiquetado getMaquinaEtiquetado() {
        return maquinaEtiquetado;
    }

    public void setMaquinaEtiquetado(MaquinaEtiquetado maquinaEtiquetado) {
        this.maquinaEtiquetado = maquinaEtiquetado;
    }
}

