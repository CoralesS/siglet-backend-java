package com.siglet.core.persistence.repository;

import com.siglet.core.persistence.config.ConexionBD;
import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.enumeration.EstadoMaquina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaquinaEtiquetadoRepository {

    Connection conexion = ConexionBD.getConnexion();

    public void guardar(MaquinaEtiquetado maquina) {
        String sql = "INSERT INTO maquina_etiquetado(capacidad_maxima,estado,ope_dni) VALUES (?,?,?)";

        try(PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            EstadoMaquina estadoAsignado = maquina.getEstadoMaquina();
            Operario operarioAsignado = maquina.getOperario();

            stmt.setInt(1, maquina.getCapacidadMaxima());
            stmt.setString(2, estadoAsignado.name());
            stmt.setString(3, operarioAsignado.getdNI());
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {
                    int id = rs.getInt(1);
                    maquina.setIdMaquina(id);
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("No se pude guardar la maquina", e);
        }
    }

    public MaquinaEtiquetado buscarPorId(String idMaquina) {

        String sql = "SELECT " +
                "mq.id_maquina, " +
                "mq.capacidad_maxima, " +
                "mq.estado, " +
                "o.ope_dni, " +
                "o.nombre, " +
                "o.apellido, " +
                "FROM maquina_etiquetado mq " +
                "INNER JOIN operario o ON o.ope_dni = mq.ope_dni " +
                "WHERE mq.id_maquina = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, idMaquina);
            stmt.executeQuery();

            try(ResultSet rs = stmt.getResultSet()) {
                if (rs.next()) {

                    Operario operarioEncontrado = new Operario(); // Operario de la BD
                    operarioEncontrado.setdNI(rs.getString("ope_dni"));
                    operarioEncontrado.setNombre(rs.getString("nombre"));
                    operarioEncontrado.setApellido(rs.getString("apellido"));

                    MaquinaEtiquetado maquinaEncontrado = new MaquinaEtiquetado(); // maquina debe contener al operario
                    maquinaEncontrado.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                    maquinaEncontrado.setIdMaquina(rs.getInt("id_maquina"));
                    maquinaEncontrado.setEstadoMaquina(EstadoMaquina.valueOf(rs.getString("estado")));
                    maquinaEncontrado.setOperario(operarioEncontrado);

                    return maquinaEncontrado;
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("No se pude consultar la maquina", e);
        }

        return null;
    }

    public List<MaquinaEtiquetado> buscarPorEstado(EstadoMaquina estado) {

        List<MaquinaEtiquetado> listaMaquinas = new ArrayList<>();

        String sql = "SELECT " +
                "mq.id_maquina, " +
                "mq.capacidad_maxima, " +
                "mq.estado, " +
                "o.ope_dni, " +
                "o.nombre, " +
                "o.apellido, " +
                "FROM maquina_etiquetado mq " +
                "INNER JOIN operario o ON o.ope_dni = mq.ope_dni " +
                "WHERE mq.estado = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, estado.name());
            stmt.executeQuery();

            try(ResultSet rs = stmt.getResultSet()) {
                while (rs.next()) {

                    Operario operarioEncontrado = new Operario(); // Operario de la BD
                    operarioEncontrado.setdNI(rs.getString("ope_dni"));
                    operarioEncontrado.setNombre(rs.getString("nombre"));
                    operarioEncontrado.setApellido(rs.getString("apellido"));

                    MaquinaEtiquetado maquinaEncontrado = new MaquinaEtiquetado(); // maquina debe contener al operario
                    maquinaEncontrado.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                    maquinaEncontrado.setIdMaquina(rs.getInt("id_maquina"));
                    maquinaEncontrado.setEstadoMaquina(EstadoMaquina.valueOf(rs.getString("estado")));
                    maquinaEncontrado.setOperario(operarioEncontrado);

                    listaMaquinas.add(maquinaEncontrado);
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("No se pude consultar la maquina por estado", e);
        }

        return listaMaquinas;
    }

    public List<MaquinaEtiquetado> buscarTodo(){

        List<MaquinaEtiquetado> listaTotalMaquinas = new ArrayList<>();

        String sql = "SELECT " +
                "mq.id_maquina, " +
                "mq.capacidad_maxima, " +
                "mq.estado, " +
                "o.ope_dni, " +
                "o.nombre, " +
                "o.apellido " +
                "FROM maquina_etiquetado mq " +
                "INNER JOIN operario o ON o.ope_dni = mq.ope_dni";

        try(PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Operario operarioEncontrado = new Operario(); // Operario de la BD
                operarioEncontrado.setdNI(rs.getString("ope_dni"));
                operarioEncontrado.setNombre(rs.getString("nombre"));
                operarioEncontrado.setApellido(rs.getString("apellido"));

                MaquinaEtiquetado maquinaEncontrado = new MaquinaEtiquetado(); // maquina debe contener al operario
                maquinaEncontrado.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                maquinaEncontrado.setIdMaquina(rs.getInt("id_maquina"));
                maquinaEncontrado.setEstadoMaquina(EstadoMaquina.valueOf(rs.getString("estado")));
                maquinaEncontrado.setOperario(operarioEncontrado);

                listaTotalMaquinas.add(maquinaEncontrado);
            }


        } catch (SQLException e){
            throw new RuntimeException("No se pude consultar la maquina por estado", e);
        }

        return listaTotalMaquinas;
    }
}
