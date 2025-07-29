/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import ui.Formulario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 *
 * @author USER
 */
public class TablaContactos extends JFrame {
    private JTable tblContactos;
    private DefaultTableModel modelo;
    private JButton btnEditar, btnEliminar, btnSalir;

    public TablaContactos() {
        setTitle("Lista de Contactos");
        setSize(700, 400);
        setLocationRelativeTo(null);

        // ✅ Cambiado para que NO se destruya la ventana al cerrarla
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellidos", "Teléfono", "Email", "Dirección"}, 0);
        tblContactos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblContactos);

        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnSalir);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnEditar.addActionListener(e -> {
            int fila = tblContactos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un contacto para editar.");
                return;
            }

            // Obtener datos de la fila seleccionada
            String nombre = modelo.getValueAt(fila, 1).toString();
            String apellidos = modelo.getValueAt(fila, 2).toString();
            String telefono = modelo.getValueAt(fila, 3).toString();
            String email = modelo.getValueAt(fila, 4).toString();
            String direccion = modelo.getValueAt(fila, 5).toString();

            // Abrir formulario
            Formulario formulario = new Formulario(modelo, fila, nombre, apellidos, telefono, email, direccion);
            formulario.setVisible(true);
        });

        btnEliminar.addActionListener(e -> {
            int fila = tblContactos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un contacto para eliminar.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar contacto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                modelo.removeRow(fila);
            }
        });

        // ✅ Cambiado para que no destruya la ventana, solo la oculte
        btnSalir.addActionListener(e -> setVisible(false));
    }

    // Método para agregar contacto desde otra clase (ejemplo: tu formulario)
    public void agregarContacto(String nombre, String apellidos, String telefono, String email, String direccion) {
        int id = modelo.getRowCount() + 1;
        modelo.addRow(new Object[]{id, nombre, apellidos, telefono, email, direccion});
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}
