package com.siglet.core.persistence.repository;

import com.siglet.core.persistence.config.ConexionBD;
import com.siglet.core.persistence.domain.Lote;
import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.enumeration.EstadoMaquina;
import com.siglet.core.persistence.enumeration.PrioridadLote;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoteRepository {

    Connection conexion = ConexionBD.getConnexion();

    public void guardar(Lote lote){
        String sql = "INSERT INTO lote(cantidad, inicio_proceso, fin_proceso, prioridad, id_maquina)" +
                     "VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            PrioridadLote prioridadAsignado = lote.getPrioridadLote();
            MaquinaEtiquetado maquinaAsignado = lote.getMaquinaEtiquetado();

            stmt.setInt(1, lote.getCantidad());
            stmt.setObject(2, lote.getInicioProceso());
            stmt.setObject(3, lote.getFinProceso());
            stmt.setString(4, prioridadAsignado.name());
            stmt.setInt(5, maquinaAsignado.getIdMaquina());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    int id = rs.getInt(1);
                    lote.setCodigo(id);
                }
            }
        } catch (Exception e){
            throw new RuntimeException("Error al guardar el lote", e);
        }
    }

    public Lote buscarPorCodigo(int codigo){

        String sql = "SELECT " +
                "l.id_lote, " +
                "l.cantidad, " +
                "l.inicio_proceso, " +
                "l.fin_proceso, " +
                "l.prioridad, " +
                "me.id_maquina " +
                "FROM lote l " +
                "INNER JOIN maquina_etiquetado me ON me.id_maquina = l.id_maquina " +
                "WHERE l.id_Lote = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)){

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()){

                if (rs.next()) {
                    MaquinaEtiquetado maquinaEncontrado = new MaquinaEtiquetado();
                    maquinaEncontrado.setIdMaquina(rs.getInt("id_maquina"));

                    Lote loteEntontrado = new Lote();
                    loteEntontrado.setCodigo(rs.getInt("id_lote"));
                    loteEntontrado.setCantidad(rs.getInt("cantidad"));
                    loteEntontrado.setInicioProceso(rs.getObject("inicio_proceso", java.time.LocalDateTime.class));
                    loteEntontrado.setFinProceso(rs.getObject("fin_proceso", java.time.LocalDateTime.class));
                    loteEntontrado.setMaquinaEtiquetado(maquinaEncontrado);

                    return loteEntontrado;
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el lote por codigo", e);
        }
        return null;
    }

    public List<Lote> buscarPorPrioridad(PrioridadLote lotePrioridad){

        List<Lote> listaLotes = new ArrayList<>();

        String sql = "SELECT " +
                "l.id_lote, " +
                "l.cantidad, " +
                "l.inicio_proceso, " +
                "l.fin_proceso, " +
                "l.prioridad, " +
                "me.id_maquina " +
                "FROM lote l " +
                "INNER JOIN maquina_etiquetado me ON me.id_maquina = l.id_maquina " +
                "WHERE l.prioridad = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)){

            stmt.setString(1, lotePrioridad.name());

            try (ResultSet rs = stmt.executeQuery()){

                while (rs.next()) {
                    MaquinaEtiquetado maquinaEncontrado = new MaquinaEtiquetado();
                    maquinaEncontrado.setIdMaquina(rs.getInt("id_maquina"));

                    Lote loteEntontrado = new Lote();
                    loteEntontrado.setCodigo(rs.getInt("id_lote"));
                    loteEntontrado.setCantidad(rs.getInt("cantidad"));
                    loteEntontrado.setInicioProceso(rs.getObject("inicio_proceso", java.time.LocalDateTime.class));
                    loteEntontrado.setFinProceso(rs.getObject("fin_proceso", java.time.LocalDateTime.class));
                    loteEntontrado.setMaquinaEtiquetado(maquinaEncontrado);

                    listaLotes.add(loteEntontrado);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el lote por prioridad", e);
        }

        return listaLotes;
    }

    public List<Lote> buscarTodo(){

        List<Lote> listaLotesTotal = new ArrayList<>();

        String sql = "SELECT " +
                "l.id_lote, " +
                "l.cantidad, " +
                "l.inicio_proceso, " +
                "l.fin_proceso, " +
                "l.prioridad, " +
                "me.id_maquina " +
                "FROM lote l " +
                "INNER JOIN maquina_etiquetado me ON me.id_maquina = l.id_maquina";

        try(PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                MaquinaEtiquetado maquinaEncontrado = new MaquinaEtiquetado();
                maquinaEncontrado.setIdMaquina(rs.getInt("id_maquina"));

                Lote loteEntontrado = new Lote();
                loteEntontrado.setCodigo(rs.getInt("id_lote"));
                loteEntontrado.setCantidad(rs.getInt("cantidad"));
                loteEntontrado.setInicioProceso(rs.getObject("inicio_proceso", java.time.LocalDateTime.class));
                loteEntontrado.setFinProceso(rs.getObject("fin_proceso", java.time.LocalDateTime.class));
                loteEntontrado.setMaquinaEtiquetado(maquinaEncontrado);

                listaLotesTotal.add(loteEntontrado);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el lote por prioridad", e);
        }

        return listaLotesTotal;
    }
}
