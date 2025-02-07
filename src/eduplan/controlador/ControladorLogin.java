package eduplan.controlador;

import eduplan.modelo.Usuario;
import eduplan.controlador.GestorUsuarios;

public class ControladorLogin {
    private GestorUsuarios gestorUsuarios;

    public ControladorLogin() {
        this.gestorUsuarios = new GestorUsuarios();
    }

    public Usuario autenticarUsuario(String correo, String contraseña) {
        return gestorUsuarios.autenticarUsuario(correo, contraseña);
    }
}



