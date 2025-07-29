/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emesis
 */
/**
 * La clase Group representa una agrupación genérica dentro del sistema.
 * 
 * Cada grupo tiene:
 * - Un identificador único (ID)
 * - Un nombre que lo describe o distingue
 * 
 * Esta clase sirve principalmente como modelo de datos o DTO para operaciones CRUD con base de datos.
 */
public class Group {

    /** Identificador único del grupo, asignado por el sistema o base de datos. */
    private int id;

    /** Nombre del grupo. Puede representar una categoría, etiqueta o conjunto de usuarios. */
    private String nombre;

    /**
     * Constructor vacío. 
     * Requerido por ciertas librerías (como frameworks ORM) para instanciar el objeto sin inicialización directa.
     */
    public Group() {}

    /**
     * Constructor parcial que permite crear un grupo con un nombre definido.
     * El ID puede ser asignado posteriormente por el sistema.
     * 
     * @param nombre Nombre del grupo.
     */
    public Group(String nombre) {
        this.nombre = nombre;
    }

    // MÉTODOS GETTER Y SETTER

    /**
     * Retorna el ID del grupo.
     * @return Identificador único del grupo.
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el ID al grupo.
     * @param id Identificador generado o definido externamente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna el nombre del grupo.
     * @return Nombre como cadena de texto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del grupo.
     * @param nombre Nombre descriptivo del grupo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}