package com.siglet.core.app;

import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.enumeration.EstadoOperario;

public class Main {
    public static void main(String[] args) {
        System.out.println("SIGLET ejecutandose");

        Operario operario = new Operario("73493012","Martin","Gaspar");

        System.out.println("OPERARIO EJECUTADO su estado es: " + operario.estaDisponible());

        operario.cambiarEstado(EstadoOperario.TRABAJANDO);

        System.out.println("OPERARIO EJECUTADO su estado cambio es es: " + operario.estaDisponible());


    }
}
