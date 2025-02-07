package eduplan.vista;

import eduplan.controlador.ControladorCalificaciones;
import eduplan.modelo.Usuario;
import eduplan.modelo.Calificacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerCalificaciones extends JFrame {
    private ControladorCalificaciones controladorCalificaciones;
    private JTable tablaCalificaciones;
    private DefaultTableModel modeloTabla;
    private Usuario usuarioActual;

    public VentanaVerCalificaciones(Usuario usuarioActual) {
        if (!usuarioActual.getTipo().equals("Estudiante")) {
            JOptionPane.showMessageDialog(null, "Solo los estudiantes pueden ver calificaciones.");
            dispose();
            return;
        }

        this.usuarioActual = usuarioActual;
        controladorCalificaciones = new ControladorCalificaciones();

        setTitle("Mis Calificaciones");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Tabla de calificaciones**
        modeloTabla = new DefaultTableModel(new String[]{"Materia", "Nota"}, 0);
        tablaCalificaciones = new JTable(modeloTabla);
        add(new JScrollPane(tablaCalificaciones), BorderLayout.CENTER);

        // Cargar calificaciones
        cargarCalificaciones();
    }

    private void cargarCalificaciones() {
        modeloTabla.setRowCount(0);
        List<Calificacion> calificaciones = controladorCalificaciones.obtenerCalificacionesPorEstudiante(usuarioActual.getId());

        if (calificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes calificaciones registradas.");
        } else {
            for (Calificacion cal : calificaciones) {
                modeloTabla.addRow(new Object[]{cal.getMateria(), cal.getNota()});
            }
        }
    }
}


