package eduplan.vista;

import eduplan.controlador.ControladorUsuarios;
import eduplan.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionUsuarios extends JFrame {
    private ControladorUsuarios controladorUsuarios;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContraseña;
    private JComboBox<String> cmbTipoUsuario;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private boolean esSuperUsuario;
    private Usuario usuarioActual; // Declaramos la variable

    public VentanaGestionUsuarios(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual; // Inicializamos la variable
        controladorUsuarios = new ControladorUsuarios();
        esSuperUsuario = usuarioActual.getTipo().equals("SuperUsuario");

        setTitle("Gestión de Usuarios");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Tabla para mostrar usuarios**
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Correo", "Tipo"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        // **Panel de ingreso de datos**
        JPanel panelInferior = new JPanel(new FlowLayout());
        txtNombre = new JTextField(10);
        txtCorreo = new JTextField(10);
        txtContraseña = new JPasswordField(10);
        cmbTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "Profesor"});
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelInferior.add(new JLabel("Nombre:"));
        panelInferior.add(txtNombre);
        panelInferior.add(new JLabel("Correo:"));
        panelInferior.add(txtCorreo);
        panelInferior.add(new JLabel("Contraseña:"));
        panelInferior.add(txtContraseña);
        panelInferior.add(new JLabel("Tipo:"));
        panelInferior.add(cmbTipoUsuario);
        panelInferior.add(btnAgregar);
        panelInferior.add(btnActualizar);
        panelInferior.add(btnEliminar);

        add(panelInferior, BorderLayout.SOUTH);

        // **Acción del botón Agregar**
        btnAgregar.addActionListener(e -> agregarUsuario());

        // **Solo el superusuario puede actualizar o eliminar**
        btnActualizar.setEnabled(esSuperUsuario);
        btnEliminar.setEnabled(esSuperUsuario);

        if (esSuperUsuario) {
            btnActualizar.addActionListener(e -> actualizarUsuario());
            btnEliminar.addActionListener(e -> eliminarUsuario());
        }

        // Cargar usuarios al iniciar
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0); // Limpia la tabla
        List<Usuario> usuarios = controladorUsuarios.obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            modeloTabla.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getTipo()
            });
        }
    }

    private void agregarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        String tipo = (String) cmbTipoUsuario.getSelectedItem();

        if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        controladorUsuarios.registrarUsuario(nombre, correo, contraseña, tipo);
        cargarUsuarios();
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContraseña.setText("");
    }

    private void actualizarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para actualizar.");
            return;
        }

        int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();
        String tipo = (String) cmbTipoUsuario.getSelectedItem();

        controladorUsuarios.actualizarUsuario(idUsuario, nombre, correo, contraseña, tipo, usuarioActual.getTipo());
        cargarUsuarios();
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.");
            return;
        }

        int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        controladorUsuarios.eliminarUsuario(idUsuario, usuarioActual.getTipo());
        cargarUsuarios();
    }
}



