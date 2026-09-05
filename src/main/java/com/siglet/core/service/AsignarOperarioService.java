package com.siglet.core.service;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.enumeration.EstadoOperario;
import com.siglet.core.persistence.repository.OperarioRepository;
import com.siglet.core.presentation.dto.OperarioDisponibleDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsignarOperarioService{

    private OperarioRepository operarioRepository;

    public AsignarOperarioService(OperarioRepository operarioRepository) {
        this.operarioRepository = operarioRepository;
    }

    public List<OperarioDisponibleDTO> obtenerOperariosDisponibles() {
        List<OperarioDisponibleDTO> listaDtos = new ArrayList<>();

        // Se asume que tu enumerador tiene el valor DISPONIBLE
        List<Operario> operariosLibres = operarioRepository.buscarPorEstado(EstadoOperario.DISPONIBLE);

        for (Operario operario : operariosLibres) {
            OperarioDisponibleDTO dto = new OperarioDisponibleDTO();

            // Convierte el String de la BD al int que exige tu DTO
            dto.setIdOperario(Integer.parseInt(operario.getdNI()));

            // Une nombre y apellido para entregarlo listo a la vista
            dto.setNombreOperario(operario.getNombre() + " " + operario.getApellido());

            listaDtos.add(dto);
        }

        return listaDtos;
    }
}
