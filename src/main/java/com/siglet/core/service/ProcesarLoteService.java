package com.siglet.core.service;

import com.siglet.core.persistence.domain.Lote;
import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.domain.Ticket;
import com.siglet.core.persistence.enumeration.EstadoMaquina;
import com.siglet.core.persistence.repository.LoteRepository;
import com.siglet.core.persistence.repository.MaquinaEtiquetadoRepository;
import com.siglet.core.persistence.repository.TicketRepository;
import com.siglet.core.presentation.dto.FilaMonitoreoDTO;

import java.util.ArrayList;
import java.util.List;

public class ProcesarLoteService {

    MaquinaEtiquetadoRepository mEtiqRepository;
    LoteRepository loteRepository;
    TicketRepository ticketRepository;

    public ProcesarLoteService(MaquinaEtiquetadoRepository mEtiqRepository, LoteRepository loteRepository, TicketRepository ticketRepository) {
        this.mEtiqRepository = mEtiqRepository;
        this.loteRepository = loteRepository;
        this.ticketRepository = ticketRepository;
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

    public List<FilaMonitoreoDTO> obtenerDatosMonitoreo() {
        List<FilaMonitoreoDTO> listaMonitoreo = new ArrayList<>();

        // 1. Obtener todos los lotes de la base de datos (según tu diagrama)
        List<Lote> lotes = loteRepository.buscarTodo();


        // 2. Transformar cada entidad de Dominio a un DTO de Presentación
        for (Lote lote : lotes) {
            String codigoFormateado = "L-" + lote.getCodigo();
            boolean urgente = lote.esUrgente();

            // Valores por defecto si el lote aún no tiene recursos asignados
            String nomMaquina = "Sin asignar";
            String nomOperario = "Sin asignar";

            // Navegar con seguridad para evitar NullPointerException
            if (lote.getMaquinaEtiquetado() != null) {
                // 1. Obtienes el ID de la máquina que tiene el lote
                String idMaquina = String.valueOf(lote.getMaquinaEtiquetado().getIdMaquina());
                nomMaquina = "MQ-" + idMaquina;

                // 2. Buscas la máquina ACTUALIZADA directamente en su repositorio
                MaquinaEtiquetado maquinaActual = mEtiqRepository.buscarPorId(idMaquina);

                // 3. Verificas si la máquina actualizada existe y tiene un operario
                if (maquinaActual != null && maquinaActual.getOperario() != null) {
                    nomOperario = maquinaActual.getOperario().getNombre();
                }
            }

            // 3. Empaquetar y agregar a la lista
            FilaMonitoreoDTO dto = new FilaMonitoreoDTO(codigoFormateado, urgente, nomMaquina, nomOperario);
            listaMonitoreo.add(dto);
        }

        return listaMonitoreo;
    }

}
