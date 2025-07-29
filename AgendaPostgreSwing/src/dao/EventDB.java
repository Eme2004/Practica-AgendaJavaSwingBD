/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Event;


/**
 *
 * @author Emesis
 */

/**
 * La clase EventDB encapsula operaciones de acceso a datos para la entidad 'evento'.
 * Utiliza una conexión JDBC para interactuar con una base de datos PostgreSQL.
 * Proporciona métodos para insertar, consultar, actualizar y eliminar eventos.
 * 
 * Cada evento está asociado a un contacto (por medio de contacto_id),
 * y contiene información como fecha, hora, descripción y ubicación.
 */
public class EventDB {

    /**
     * Objeto Connection que representa la conexión activa con la base de datos.
     * Se espera que esta conexión esté abierta y gestionada externamente.
     */
    private Connection conexion;

    /**
     * Constructor que inicializa la instancia EventDB con una conexión específica.
     * @param conexion Conexión JDBC válida y activa a la base de datos.
     */
    public EventDB(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * Inserta un nuevo registro de evento en la tabla 'evento'.
     * @param evento Objeto Event con todos los atributos necesarios.
     * @return true si la operación fue exitosa; false si ocurre una excepción.
     * 
     * Notas:
     * - Se convierte LocalDate y LocalTime a tipos compatibles con SQL.
     * - Se usa PreparedStatement para evitar inyecciones SQL.
     */
    public boolean insertarEvento(Event evento) {
        String sql = "INSERT INTO evento (contacto_id, fecha, hora, descripcion, ubicacion) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, evento.getContactoId());
            ps.setDate(2, Date.valueOf(evento.getFecha()));
            ps.setTime(3, Time.valueOf(evento.getHora()));
            ps.setString(4, evento.getDescripcion());
            ps.setString(5, evento.getUbicacion());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar evento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera todos los eventos almacenados en la base de datos.
     * @return Lista de objetos Event con datos completos de cada evento.
     * 
     * Detalles:
     * - Realiza un SELECT sin condiciones.
     * - Convierte fechas y horas SQL a tipos LocalDate y LocalTime.
     * - Los datos se transforman a objetos Event en tiempo de ejecución.
     */
    public List<Event> obtenerTodosLosEventos() {
        List<Event> eventos = new ArrayList<>();
        String sql = "SELECT * FROM evento";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event ev = new Event(
                    rs.getInt("id"),
                    rs.getInt("contacto_id"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("descripcion"),
                    rs.getString("ubicacion")
                );
                eventos.add(ev);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener eventos: " + e.getMessage());
        }
        return eventos;
    }

    /**
     * Elimina un evento existente usando su identificador único.
     * @param id ID del evento a eliminar.
     * @return true si se eliminó al menos un registro; false si no o si hubo error.
     * 
     * Consideraciones:
     * - Si el ID no existe, no se elimina nada.
     * - Se usa PreparedStatement para mayor seguridad.
     */
    public boolean eliminarEvento(int id) {
        String sql = "DELETE FROM evento WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar evento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los campos de un evento existente según su ID.
     * @param evento Objeto Event con datos nuevos (incluyendo ID del evento).
     * @return true si se modificó algún registro; false si no o si hubo error.
     * 
     * Aspectos técnicos:
     * - Todos los campos son actualizables.
     * - El ID se usa como identificador primario para aplicar cambios.
     */
    public boolean actualizarEvento(Event evento) {
        String sql = "UPDATE evento SET contacto_id = ?, fecha = ?, hora = ?, descripcion = ?, ubicacion = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, evento.getContactoId());
            ps.setDate(2, Date.valueOf(evento.getFecha()));
            ps.setTime(3, Time.valueOf(evento.getHora()));
            ps.setString(4, evento.getDescripcion());
            ps.setString(5, evento.getUbicacion());
            ps.setInt(6, evento.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar evento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Recupera un evento único a partir de su ID.
     * @param id Identificador del evento.
     * @return Objeto Event si se encuentra; null si no existe o ocurre error.
     * 
     * Observaciones:
     * - Útil para mostrar o editar eventos específicos.
     * - Si el ID no existe, devuelve null sin excepciones.
     */
    public Event obtenerEventoPorId(int id) {
        String sql = "SELECT * FROM evento WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Event(
                    rs.getInt("id"),
                    rs.getInt("contacto_id"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("descripcion"),
                    rs.getString("ubicacion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener evento por ID: " + e.getMessage());
        }
        return null;
    }
}