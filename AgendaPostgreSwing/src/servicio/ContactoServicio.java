/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import dao.ContactoDAO;
import modelo.Contacto;
/**
 *
 * @author USER
 */
/**
 * La clase ContactoServicio actúa como capa de servicio para gestionar operaciones de negocio
 * relacionadas con contactos. Esta capa intermedia permite aplicar validaciones y reglas
 * antes de interactuar con la capa de acceso a datos (DAO).
 */
public class ContactoServicio {

    /**
     * Instancia de ContactoDAO utilizada para acceder a métodos de persistencia.
     * Se usa para insertar contactos en la base de datos.
     */
    private ContactoDAO dao = new ContactoDAO();

    /**
     * Agrega un nuevo contacto después de realizar validaciones básicas.
     * 
     * Validaciones aplicadas:
     * - El nombre no puede ser null.
     * - El nombre no puede estar vacío o contener solo espacios.
     * 
     * @param c Objeto Contacto a agregar.
     * @return true si el contacto fue insertado exitosamente; false si la validación falla o la inserción no se realiza.
     */
    public boolean agregarContacto(Contacto c) {
        if (c.getNombre() == null || c.getNombre().isBlank()) return false;
        return dao.insertar(c);
    }
}
