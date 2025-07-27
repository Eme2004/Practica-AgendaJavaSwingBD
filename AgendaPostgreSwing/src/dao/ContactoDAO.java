/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Contacto;
/**
 *
 * @author Emesis
 */
// Clase DAO (Data Access Object) para manejar operaciones CRUD de la tabla contacto
public class ContactoDAO {

    // Atributos de clase: conexión, statement y resultados
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 🔌 Método para conectar a la base de datos
    public Connection conectar() {
        try {
            // Carga explícita del driver de PostgreSQL (opcional en JDBC 4+)
            Class.forName("org.postgresql.Driver");

            // Establece la conexión a la base de datos
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/miAgenda",
                "postgres",
                "eme12"
            );
        } catch (Exception e) {
            // Imprime el error si ocurre alguna excepción
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conn; // Devuelve la conexión (puede ser null si falló)
    }

    // ❌ Método para cerrar todos los recursos abiertos (resultset, statement, conexión)
    public void cerrarConexion() {
        try {
            if (rs != null) rs.close();      // Cierra el resultado
            if (ps != null) ps.close();      // Cierra el PreparedStatement
            if (conn != null) conn.close();  // Cierra la conexión
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    // 📥 Método para insertar un nuevo contacto en la base de datos
    public boolean insertar(Contacto c) {
        // SQL con placeholders para evitar inyección SQL
        String sql = "INSERT INTO contacto(nombre, telefono, email, direccion) VALUES (?, ?, ?, ?)";

        try {
            // Se conecta a la base y prepara la consulta
            conn = conectar();
            ps = conn.prepareStatement(sql);

            // Se establecen los valores del objeto Contacto
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());

            // Ejecuta la inserción
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            // En caso de error, se imprime y retorna false
            System.out.println("Error al insertar: " + e.getMessage());
            return false;

        } finally {
            // Se cierran los recursos siempre, haya o no error
            cerrarConexion();
        }
    }

    // 📋 Método para obtener todos los contactos en forma de lista
    public List<Contacto> listar() {
        List<Contacto> lista = new ArrayList<>();
        String sql = "SELECT * FROM contacto";

        try {
            // Se conecta y ejecuta el SELECT
            conn = conectar();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Se recorre el resultado y se crean objetos Contacto
            while (rs.next()) {
                Contacto c = new Contacto();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email")); // ⚠ Asegúrate de que esté en minúsculas en la base
                c.setDireccion(rs.getString("direccion"));

                // Se agrega a la lista
                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        } finally {
            // Se cierra todo
            cerrarConexion();
        }

        return lista;
    }
}