package eduplan.vista;

import eduplan.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaProfesor extends JFrame {
    private Usuario profesor;
    private JButton btnGestionCalificaciones;
    private JButton btnGestionAsistencia;
    private JButton btnCerrarSesion;

    public VentanaProfesor(Usuario profesor) {
        this.profesor = profesor;
        setTitle("EduPlan - Panel del Profesor");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Panel de bienvenida**
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Bienvenido, " + profesor.getNombre() + " (Profesor)"));
        add(panelSuperior, BorderLayout.NORTH);

        // **Panel de botones**
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(2, 1, 10, 10));

        btnGestionCalificaciones = new JButton("Gestionar Calificaciones");
        btnGestionAsistencia = new JButton("Gestionar Asistencia");

        panelCentro.add(btnGestionCalificaciones);
        panelCentro.add(btnGestionAsistencia);

        add(panelCentro, BorderLayout.CENTER);

        // **Panel de cierre de sesión**
        JPanel panelInferior = new JPanel();
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelInferior.add(btnCerrarSesion);
        add(panelInferior, BorderLayout.SOUTH);

        // **Eventos de botones**
        btnGestionCalificaciones.addActionListener(e -> abrirGestionCalificaciones());
        btnGestionAsistencia.addActionListener(e -> abrirGestionAsistencia());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void abrirGestionCalificaciones() {
        VentanaGestionCalificaciones ventana = new VentanaGestionCalificaciones(profesor);
        ventana.setVisible(true);
    }

    private void abrirGestionAsistencia() {
        VentanaGestionAsistencia ventana = new VentanaGestionAsistencia(profesor);
        ventana.setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        new VentanaPrincipal().setVisible(true);
    }
}

