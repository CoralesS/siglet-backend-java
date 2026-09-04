package com.siglet.core.service;

import com.siglet.core.persistence.domain.Lote;
import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.domain.Ticket;
import com.siglet.core.persistence.enumeration.EstadoMaquina;
import com.siglet.core.persistence.repository.LoteRepository;
import com.siglet.core.persistence.repository.MaquinaEtiquetadoRepository;
import com.siglet.core.persistence.repository.TicketRepository;

public class ProcesarLoteService {

    MaquinaEtiquetadoRepository mEtiqRepository;
    LoteRepository loteRepository;
    TicketRepository ticketRepository;

    public ProcesarLoteService(MaquinaEtiquetadoRepository mEtiqRepository, LoteRepository loteRepository) {
        this.mEtiqRepository = mEtiqRepository;
        this.loteRepository = loteRepository;
    }

    public void iniciarProcesoLote(int codigoLote){
        Lote lote = loteRepository.buscarPorCodigo(codigoLote);

        if(lote == null) {
            throw new RuntimeException("Lote no encontrado " + codigoLote);
        }

        lote.iniciarProceso();
        loteRepository.guardar(lote);
    }

    public void finalizarProcesoLote(int codigoLote){
        Lote lote = loteRepository.buscarPorCodigo(codigoLote);
        if(lote == null) {
            throw new RuntimeException("Lote no encontrado " + codigoLote);
        }

        lote.finalizarProceso();

        MaquinaEtiquetado maquinaUsada = lote.getMaquinaEtiquetado();
        Operario operarioInvolucrado = maquinaUsada.getOperario();

        Ticket nuevoTicket = new Ticket(lote, operarioInvolucrado, maquinaUsada);
        loteRepository.guardar(lote);
        ticketRepository.guardar(nuevoTicket);

    }
}
