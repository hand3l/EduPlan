package eduplan.vista;

import eduplan.modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VentanaUsuario extends JFrame {
    private Usuario usuario;
    private JButton btnVerCalificaciones;
    private JButton btnVerHorarios;
    private JButton btnCerrarSesion;

    public VentanaUsuario(Usuario usuario) {
        this.usuario = usuario;
        setTitle("EduPlan - Panel del Usuario");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // **Panel de bienvenida**
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Bienvenido, " + usuario.getNombre() + " (" + usuario.getTipo() + ")"));
        add(panelSuperior, BorderLayout.NORTH);

        // **Panel de botones**
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(2, 1, 10, 10));

        btnVerCalificaciones = new JButton("Ver Calificaciones");
        btnVerHorarios = new JButton("Ver Horarios");

        panelCentro.add(btnVerCalificaciones);
        panelCentro.add(btnVerHorarios);

        add(panelCentro, BorderLayout.CENTER);

        // **Panel de cierre de sesión**
        JPanel panelInferior = new JPanel();
        btnCerrarSesion = new JButton("Cerrar Sesión");
        panelInferior.add(btnCerrarSesion);
        add(panelInferior, BorderLayout.SOUTH);

        // **Eventos de botones**
        btnVerCalificaciones.addActionListener(e -> abrirVerCalificaciones());
        btnVerHorarios.addActionListener(e -> abrirVerHorarios());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private void abrirVerCalificaciones() {
        VentanaVerCalificaciones ventana = new VentanaVerCalificaciones(usuario);
        ventana.setVisible(true);
    }

    private void abrirVerHorarios() {
        VentanaVerHorarios ventana = new VentanaVerHorarios(usuario);
        ventana.setVisible(true);
    }

    private void cerrarSesion() {
        dispose();
        new VentanaPrincipal().setVisible(true);
    }
}


