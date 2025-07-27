/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author USER
 */
public class FrmPrincipal extends JFrame {
  
    public FrmPrincipal() {
        setTitle("Agenda de Contactos");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton btnVerContactos = new JButton("📄 Ver Contactos");
        JButton btnAgregar = new JButton("➕ Agregar Contacto");

        add(btnVerContactos);
        add(btnAgregar);

        // Abrir ventana de listado
        btnVerContactos.addActionListener(e -> new VistaContactos());

        // Abrir ventana de agregar
        btnAgregar.addActionListener(e -> new FrmAgregarContacto());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPrincipal());
    }


}
