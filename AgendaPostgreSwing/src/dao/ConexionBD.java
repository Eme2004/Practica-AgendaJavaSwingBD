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
 public static Connection getConexion() {
        try {
            String url = "jdbc:postgresql://localhost:5432/miAgenda";
            String usuario = "postgres";
            String contraseña = "eme12";
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            System.out.println("⚠ Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
