/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USER
 */
// Clase encargada de establecer la conexión con la base de datos PostgreSQL
public class ConexionBD {

    // Método estático que intenta establecer y devolver una conexión a la base de datos
    public static Connection conectar() {
        Connection conn = null; // Variable para almacenar la conexión

        try {
            // Datos de conexión a la base de datos
            String url = "jdbc:postgresql://localhost:5432/miAgenda"; // URL del servidor PostgreSQL con base de datos "miAgenda"
            String usuario = "postgres";       // Usuario de la base de datos
            String contraseña = "eme12";       // Contraseña del usuario

            // Se intenta establecer la conexión usando DriverManager
            conn = DriverManager.getConnection(url, usuario, contraseña);

        } catch (SQLException e) {
            // Si ocurre un error al conectar, se imprime el mensaje
            System.out.println("⚠ Error al conectar: " + e.getMessage());
        }

        // Se devuelve la conexión (puede ser null si falló)
        return conn;
    }
}
