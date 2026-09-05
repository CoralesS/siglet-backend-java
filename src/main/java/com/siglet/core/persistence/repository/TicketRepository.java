package com.siglet.core.persistence.repository;

import com.siglet.core.persistence.config.ConexionBD;
import com.siglet.core.persistence.domain.Lote;
import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.domain.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    Connection conexion = ConexionBD.getConnexion();

    public void guardar(Ticket ticket){
        String sql = "INSERT INTO ticket (id_lote, id_maquina, fecha_emision, ope_dni) VALUES (?,?,?,?)";

        Lote loteAsignado = ticket.getLote();
        MaquinaEtiquetado maquinaAsignada = ticket.getMaquinaEtiquetado();
        Operario operarioAsignado = ticket.getOperario();

        //caso si la BD no llega a conectarse
        if (conexion == null) {
            System.out.println("Conexion a BD fallida no es posible guardar");
            return;
        }

        //PreparedStatement
        try(PreparedStatement stmt= conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            //Asignacion de valores
            stmt.setInt(1,loteAsignado.getCodigo());
            stmt.setInt(2,maquinaAsignada.getIdMaquina());
            stmt.setObject(3,ticket.getFechaEmison());
            stmt.setString(4,operarioAsignado.getdNI());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    int id = rs.getInt(1);
                    ticket.setCodigo(id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el ticket",e);
        }
    }

    public Ticket buscarPorLoteCodigo(int codigolote){

        String sql = "SELECT t.id_ticket, t.fecha_emision, " +
                "l.id_lote, l.cantidad, " +
                "m.id_maquina, m.capacidad_maxima, " +
                "o.nombre, o.apellido " +
                "FROM ticket t " +
                "INNER JOIN lote l ON t.id_lote = l.id_lote " +
                "INNER JOIN maquina_etiquetado m ON t.id_maquina = m.id_maquina " +
                "INNER JOIN operario o ON t.ope_dni = o.ope_dni " +
                "WHERE t.id_lote = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1,codigolote);

            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) { // el .next() mueve el puntero de la tabla devuelta a la primera fila

                   // llena datos desde las columnas de operario
                    Operario operarioEncontrado = new Operario();
                    operarioEncontrado.setNombre(rs.getString("nombre"));
                    operarioEncontrado.setApellido(rs.getString("apellido"));


                    // llena datos desde las columnas de Maquina
                    MaquinaEtiquetado maquinaEncontrada = new MaquinaEtiquetado();
                    maquinaEncontrada.setIdMaquina(rs.getInt("id_maquina"));
                    maquinaEncontrada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                    maquinaEncontrada.setOperario(operarioEncontrado);

                    // llena datos desde las columnas de Lote
                    Lote loteEncontrado = new Lote();
                    loteEncontrado.setMaquinaEtiquetado(maquinaEncontrada);
                    loteEncontrado.setCodigo(rs.getInt("id_lote"));
                    loteEncontrado.setCantidad(rs.getInt("cantidad"));

                    // llena datos desde las columnas de ticket
                    Ticket ticketEncontrado = new Ticket();
                    ticketEncontrado.setCodigo(rs.getInt("id_ticket"));
                    ticketEncontrado.setFechaEmison(rs.getObject("fecha_emision", java.time.LocalDateTime.class));
                    ticketEncontrado.setMaquinaEtiquetado(maquinaEncontrada);
                    ticketEncontrado.setLote(loteEncontrado);
                    ticketEncontrado.setOperario(operarioEncontrado);

                    return ticketEncontrado;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el ticket",e);
        }
        return null;
    }

    public List<Ticket> buscarTodo(){
        List<Ticket> listaTickets = new ArrayList<>();

        String sql = "SELECT t.id_ticket, t.fecha_emision, " +
                "l.id_lote, l.cantidad, " +
                "m.id_maquina, m.capacidad_maxima, " +
                "o.nombre, o.apellido " +
                "FROM ticket t " +
                "INNER JOIN lote l ON t.id_lote = l.id_lote " +
                "INNER JOIN maquina_etiquetado m ON t.id_maquina = m.id_maquina " +
                "INNER JOIN operario o ON t.ope_dni = o.ope_dni";

        try(PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs= stmt.executeQuery()){

            while (rs.next()){
                // Llena datos desde las columnas de operario
                Operario operarioEncontrado = new Operario();
                operarioEncontrado.setNombre(rs.getString("nombre"));
                operarioEncontrado.setApellido(rs.getString("apellido"));

                // Llena datos desde las columnas de Maquina
                MaquinaEtiquetado maquinaEncontrada = new MaquinaEtiquetado();
                maquinaEncontrada.setIdMaquina(rs.getInt("id_maquina"));
                maquinaEncontrada.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                maquinaEncontrada.setOperario(operarioEncontrado);

                // Llena datos desde las columnas de Lote
                Lote loteEncontrado = new Lote();
                loteEncontrado.setMaquinaEtiquetado(maquinaEncontrada);
                loteEncontrado.setCodigo(rs.getInt("id_lote"));
                loteEncontrado.setCantidad(rs.getInt("cantidad"));

                // Llena datos desde las columnas de ticket
                Ticket ticketEncontrado = new Ticket();
                ticketEncontrado.setCodigo(rs.getInt("id_ticket"));
                ticketEncontrado.setFechaEmison(rs.getObject("fecha_emision", java.time.LocalDateTime.class));
                ticketEncontrado.setMaquinaEtiquetado(maquinaEncontrada);
                ticketEncontrado.setLote(loteEncontrado);
                ticketEncontrado.setOperario(operarioEncontrado);

                //Agregar el ticket recién armado a nuestra lista
                listaTickets.add(ticketEncontrado);
            }

        }catch (SQLException e) {
            throw new RuntimeException("Error al buscar los tickets",e);
        }

        return listaTickets;
    }
}
