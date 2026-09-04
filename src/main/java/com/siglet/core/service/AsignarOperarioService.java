package com.siglet.core.service;

import com.siglet.core.persistence.domain.MaquinaEtiquetado;
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



    }
}
