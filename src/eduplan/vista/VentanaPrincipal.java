package eduplan.vista;

import eduplan.controlador.ControladorLogin;
import eduplan.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JPanel panelPrincipal;  // 🔹 Agregado para conectar con el .form
    private JTextField txtCorreo;
    private JPasswordField txtContraseña;
    private JButton btnLogin;
    private JLabel lblBienvenida;
    private ControladorLogin controladorLogin;

    public VentanaPrincipal() {
        controladorLogin = new ControladorLogin();
        setTitle("EduPlan - Inicio de Sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal); // 🔹 Asegura que el diseño del GUI Builder se cargue
        pack(); // 🔹 Ajusta la ventana automáticamente según los componentes

        btnLogin.addActionListener(e -> iniciarSesion());
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contraseña = new String(txtContraseña.getPassword()).trim();

        Usuario usuario = controladorLogin.autenticarUsuario(correo, contraseña);

        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre() + " (" + usuario.getTipo() + ")");
            abrirPanelSegunTipo(usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirPanelSegunTipo(Usuario usuario) {
        if (usuario.getTipo().equals("SuperUsuario")) {
            new VentanaGestionUsuarios(usuario).setVisible(true);
        } else if (usuario.getTipo().equals("Profesor")) {
            new VentanaGestionHorarios(usuario).setVisible(true);
        } else {
            new VentanaEstudiante(usuario).setVisible(true);
        }
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}







