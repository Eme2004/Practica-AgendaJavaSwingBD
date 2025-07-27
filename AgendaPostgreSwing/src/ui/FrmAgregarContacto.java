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
 * @author USER
 */
public class FrmAgregarContacto extends JFrame{
     private JTextField txtNombre, txtTelefono, txtEmail, txtDireccion;
    private JButton btnGuardar;

    public FrmAgregarContacto() {
        setTitle("Agregar nuevo contacto");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        // 🧩 Componentes
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

        btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
        add(btnCancelar);

        // ⚙️ Acción guardar
        btnGuardar.addActionListener(e -> {
            Contacto c = new Contacto();
            c.setNombre(txtNombre.getText());
            c.setTelefono(txtTelefono.getText());
            c.setEmail(txtEmail.getText());
            c.setDireccion(txtDireccion.getText());

            ContactoDAO dao = new ContactoDAO();
            if (dao.insertar(c)) {
                JOptionPane.showMessageDialog(this, "Contacto guardado exitosamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el contacto.");
            }
        });

        // ❌ Acción cancelar
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    // 🧪 Método de prueba
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAgregarContacto());
    }
}
