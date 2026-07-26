package com.siglet.core.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/siglet_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Coral23";

    private static Connection conexion = null;

    // Constructor privado para evitar instanciar
    private ConexionBD(){}

    // Metodo para realizar la ocnexion a la BD
    public static Connection getConnexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("BD conectada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    // Metodo para cerrar conexion de BD
    public static void cerrarConnexion(){
        try {
            if (conexion != null || !conexion.isClosed()) {
                conexion.close();
                System.out.println("Cerrando conexion con la base de datos");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
