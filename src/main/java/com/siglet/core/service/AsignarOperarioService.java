package com.siglet.core.service;

import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.enumeration.EstadoMaquina;
import com.siglet.core.persistence.enumeration.EstadoOperario;
import com.siglet.core.persistence.repository.MaquinaEtiquetadoRepository;
import com.siglet.core.persistence.repository.OperarioRepository;

public class AsignarOperarioService {

    MaquinaEtiquetadoRepository mEtiqRepository;
    OperarioRepository oRepository;

    public AsignarOperarioService(MaquinaEtiquetadoRepository mEtiqRepository, OperarioRepository oRepository) {
        this.mEtiqRepository = mEtiqRepository;
        this.oRepository = oRepository;
    }

    public void ejecutarAsignarOperario(String idMaquina, String dNI) {

        // Consultar repositorio para obtener la entidad
        MaquinaEtiquetado maquina = mEtiqRepository.buscarPorId(idMaquina);
        Operario operario = oRepository.buscarPorDNI(dNI);

        // Validar si las entidades existen
        if (maquina == null || operario == null) {
            throw new RuntimeException("Operario no encontrado");
        }

        // Validar si la maquina esta libre y operario disponible
        if (!maquina.estaDisponible()) {
            throw new  RuntimeException("Maquina no encontrada");
        }

        if (!operario.estaDisponible()) {
            throw new  RuntimeException("Operario no encontrado");
        }

        // Cambio de estado de la maquina y operario
        maquina.setOperario(operario);
        operario.setEstadoOperario(EstadoOperario.TRABAJANDO);

        // guardar cambios en la BD
        mEtiqRepository.guardar(maquina);
    }
}
