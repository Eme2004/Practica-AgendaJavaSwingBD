/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import dao.ContactoDAO;
import modelo.Contacto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author Emesis
 */
// Clase que representa la ventana de visualización de contactos en una tabla
public class VistaContactos extends JFrame {

    private JTable tabla;               // Tabla que mostrará los contactos
    private DefaultTableModel modelo;   // Modelo que gestiona las filas/columnas de la tabla

    // Constructor: configura la ventana
    public VistaContactos() {
        setTitle("Lista de Contactos");       // Título de la ventana
        setSize(600, 400);                    // Dimensiones
        setLocationRelativeTo(null);          // Centrado en pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana
        setLayout(new BorderLayout());        // Diseño de tipo "borde"

        //  Crear modelo de la tabla con nombres de columnas
        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Teléfono", "Email", "Dirección"}, 0
        );

        // Crear tabla con ese modelo
        tabla = new JTable(modelo);

        // Agregar la tabla con scroll automático
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        //  Cargar datos de la base en la tabla
        cargarContactos();

        // Mostrar ventana
        setVisible(true);
    }

    // Método que llena la tabla con datos desde la base de datos
    private void cargarContactos() {
        ContactoDAO dao = new ContactoDAO();  // Instancia del DAO
        List<Contacto> lista = dao.listar();  // Lista de contactos

        for (Contacto c : lista) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getTelefono(),
                c.getEmail(),
                c.getDireccion()
            });
        }
    }

    //  Método main para probar esta ventana directamente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaContactos());
    }
}
