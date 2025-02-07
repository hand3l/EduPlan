package eduplan.vista;

import eduplan.modelo.Usuario;
import javax.swing.*;

public class VentanaEstudiante extends JFrame {
    private Usuario estudiante;

    public VentanaEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
        setTitle("Panel del Estudiante");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblBienvenida = new JLabel("Bienvenido, " + estudiante.getNombre() + " (Estudiante)", SwingConstants.CENTER);
        add(lblBienvenida);
    }
}

