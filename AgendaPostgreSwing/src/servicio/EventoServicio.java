/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import dao.EventDB;
import modelo.Event;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Emesis
 */
/**
 * La clase EventoServicio encapsula la lógica de negocio para gestionar eventos.
 * 
 * Se encarga de validar datos de entrada, coordinar operaciones con la base de datos
 * y asegurar integridad usando transacciones manuales.
 * 
 * Utiliza una instancia de EventDB para las operaciones CRUD relacionadas con la entidad Event.
 */
public class EventoServicio {

    /** Conexión activa a la base de datos. Se requiere que esté abierta y administrada externamente. */
    private final Connection conexion;

    /** DAO especializado en operaciones sobre la tabla 'evento'. */
    private final EventDB eventDB;

    /**
     * Constructor que inicializa el servicio con una conexión de base de datos.
     * @param conexion Conexión JDBC válida.
     */
    public EventoServicio(Connection conexion) {
        this.conexion = conexion;
        this.eventDB = new EventDB(conexion);
    }

    /**
     * Agrega un nuevo evento al sistema con validaciones previas y manejo transaccional.
     * 
     * Reglas de validación:
     * - La descripción y ubicación no deben estar vacías.
     * - La fecha y hora deben estar definidas.
     * - El contacto asociado debe tener un ID válido (> 0).
     * 
     * @param contactoId ID del contacto asociado.
     * @param fecha Fecha del evento.
     * @param hora Hora del evento.
     * @param descripcion Descripción del evento.
     * @param ubicacion Ubicación del evento.
     * @return true si se inserta correctamente; false si hay datos inválidos o errores SQL.
     */
    public boolean agregarEvento(int contactoId, LocalDate fecha, LocalTime hora, String descripcion, String ubicacion) {
        if (descripcion == null || descripcion.isBlank()) return false;
        if (ubicacion == null || ubicacion.isBlank()) return false;
        if (fecha == null || hora == null) return false;
        if (contactoId <= 0) return false;

        Event evento = new Event(contactoId, fecha, hora, descripcion, ubicacion);

        try {
            conexion.setAutoCommit(false);
            boolean resultado = eventDB.insertarEvento(evento);
            conexion.commit();
            return resultado;
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar evento: " + e.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("⛔ Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("⚠ Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }

    /**
     * Lista todos los eventos disponibles en la base de datos.
     * 
     * @return Lista de objetos Event.
     */
    public List<Event> listarEventos() {
        return eventDB.obtenerTodosLosEventos();
    }

    /**
     * Elimina un evento por su ID. Maneja transacción para asegurar consistencia.
     * 
     * @param id Identificador del evento a eliminar.
     * @return true si la eliminación fue exitosa; false si ocurrió un error.
     */
    public boolean eliminarEvento(int id) {
        try {
            conexion.setAutoCommit(false);
            boolean resultado = eventDB.eliminarEvento(id);
            conexion.commit();
            return resultado;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar evento ID=" + id + ": " + e.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("⛔ Rollback fallido: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("⚠ Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }

    /**
     * Actualiza los datos de un evento existente. Se usa transacción para aplicar el cambio de forma segura.
     * 
     * @param evento Objeto Event con los nuevos valores y un ID válido.
     * @return true si la operación fue exitosa; false si ocurre algún error.
     */
    public boolean actualizarEvento(Event evento) {
        try {
            conexion.setAutoCommit(false);
            boolean resultado = eventDB.actualizarEvento(evento);
            conexion.commit();
            return resultado;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar evento ID=" + evento.getId() + ": " + e.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("⛔ Rollback fallido: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("⚠ Error al restaurar autoCommit: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene un evento a partir de su ID.
     * 
     * @param id Identificador único.
     * @return Objeto Event si existe; null si no se encuentra.
     */
    public Event obtenerEventoPorId(int id) {
        return eventDB.obtenerEventoPorId(id);
    }

    /**
     * Método auxiliar para editar evento usando parámetros individuales.
     * Realiza las mismas validaciones que agregarEvento.
     * 
     * @param id ID del evento a editar.
     * @param contactoId ID del contacto asociado.
     * @param fecha Fecha nueva.
     * @param hora Hora nueva.
     * @param descripcion Descripción nueva.
     * @param ubicacion Ubicación nueva.
     * @return true si la actualización es exitosa; false si falla validación o SQL.
     */
    public boolean editarEvento(int id, int contactoId, LocalDate fecha, LocalTime hora, String descripcion, String ubicacion) {
        if (descripcion == null || descripcion.isBlank()) return false;
        if (ubicacion == null || ubicacion.isBlank()) return false;
        if (fecha == null || hora == null) return false;
        if (contactoId <= 0 || id <= 0) return false;

        Event evento = new Event(id, contactoId, fecha, hora, descripcion, ubicacion);

        return actualizarEvento(evento);
    }
}