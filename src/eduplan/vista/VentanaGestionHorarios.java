package eduplan.vista;

import eduplan.controlador.ControladorHorarios;
import eduplan.modelo.Horario;
import eduplan.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaGestionHorarios extends JFrame {
    private ControladorHorarios controladorHorarios;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private JComboBox<Usuario> cmbProfesores;
    private JTextField txtMateria;
    private JTextField txtDia;
    private JTextField txtHora;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private boolean esSuperUsuario;
    private JTextField txtProfesorId;
    private JComboBox<String> cbDia;




    public VentanaGestionHorarios(Usuario usuarioActual) {
        controladorHorarios = new ControladorHorarios();
        esSuperUsuario = usuarioActual.getTipo().equals("SuperUsuario");

        setTitle("Gestión de Horarios");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Tabla de horarios**
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Profesor", "Materia", "Día", "Hora"}, 0);
        tablaHorarios = new JTable(modeloTabla);
        add(new JScrollPane(tablaHorarios), BorderLayout.CENTER);

        // **Panel de ingreso de datos**
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        cmbProfesores = new JComboBox<>(controladorHorarios.obtenerProfesores().toArray(new Usuario[0]));
        txtMateria = new JTextField(10);
        txtDia = new JTextField(10);
        txtHora = new JTextField(10);
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelInferior.add(new JLabel("Profesor:"));
        panelInferior.add(cmbProfesores);
        panelInferior.add(new JLabel("Materia:"));
        panelInferior.add(txtMateria);
        panelInferior.add(new JLabel("Día:"));
        panelInferior.add(txtDia);
        panelInferior.add(new JLabel("Hora:"));
        panelInferior.add(txtHora);
        panelInferior.add(btnAgregar);
        panelInferior.add(btnActualizar);
        panelInferior.add(btnEliminar);

        add(panelInferior, BorderLayout.SOUTH);

        // **Eventos de los botones**
        btnAgregar.addActionListener(e -> agregarHorario());

        // **Solo el superusuario puede actualizar o eliminar horarios**
        btnActualizar.setEnabled(esSuperUsuario);
        btnEliminar.setEnabled(esSuperUsuario);

        if (esSuperUsuario) {
            btnActualizar.addActionListener(e -> actualizarHorario());
            btnEliminar.addActionListener(e -> eliminarHorario());
        }

        // Cargar horarios al iniciar
        cargarHorarios();
    }

    private void cargarHorarios() {
        modeloTabla.setRowCount(0);
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

    private void agregarHorario() {
        Usuario profesor = (Usuario) cmbProfesores.getSelectedItem();
        String materia = txtMateria.getText().trim();
        String dia = txtDia.getText().trim();
        String hora = txtHora.getText().trim();

        controladorHorarios.registrarHorario(profesor.getId(), materia, dia, hora);
        cargarHorarios();
        txtMateria.setText("");
        txtDia.setText("");
        txtHora.setText("");
    }

    private void actualizarHorario() {
        int filaSeleccionada = tablaHorarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario para actualizar.");
            return;
        }

        int idHorario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String materia = txtMateria.getText().trim();
        String dia = txtDia.getText().trim();
        String hora = txtHora.getText().trim();

        controladorHorarios.actualizarHorario(idHorario, materia, dia, hora, "SuperUsuario");
        cargarHorarios();
    }

    private void eliminarHorario() {
        int filaSeleccionada = tablaHorarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario para eliminar.");
            return;
        }

        int idHorario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        controladorHorarios.eliminarHorario(idHorario, "SuperUsuario");
        cargarHorarios();
    }
}


