/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Emesis
 */
/**
 * La clase Event representa un evento asociado a un contacto en el sistema.
 * 
 * Cada evento contiene información como:
 * - Fecha y hora en que ocurre
 * - Una descripción textual
 * - La ubicación del evento
 * - El ID del contacto asociado
 * - Un ID único para identificar el evento en la base de datos
 * 
 * Esta clase actúa como un modelo para transportar datos entre capas del sistema.
 */
public class Event {

    // Identificador único del evento (clave primaria en la base de datos)
    private int id;

    // Identificador del contacto asociado al evento (clave foránea)
    private int contactoId;

    // Fecha en que se realizará el evento
    private LocalDate fecha;

    // Hora del evento
    private LocalTime hora;

    // Breve descripción del evento
    private String descripcion;

    // Ubicación del evento
    private String ubicacion;

    /**
     * Constructor completo: ideal cuando se recupera el evento desde la base de datos
     * y se conoce el ID único.
     * 
     * @param id ID del evento (usualmente asignado por la base de datos).
     * @param contactoId ID del contacto asociado al evento.
     * @param fecha Fecha en formato LocalDate.
     * @param hora Hora en formato LocalTime.
     * @param descripcion Descripción del evento.
     * @param ubicacion Ubicación del evento.
     */
    public Event(int id, int contactoId, LocalDate fecha, LocalTime hora, String descripcion, String ubicacion) {
        this.id = id;
        this.contactoId = contactoId;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    /**
     * Constructor parcial: útil para crear nuevos eventos antes de ser insertados en la base de datos.
     * 
     * Se inicializa con un ID temporal (-1) hasta que se asigne un ID real por el sistema.
     * 
     * @param contactoId ID del contacto asociado.
     * @param fecha Fecha del evento.
     * @param hora Hora del evento.
     * @param descripcion Descripción breve.
     * @param ubicacion Lugar donde se realizará el evento.
     */
    public Event(int contactoId, LocalDate fecha, LocalTime hora, String descripcion, String ubicacion) {
        this(-1, contactoId, fecha, hora, descripcion, ubicacion);
    }

    // Getters: permiten leer los atributos de manera controlada

    public int getId() { return id; }
    public int getContactoId() { return contactoId; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getDescripcion() { return descripcion; }
    public String getUbicacion() { return ubicacion; }

    // Setters: permiten modificar atributos del objeto después de su creación

    public void setId(int id) { this.id = id; }
    public void setContactoId(int contactoId) { this.contactoId = contactoId; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    /**
     * Devuelve una representación legible del objeto Event.
     * Útil para depuración, logging o visualización rápida de datos.
     */
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", contactoId=" + contactoId +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}