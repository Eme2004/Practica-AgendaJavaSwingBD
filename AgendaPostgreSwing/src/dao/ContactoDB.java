/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Contacto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emesis
 */
// Clase encargada de manejar operaciones con la tabla "contacto" en la base de datos.
public class ContactoDB {

    //  Método para guardar un nuevo contacto en la base de datos
    public static boolean guardar(Contacto c) {
        // Consulta SQL con placeholders para prevenir inyección SQL
        String sql = "INSERT INTO contacto (nombres, apellidos, telefono, email, direccion, etiquetas) VALUES (?, ?, ?, ?, ?, ?)";

        // Uso de try-with-resources: se asegura de cerrar automáticamente la conexión y el PreparedStatement
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignación de valores del objeto Contacto a los parámetros de la consulta
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getApellidos());
            stmt.setString(3, c.getTelefono());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getDireccion());
            
            // Ejecuta la inserción y devuelve true si se insertó al menos una fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // En caso de error, se registra en la tabla log_error
            registrarError(e.getMessage(), "guardar");
            return false;
        }
    }

    //  Método para obtener todos los contactos desde la base de datos
    public static List<Contacto> listar() {
        List<Contacto> lista = new ArrayList<>();
        String sql = "SELECT * FROM contacto";

        // try-with-resources para conexión, Statement y ResultSet
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Itera sobre los resultados y construye objetos Contacto
            while (rs.next()) {
                Contacto c = new Contacto(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("direccion")
                );
                lista.add(c); // Agrega el contacto a la lista
            }

        } catch (SQLException e) {
            // Registra cualquier error en el método listar
            registrarError(e.getMessage(), "listar");
        }

        return lista; // Retorna la lista de contactos (puede estar vacía)
    }

    //  Método para registrar errores en la tabla log_error
    private static void registrarError(String error, String metodo) {
        String sql = "INSERT INTO log_error (error, metodo) VALUES (?, ?)";

        // try-with-resources para registrar el error en la base
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, error);   // Mensaje de error
            stmt.setString(2, metodo);  // Método donde ocurrió el error
            stmt.executeUpdate();

        } catch (SQLException ex) {
            // Si ocurre otro error al registrar el log, se muestra en consola
            System.err.println("Error al registrar log: " + ex.getMessage());
        }
    }
}