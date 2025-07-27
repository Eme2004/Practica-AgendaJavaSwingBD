/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Emesis
 */
// Clase que representa la ventana principal de la agenda
public class FrmPrincipal extends JFrame {

    // Constructor: configura la ventana
    public FrmPrincipal() {
        setTitle("Agenda de Contactos");        // Título de la ventana
        setSize(400, 200);                      // Tamaño de la ventana
        setLocationRelativeTo(null);           // Centra la ventana en pantalla
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Finaliza la aplicación al cerrar
        setLayout(new FlowLayout());           // Usa un layout simple (flujo horizontal)

        //  Botón para ver contactos existentes
        JButton btnVerContactos = new JButton("📄 Ver Contactos");

        //  Botón para abrir formulario y agregar nuevo contacto
        JButton btnAgregar = new JButton("➕ Agregar Contacto");

        // Se agregan los botones a la ventana
        add(btnVerContactos);
        add(btnAgregar);

        // Acción al hacer clic en "Ver Contactos": abre la ventana de lista
        btnVerContactos.addActionListener(e -> new VistaContactos());

        // Acción al hacer clic en "Agregar Contacto": abre el formulario de ingreso
        btnAgregar.addActionListener(e -> new FrmAgregarContacto());

        // Hace visible la ventana principal
        setVisible(true);
    }

    // Punto de entrada de la aplicación: lanza la interfaz principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPrincipal());
    }
}