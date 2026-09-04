package com.siglet.core.persistence.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Ticket {
    private int codigo;
    private Lote lote;
    private Operario operario;
    private  MaquinaEtiquetado maquinaEtiquetado;
    private LocalDateTime fechaEmison;

    // constructor
    public Ticket(Lote lote, Operario operario,  MaquinaEtiquetado maquinaEtiquetado) {
        this.lote = lote;
        this.operario = operario;
        this.maquinaEtiquetado = maquinaEtiquetado;
        this.fechaEmison = LocalDateTime.now();
    }

    public Ticket() {
    }

    // metodos
    public int getCodigo() {
        return this.codigo;
    }

    public Lote getLote() {
        return lote;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Operario getOperario() {
        return operario;
    }

    public MaquinaEtiquetado getMaquinaEtiquetado() {
        return maquinaEtiquetado;
    }

    public LocalDateTime getFechaEmison() {
        return fechaEmison;
    }

    public void setFechaEmison(LocalDateTime fechaEmison) {
        this.fechaEmison = fechaEmison;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public void setMaquinaEtiquetado(MaquinaEtiquetado maquinaEtiquetado) {
        this.maquinaEtiquetado = maquinaEtiquetado;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
}


