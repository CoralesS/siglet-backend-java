package com.siglet.core.persistence.repository;

import com.siglet.core.persistence.config.ConexionBD;
import com.siglet.core.persistence.domain.MaquinaEtiquetado;
import com.siglet.core.persistence.domain.Operario;
import com.siglet.core.persistence.enumeration.EstadoOperario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperarioRepository {
    Connection conexion = ConexionBD.getConnexion();

    public void guardar(Operario operario){


        String sql = "INSERT INTO operario(ope_dni, nombre, apellido, estado) " +
                "VALUES (?, ?, ?, ?)";


        EstadoOperario estadoAsignado = operario.getEstadoOperario();

        if(conexion == null){
            System.out.println("Conexion a BD fallida no es posible guardar");
            return;
        }

        try(PreparedStatement stmt = conexion.prepareStatement(sql)){

            //Asignacion de valores
            stmt.setString(1,operario.getdNI());
            stmt.setString(2,operario.getNombre());
            stmt.setString(3,operario.getApellido());
            stmt.setString(4,estadoAsignado.name());

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException("Error al guardar el operario",e);
        }

    }

    public Operario buscarPorDNI(String id){

        String sql = "SELECT * FROM operario WHERE ope_dni = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1,id);

            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Operario operario = new Operario();

                    operario.setdNI(rs.getString("ope_dni"));
                    operario.setNombre(rs.getString("nombre"));
                    operario.setApellido(rs.getString("apellido"));
                    operario.setEstadoOperario(EstadoOperario.valueOf(rs.getString("estado")));
                    return operario;
                }
            }

        } catch (SQLException e){
            throw new RuntimeException("Error al buscar el operario",e);
        }
        return null;
    }

    public List<Operario> buscarPorEstado(EstadoOperario estado){
        List<Operario> listaOperario = new ArrayList<>();
        String sql = "SELECT * FROM operario WHERE estado = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1,estado.name());

            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Operario operario = new Operario();
                    operario.setdNI(rs.getString("ope_dni"));
                    operario.setNombre(rs.getString("nombre"));
                    operario.setApellido(rs.getString("apellido"));
                    operario.setEstadoOperario(EstadoOperario.valueOf(rs.getString("estado")));
                    listaOperario.add(operario);
                }
            }

        }catch (SQLException e){
            throw new RuntimeException("Error al buscar el operario",e);
        }

        return listaOperario;
    }
}
