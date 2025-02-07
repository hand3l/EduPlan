package eduplan.controlador;

import eduplan.modelo.Asistencia;
import eduplan.modelo.DatabaseConnection;
import eduplan.modelo.Usuario;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ControladorAsistencia {
    private GestorAsistencia gestorAsistencia;
    private ControladorUsuarios controladorUsuarios;

    public ControladorAsistencia() {
        this.gestorAsistencia = new GestorAsistencia();
        this.controladorUsuarios = new ControladorUsuarios();
    }

    // Método para obtener la lista de estudiantes (filtrando usuarios cuyo tipo sea "Estudiante")
    public List<Usuario> obtenerEstudiantes() {
        List<Usuario> todos = controladorUsuarios.obtenerUsuarios();
        List<Usuario> estudiantes = new ArrayList<>();
        for (Usuario u : todos) {
            if (u.getTipo().equals("Estudiante")) {
                estudiantes.add(u);
            }
        }
        return estudiantes;
    }

    // Método para obtener todas las asistencias (la implementación de gestorAsistencia.obtenerAsistencias() debe existir)
    public List<Asistencia> obtenerAsistencias() {
        return gestorAsistencia.obtenerAsistencias();
    }

    // Método para registrar asistencia
    // Se espera que la fecha se envíe en formato "yyyy-MM-dd"
    // Solo los usuarios con tipo "Profesor" o "SuperUsuario" pueden registrar asistencia
    public boolean registrarAsistencia(int estudianteId, String fecha, boolean asistio, String tipoUsuario) {
        if (!tipoUsuario.equals("Profesor") && !tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo Profesores y SuperUsuario pueden registrar asistencia.");
            return false;
        }
        try {
            Date fechaSQL = Date.valueOf(fecha); // Convierte la cadena a java.sql.Date
            return gestorAsistencia.registrarAsistencia(estudianteId, fechaSQL, asistio, tipoUsuario);
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la conversión de fecha: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar asistencia
    // Se espera que la fecha se envíe en formato "yyyy-MM-dd"
    // Solo los usuarios con tipo "Profesor" o "SuperUsuario" pueden actualizar asistencia
    public boolean actualizarAsistencia(int idAsistencia, String fecha, boolean asistio, String tipoUsuario) {
        if (!tipoUsuario.equals("Profesor") && !tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo Profesores y SuperUsuario pueden actualizar asistencia.");
            return false;
        }
        try {
            Date fechaSQL = Date.valueOf(fecha);
            return gestorAsistencia.actualizarAsistencia(idAsistencia, fechaSQL, asistio, tipoUsuario);
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la conversión de fecha: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar asistencia (solo el SuperUsuario puede eliminar)
    public boolean eliminarAsistencia(int idAsistencia, String tipoUsuario) {
        if (!tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede eliminar asistencia.");
            return false;
        }
        return gestorAsistencia.eliminarAsistencia(idAsistencia, tipoUsuario);
    }
}
