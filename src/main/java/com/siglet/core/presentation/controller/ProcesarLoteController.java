package com.siglet.core.presentation.controller;

import com.siglet.core.presentation.dto.FilaMonitoreoDTO;
import com.siglet.core.service.ProcesarLoteService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class ProcesarLoteController implements HttpHandler {
    private ProcesarLoteService service;

    public ProcesarLoteController(ProcesarLoteService service) {
        this.service = service;
    }

    // 1. EL RECEPCIONISTA (Obligatorio por Java)
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Configuramos CORS (permisos)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String ruta = exchange.getRequestURI().getPath();
        String metodoHttp = exchange.getRequestMethod();

        try {
            // El "Semáforo": Deriva la petición según la URL
            if (metodoHttp.equals("GET") && ruta.equals("/api/lote/monitoreo")) {
                this.obtenerDatosMonitoreo(exchange);

            } else if (metodoHttp.equals("POST") && ruta.equals("/api/lote/iniciar")) {
                this.iniciarProceso(exchange);

            } else if (metodoHttp.equals("POST") && ruta.equals("/api/lote/finalizar")) {
                this.finalizarProceso(exchange);

            } else {
                // Si la URL no existe, enviamos error 404
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
            }
        } catch (Exception e) {
            // Manejo global de errores
            exchange.sendResponseHeaders(500, -1);
            exchange.close();
        }
    }

    // 2. TUS MÉTODOS DEL DIAGRAMA UML (Lógica delegada)

    private void obtenerDatosMonitoreo(HttpExchange exchange) throws IOException {
        try {
            // 1. Obtener los datos del servicio
            List<FilaMonitoreoDTO> datos = service.obtenerDatosMonitoreo();

            // 2. Construir el texto JSON manualmente
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < datos.size(); i++) {
                FilaMonitoreoDTO dto = datos.get(i);

                json.append("{");
                json.append("\"codigoLote\":\"").append(dto.getCodigoLote()).append("\",");
                json.append("\"esUrgente\":").append(dto.isEsUrgente()).append(",");
                json.append("\"nombreMaquina\":\"").append(dto.getNombreMaquina()).append("\",");
                json.append("\"nombreOperario\":\"").append(dto.getNombreOperario()).append("\"");
                json.append("}");

                if (i < datos.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            // 3. Preparar los bytes para el envío
            String respuestaJson = json.toString();
            byte[] bytes = respuestaJson.getBytes(java.nio.charset.StandardCharsets.UTF_8);

            // 4. Configurar cabeceras de éxito (200 OK) y enviar
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);

            java.io.OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            String errorJson = "{\"error\":\"Error interno del servidor\"}";
            exchange.sendResponseHeaders(500, errorJson.getBytes().length);
            java.io.OutputStream os = exchange.getResponseBody();
            os.write(errorJson.getBytes());
            os.close();
        }
    }

    private void iniciarProceso(HttpExchange exchange) throws IOException {
        // 1. Leer el código del lote que envía el HTML en el POST
        // 2. Llamar a: service.iniciarProcesoLote(codigo);
        // 3. Enviar respuesta de éxito al navegador
    }

    private void finalizarProceso(HttpExchange exchange) throws IOException {
        // 1. Leer el código del lote
        // 2. Llamar a: service.finalizarProcesoLote(codigo);
        // 3. Enviar respuesta de éxito al navegador
    }
}
