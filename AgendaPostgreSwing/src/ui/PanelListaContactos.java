/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 *
 * @author USER
 */
public class PanelListaContactos extends JFrame{
     private JTable tabla;
    private DefaultTableModel modelo;

    public PanelListaContactos() {
        setTitle("Lista de Contactos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Teléfono", "Email", "Dirección"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);
    }

    public void agregarContacto(String nombre, String apellidos, String telefono, String email, String direccion) {
        int id = modelo.getRowCount() + 1;
        modelo.addRow(new Object[]{id, nombre, apellidos, telefono, email, direccion});
    }
}
