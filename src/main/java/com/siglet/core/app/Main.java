package com.siglet.core.app;

import com.siglet.core.persistence.repository.LoteRepository;
import com.siglet.core.persistence.repository.MaquinaEtiquetadoRepository;
import com.siglet.core.persistence.repository.OperarioRepository;
import com.siglet.core.persistence.repository.TicketRepository;
import com.siglet.core.presentation.controller.AsignarOperarioController;
import com.siglet.core.presentation.controller.ProcesarLoteController;
import com.siglet.core.service.AsignarOperarioService;
import com.siglet.core.service.ProcesarLoteService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. Instanciar todos los repositorios
        MaquinaEtiquetadoRepository maquinaRepo = new MaquinaEtiquetadoRepository();
        LoteRepository loteRepo = new LoteRepository();
        TicketRepository ticketRepo = new TicketRepository();
        OperarioRepository operarioRepo = new OperarioRepository();

        // 2. Instanciar los servicios inyectando sus repositorios
        ProcesarLoteService loteService = new ProcesarLoteService(maquinaRepo, loteRepo, ticketRepo);
        AsignarOperarioService operarioService = new AsignarOperarioService(operarioRepo);

        // 3. Crear el servidor HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // 4. Conectar las rutas (URLs) con su respectivo controlador
        server.createContext("/api/lote", new ProcesarLoteController(loteService));
        server.createContext("/api/operario", new AsignarOperarioController(operarioService));

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor web iniciado en http://localhost:8080");
    }

}
