package eduplan.controlador;

import eduplan.modelo.Calificacion;
import eduplan.controlador.GestorCalificaciones;
import eduplan.modelo.Usuario;
import eduplan.controlador.ControladorUsuarios;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControladorCalificaciones {
    private GestorCalificaciones gestorCalificaciones;
    private ControladorUsuarios controladorUsuarios; // Para obtener la lista de usuarios

    public ControladorCalificaciones() {
        this.gestorCalificaciones = new GestorCalificaciones();
        this.controladorUsuarios = new ControladorUsuarios();
    }

    // Método para obtener las calificaciones de un estudiante
    public List<Calificacion> obtenerCalificacionesPorEstudiante(int estudianteId) {
        return gestorCalificaciones.obtenerCalificacionesPorEstudiante(estudianteId);
    }

    // Nuevo método: obtener todas las calificaciones (sin filtro)
    public List<Calificacion> obtenerCalificaciones() {
        return gestorCalificaciones.obtenerCalificaciones();
    }

    // Método agregado para obtener la lista de estudiantes
    public List<Usuario> obtenerEstudiantes() {
        List<Usuario> todos = controladorUsuarios.obtenerUsuarios();
        return todos.stream()
                .filter(u -> u.getTipo().equals("Estudiante"))
                .collect(Collectors.toList());
    }

    public void registrarCalificacion(int estudianteId, String materia, double nota, String tipoUsuario) {
        gestorCalificaciones.registrarCalificacion(estudianteId, materia, nota, tipoUsuario);
    }


    public void actualizarCalificacion(int idCalificacion, String materia, double nuevaNota, String tipoUsuario) {
        gestorCalificaciones.actualizarCalificacion(idCalificacion, materia, nuevaNota, tipoUsuario);
    }

    public void eliminarCalificacion(int idCalificacion, String tipoUsuario) {
        gestorCalificaciones.eliminarCalificacion(idCalificacion, tipoUsuario);
    }



}





