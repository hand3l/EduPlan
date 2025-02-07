package eduplan.vista;

import eduplan.controlador.ControladorCalificaciones;
import eduplan.modelo.Usuario;
import eduplan.modelo.Calificacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionCalificaciones extends JFrame {
    private ControladorCalificaciones controladorCalificaciones;
    private JTable tablaCalificaciones;
    private DefaultTableModel modeloTabla;
    private JComboBox<Usuario> cmbEstudiantes;
    private JTextField txtMateria, txtNota;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private boolean esSuperUsuario;

    public VentanaGestionCalificaciones(Usuario usuarioActual) {
        controladorCalificaciones = new ControladorCalificaciones();
        esSuperUsuario = usuarioActual.getTipo().equals("SuperUsuario");

        setTitle("Gestión de Calificaciones");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Tabla de calificaciones**
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Estudiante", "Materia", "Nota"}, 0);
        tablaCalificaciones = new JTable(modeloTabla);
        add(new JScrollPane(tablaCalificaciones), BorderLayout.CENTER);

        // **Panel de ingreso de datos**
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        cmbEstudiantes = new JComboBox<>(controladorCalificaciones.obtenerEstudiantes().toArray(new Usuario[0]));
        txtMateria = new JTextField(10);
        txtNota = new JTextField(5);
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelInferior.add(new JLabel("Estudiante:"));
        panelInferior.add(cmbEstudiantes);
        panelInferior.add(new JLabel("Materia:"));
        panelInferior.add(txtMateria);
        panelInferior.add(new JLabel("Nota:"));
        panelInferior.add(txtNota);
        panelInferior.add(btnAgregar);
        panelInferior.add(btnActualizar);
        panelInferior.add(btnEliminar);

        add(panelInferior, BorderLayout.SOUTH);

        // **Eventos de los botones**
        btnAgregar.addActionListener(e -> agregarCalificacion());

        // **Solo el superusuario puede actualizar o eliminar calificaciones**
        btnActualizar.setEnabled(esSuperUsuario);
        btnEliminar.setEnabled(esSuperUsuario);

        if (esSuperUsuario) {
            btnActualizar.addActionListener(e -> actualizarCalificacion());
            btnEliminar.addActionListener(e -> eliminarCalificacion());
        }

        // Cargar calificaciones al iniciar
        cargarCalificaciones();
    }

    private void cargarCalificaciones() {
        modeloTabla.setRowCount(0);
        List<Calificacion> calificaciones = controladorCalificaciones.obtenerCalificaciones();
        for (Calificacion calificacion : calificaciones) {
            modeloTabla.addRow(new Object[]{
                    calificacion.getId(),
                    calificacion.getEstudiante().getNombre(),
                    calificacion.getMateria(),
                    calificacion.getNota()
            });
        }
    }

    private void agregarCalificacion() {
        Usuario estudiante = (Usuario) cmbEstudiantes.getSelectedItem();
        String materia = txtMateria.getText().trim();
        String notaTexto = txtNota.getText().trim();

        try {
            double nota = Double.parseDouble(notaTexto);
            if (nota < 0 || nota > 10) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 10.");
                return;
            }
            controladorCalificaciones.registrarCalificacion(estudiante.getId(), materia, nota, "Profesor");
            cargarCalificaciones();
            txtMateria.setText("");
            txtNota.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido para la nota.");
        }
    }

    private void actualizarCalificacion() {
        int filaSeleccionada = tablaCalificaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una calificación para actualizar.");
            return;
        }

        int idCalificacion = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String materia = txtMateria.getText().trim();
        String notaTexto = txtNota.getText().trim();

        try {
            double nota = Double.parseDouble(notaTexto);
            if (nota < 0 || nota > 10) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 10.");
                return;
            }
            // Aquí se pasa el cuarto parámetro ("Profesor")
            controladorCalificaciones.actualizarCalificacion(idCalificacion, materia, nota, "Profesor");
            cargarCalificaciones();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido para la nota.");
        }
    }



    private void eliminarCalificacion() {
        int filaSeleccionada = tablaCalificaciones.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una calificación para eliminar.");
            return;
        }

        int idCalificacion = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        controladorCalificaciones.eliminarCalificacion(idCalificacion, "Profesor");
        cargarCalificaciones();
    }
}
