package com.siglet.core.presentation.controller;

import com.siglet.core.presentation.dto.OperarioDisponibleDTO;
import com.siglet.core.service.AsignarOperarioService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AsignarOperarioController implements HttpHandler{


    private AsignarOperarioService service;

    public AsignarOperarioController(AsignarOperarioService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String ruta = exchange.getRequestURI().getPath();
        String metodo = exchange.getRequestMethod();

        try {
            if (metodo.equals("GET") && ruta.equals("/api/operario/disponibles")) {
                this.obtenerDisponibles(exchange);
            } else {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }
    }

    private void obtenerDisponibles(HttpExchange exchange) throws IOException {
        List<OperarioDisponibleDTO> disponibles = service.obtenerOperariosDisponibles();

        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < disponibles.size(); i++) {
            OperarioDisponibleDTO dto = disponibles.get(i);

            json.append("{");
            // Las llaves coinciden con los atributos reales de tu clase DTO
            json.append("\"idOperario\":").append(dto.getIdOperario()).append(",");
            json.append("\"nombreOperario\":\"").append(dto.getNombreOperario()).append("\"");
            json.append("}");

            if (i < disponibles.size() - 1) json.append(",");
        }
        json.append("]");

        byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
