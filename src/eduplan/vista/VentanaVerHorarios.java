package eduplan.vista;

import eduplan.controlador.ControladorHorarios;
import eduplan.modelo.Horario;
import eduplan.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaVerHorarios extends JFrame {
    private ControladorHorarios controladorHorarios;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private Usuario usuarioActual;

    public VentanaVerHorarios(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        controladorHorarios = new ControladorHorarios();
        setTitle("Mis Horarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurar la tabla para mostrar horarios
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Profesor", "Materia", "Día", "Hora"}, 0);
        tablaHorarios = new JTable(modeloTabla);
        add(new JScrollPane(tablaHorarios), BorderLayout.CENTER);

        cargarHorarios();
    }

    private void cargarHorarios() {
        modeloTabla.setRowCount(0);
        // Si se desea filtrar los horarios específicos del estudiante, se podría aplicar un filtro.
        // Por ahora, se muestran todos los horarios.
        List<Horario> horarios = controladorHorarios.obtenerHorarios();
        for (Horario horario : horarios) {
            modeloTabla.addRow(new Object[]{
                    horario.getId(),
                    horario.getProfesor().getNombre(),
                    horario.getMateria(),
                    horario.getDia(),
                    horario.getHora()
            });
        }
    }
}
