package eduplan.vista;

import eduplan.controlador.ControladorAsistencia;
import eduplan.modelo.Asistencia;
import eduplan.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionAsistencia extends JFrame {
    private ControladorAsistencia controladorAsistencia;
    private JTable tablaAsistencia;
    private DefaultTableModel modeloTabla;
    private JComboBox<Usuario> cmbEstudiantes;
    private JTextField txtFecha;
    // Usaremos un JComboBox para indicar el estado ("Presente" o "Ausente")
    private JComboBox<String> chkPresente;
    private JButton btnRegistrar, btnActualizar, btnEliminar;

    public VentanaGestionAsistencia(Usuario usuarioActual) {
        controladorAsistencia = new ControladorAsistencia();

        setTitle("Gestión de Asistencia");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurar la tabla de asistencias
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Estudiante", "Fecha", "Estado"}, 0);
        tablaAsistencia = new JTable(modeloTabla);
        add(new JScrollPane(tablaAsistencia), BorderLayout.CENTER);

        // Panel de ingreso de datos
        JPanel panelInferior = new JPanel(new FlowLayout());

        // ComboBox para seleccionar el estudiante
        cmbEstudiantes = new JComboBox<>(controladorAsistencia.obtenerEstudiantes().toArray(new Usuario[0]));
        // Campo de texto para ingresar la fecha (formato "YYYY-MM-DD")
        txtFecha = new JTextField(10);
        // ComboBox para el estado de asistencia
        chkPresente = new JComboBox<>(new String[]{"Presente", "Ausente"});
        // Botones de acciones
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelInferior.add(new JLabel("Estudiante:"));
        panelInferior.add(cmbEstudiantes);
        panelInferior.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panelInferior.add(txtFecha);
        panelInferior.add(new JLabel("Estado:"));
        panelInferior.add(chkPresente);
        panelInferior.add(btnRegistrar);
        panelInferior.add(btnActualizar);
        panelInferior.add(btnEliminar);

        add(panelInferior, BorderLayout.SOUTH);

        // Asignar eventos a los botones
        btnRegistrar.addActionListener(e -> registrarAsistencia());
        btnActualizar.addActionListener(e -> actualizarAsistencia());
        btnEliminar.addActionListener(e -> eliminarAsistencia());

        // Cargar la tabla con los datos de asistencia al iniciar la ventana
        cargarAsistencias();
    }

    private void cargarAsistencias() {
        modeloTabla.setRowCount(0);
        List<Asistencia> asistencias = controladorAsistencia.obtenerAsistencias();
        for (Asistencia asistencia : asistencias) {
            modeloTabla.addRow(new Object[]{
                    asistencia.getId(),
                    asistencia.getEstudiante().getNombre(),
                    asistencia.getFecha(),
                    asistencia.isAsistio() ? "Presente" : "Ausente"
            });
        }
    }

    private void registrarAsistencia() {
        Usuario estudiante = (Usuario) cmbEstudiantes.getSelectedItem();
        String fecha = txtFecha.getText().trim();
        // Convertir el valor del JComboBox a booleano:
        String estado = (String) chkPresente.getSelectedItem();
        boolean asistio = estado.equalsIgnoreCase("Presente");

        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una fecha válida.");
            return;
        }

        // Llamamos al método registrarAsistencia, pasando el tipo de usuario ("Profesor" en este ejemplo)
        controladorAsistencia.registrarAsistencia(estudiante.getId(), fecha, asistio, "Profesor");
        cargarAsistencias();
        txtFecha.setText("");
        chkPresente.setSelectedItem("Presente");
    }

    private void actualizarAsistencia() {
        int filaSeleccionada = tablaAsistencia.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de asistencia para actualizar.");
            return;
        }
        int idAsistencia = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String fecha = txtFecha.getText().trim();
        String estado = (String) chkPresente.getSelectedItem();
        boolean asistio = estado.equalsIgnoreCase("Presente");

        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una fecha válida.");
            return;
        }

        // Llamada para actualizar asistencia; en este ejemplo se pasa "Profesor" como tipo de usuario
        controladorAsistencia.actualizarAsistencia(idAsistencia, fecha, asistio, "Profesor");
        cargarAsistencias();
    }

    private void eliminarAsistencia() {
        int filaSeleccionada = tablaAsistencia.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de asistencia para eliminar.");
            return;
        }
        int idAsistencia = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        // Llamada para eliminar asistencia; en este ejemplo se pasa "SuperUsuario" para la eliminación
        controladorAsistencia.eliminarAsistencia(idAsistencia, "SuperUsuario");
        cargarAsistencias();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

