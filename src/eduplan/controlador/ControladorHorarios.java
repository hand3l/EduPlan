package eduplan.controlador;

import eduplan.modelo.Horario;
import eduplan.controlador.GestorHorarios;
import eduplan.modelo.Usuario;

import java.util.List;

public class ControladorHorarios {
    private GestorHorarios gestorHorarios;

    public ControladorHorarios() {
        this.gestorHorarios = new GestorHorarios();
    }

    public List<Horario> obtenerHorarios() {
        return gestorHorarios.obtenerHorarios();
    }

    public List<Usuario> obtenerProfesores() {
        return gestorHorarios.obtenerProfesores();
    }

    public void registrarHorario(int profesorId, String materia, String dia, String hora) {
        gestorHorarios.registrarHorario(profesorId, materia, dia, hora);
    }

    public void actualizarHorario(int idHorario, String materia, String dia, String hora, String tipoUsuario) {
        gestorHorarios.actualizarHorario(idHorario, materia, dia, hora, tipoUsuario);
    }

    public void eliminarHorario(int idHorario, String tipoUsuario) {
        gestorHorarios.eliminarHorario(idHorario, tipoUsuario);
    }

}


