/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import modelo.Event;
import servicio.EventoServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
/**
 *
 * @author USER
 */
public class VentanaEventos extends JFrame {
    private Connection conexion;
    private EventoServicio eventoServicio;
    private DefaultTableModel modeloTabla;
    private JTable tablaEventos;

    private JTextField txtContactoId, txtFecha, txtHora, txtDescripcion, txtUbicacion;
    private int idEventoSeleccionado = -1;

    public VentanaEventos(Connection conexion) {
        this.eventoServicio = new EventoServicio(conexion);

        setTitle("Gestión de Eventos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponentes();
        cargarEventos();
    }

    private void initComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));

        txtContactoId = new JTextField();
        txtFecha = new JTextField(); // formato yyyy-MM-dd
        txtHora = new JTextField();  // formato HH:mm:ss
        txtDescripcion = new JTextField();
        txtUbicacion = new JTextField();

        panelFormulario.add(new JLabel("ID Contacto:"));
        panelFormulario.add(txtContactoId);
        panelFormulario.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Hora (HH:mm:ss):"));
        panelFormulario.add(txtHora);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(new JLabel("Ubicación:"));
        panelFormulario.add(txtUbicacion);

        JButton btnAgregar = new JButton("Agregar Evento");
        JButton btnEliminar = new JButton("Eliminar Evento");
        JButton btnEditar = new JButton("Editar Evento");

        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(new JLabel()); // espacio vacío
        panelFormulario.add(btnEditar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "ID Contacto", "Fecha", "Hora", "Descripción", "Ubicación"}, 0);
        tablaEventos = new JTable(modeloTabla);
        add(new JScrollPane(tablaEventos), BorderLayout.CENTER);

        // Eventos de botones
        btnAgregar.addActionListener(e -> agregarEvento());
        btnEliminar.addActionListener(e -> eliminarEventoSeleccionado());
        btnEditar.addActionListener(e -> editarEvento());

        // Cargar datos al hacer clic en la tabla
        tablaEventos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tablaEventos.getSelectedRow() != -1) {
                int fila = tablaEventos.getSelectedRow();
                idEventoSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                txtContactoId.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtFecha.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtHora.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtDescripcion.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtUbicacion.setText(modeloTabla.getValueAt(fila, 5).toString());
            }
        });
    }

    private void agregarEvento() {
        try {
            int contactoId = Integer.parseInt(txtContactoId.getText().trim());
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());
            String descripcion = txtDescripcion.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();

            boolean exito = eventoServicio.agregarEvento(contactoId, fecha, hora, descripcion, ubicacion);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Evento agregado con éxito");
                limpiarCampos();
                cargarEventos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar evento. Verifica los campos.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminarEventoSeleccionado() {
        int fila = tablaEventos.getSelectedRow();
        if (fila != -1) {
            int idEvento = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar evento ID " + idEvento + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = eventoServicio.eliminarEvento(idEvento);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Evento eliminado.");
                    cargarEventos();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el evento.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un evento para eliminar.");
        }
    }

    private void editarEvento() {
        if (idEventoSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un evento para editar.");
            return;
        }

        try {
            int contactoId = Integer.parseInt(txtContactoId.getText().trim());
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
            LocalTime hora = LocalTime.parse(txtHora.getText().trim());
            String descripcion = txtDescripcion.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();

            boolean actualizado = eventoServicio.editarEvento(idEventoSeleccionado, contactoId, fecha, hora, descripcion, ubicacion);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Evento actualizado correctamente.");
                cargarEventos();
                limpiarCampos();
                idEventoSeleccionado = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar evento.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarEventos() {
        modeloTabla.setRowCount(0);
        List<Event> eventos = eventoServicio.listarEventos();
        for (Event ev : eventos) {
            modeloTabla.addRow(new Object[]{
                    ev.getId(),
                    ev.getContactoId(),
                    ev.getFecha(),
                    ev.getHora(),
                    ev.getDescripcion(),
                    ev.getUbicacion()
            });
        }
    }

    private void limpiarCampos() {
        txtContactoId.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtDescripcion.setText("");
        txtUbicacion.setText("");
        idEventoSeleccionado = -1;
    }
}