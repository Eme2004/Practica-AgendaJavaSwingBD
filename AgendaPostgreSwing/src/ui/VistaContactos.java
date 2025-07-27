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
 * @author USER
 */
public class VistaContactos extends JFrame {
     private JTable tabla;
    private DefaultTableModel modelo;

    public VistaContactos() {
        setTitle("Lista de Contactos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🧱 Modelo de tabla
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Email", "Dirección"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 📦 Cargar los datos desde la base
        cargarContactos();

        setVisible(true);
    }

    private void cargarContactos() {
        ContactoDAO dao = new ContactoDAO();
        List<Contacto> lista = dao.listar();

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

    // 🧪 Método de prueba independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaContactos());
    }
}
