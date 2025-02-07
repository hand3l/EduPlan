package eduplan.vista;

import eduplan.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class VentanaSuperUsuario extends JFrame {
    private Usuario usuarioActual;

    // El constructor ahora requiere un objeto Usuario (que debe ser de tipo "SuperUsuario")
    public VentanaSuperUsuario(Usuario usuarioActual) {
        if (!usuarioActual.getTipo().equals("SuperUsuario")) {
            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo el SuperUsuario puede acceder.");
            dispose();
            return;
        }
        this.usuarioActual = usuarioActual;
        setTitle("Panel de SuperUsuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 10, 10));

        JButton btnGestionUsuarios = new JButton("Gestión de Usuarios");
        JButton btnGestionCalificaciones = new JButton("Gestión de Calificaciones");
        JButton btnGestionAsistencia = new JButton("Gestión de Asistencia");
        JButton btnGestionHorarios = new JButton("Gestión de Horarios");

        // Se pasan el usuarioActual al llamar a los constructores que lo requieren
        btnGestionUsuarios.addActionListener(e -> new VentanaGestionUsuarios(usuarioActual).setVisible(true));
        btnGestionCalificaciones.addActionListener(e -> new VentanaGestionCalificaciones(usuarioActual).setVisible(true));
        btnGestionAsistencia.addActionListener(e -> new VentanaGestionAsistencia(usuarioActual).setVisible(true));
        btnGestionHorarios.addActionListener(e -> new VentanaGestionHorarios(usuarioActual).setVisible(true));

        add(btnGestionUsuarios);
        add(btnGestionCalificaciones);
        add(btnGestionAsistencia);
        add(btnGestionHorarios);
    }
}

