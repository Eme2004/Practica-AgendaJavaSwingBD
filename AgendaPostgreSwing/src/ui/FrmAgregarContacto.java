/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import dao.ContactoDAO;
import modelo.Contacto;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Emesis
 */
// Clase que representa la ventana de formulario para agregar un nuevo contacto
public class FrmAgregarContacto extends JFrame {

    // Campos de texto para capturar los datos del contacto
    private JTextField txtNombre, txtTelefono, txtEmail, txtDireccion;

    // Botón principal para guardar el contacto
    private JButton btnGuardar;

    // Constructor: se configura la ventana y se agregan los componentes
    public FrmAgregarContacto() {
        setTitle("Agregar nuevo contacto");         // Título de la ventana
        setSize(350, 300);                          // Tamaño de la ventana
        setLocationRelativeTo(null);                // Centrar ventana en pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLayout(new GridLayout(6, 2, 5, 5));      // Diseño de rejilla (6 filas, 2 columnas, espacio 5px)

        //  Agregar componentes de formulario
        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel("Correo:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        add(txtDireccion);

        // Botones
        btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
        add(btnCancelar);

        // ️ Acción al hacer clic en "Guardar"
        btnGuardar.addActionListener(e -> {
            Contacto c = new Contacto();
            c.setNombre(txtNombre.getText());
            c.setTelefono(txtTelefono.getText());
            c.setEmail(txtEmail.getText());
            c.setDireccion(txtDireccion.getText());

            // Usa el DAO para insertar el contacto
            ContactoDAO dao = new ContactoDAO();
            if (dao.insertar(c)) {
                JOptionPane.showMessageDialog(this, "Contacto guardado exitosamente.");
                dispose(); // Cierra la ventana
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el contacto.");
            }
        });

        //  Acción al hacer clic en "Cancelar"
        btnCancelar.addActionListener(e -> dispose());

        // Hace visible la ventana
        setVisible(true);
    }

    //  Método main para ejecutar la ventana de forma independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAgregarContacto());
    }
}