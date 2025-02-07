package eduplan.controlador;

import eduplan.modelo.Asistencia;
import eduplan.modelo.DatabaseConnection;
import eduplan.modelo.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorAsistencia {

    // Método para obtener todas las asistencias
    public List<Asistencia> obtenerAsistencias() {
        List<Asistencia> asistencias = new ArrayList<>();
        String query = "SELECT a.id, a.estudiante_id, a.fecha, a.asistio, u.nombre AS estudiante_nombre " +
                "FROM asistencia a JOIN usuarios u ON a.estudiante_id = u.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int estudianteId = rs.getInt("estudiante_id");
                Date fecha = rs.getDate("fecha");
                boolean asistio = rs.getBoolean("asistio");
                String estudianteNombre = rs.getString("estudiante_nombre");
                // Crear un objeto Usuario (solo con id y nombre, ya que no necesitamos más datos para este caso)
                Usuario estudiante = new Usuario(estudianteId, estudianteNombre, null, null, "Estudiante");
                Asistencia asistencia = new Asistencia(id, estudiante, fecha, asistio);
                asistencias.add(asistencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asistencias;
    }

    // Método para registrar una asistencia
    // Se espera que la fecha se pase en formato java.sql.Date
    // Solo los usuarios de tipo "Profesor" o "SuperUsuario" pueden registrar asistencia
    public boolean registrarAsistencia(int estudianteId, Date fecha, boolean asistio, String tipoUsuario) {
        if (!tipoUsuario.equals("Profesor") && !tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo Profesores y SuperUsuario pueden registrar asistencia.");
            return false;
        }
        String sql = "INSERT INTO asistencia (estudiante_id, fecha, asistio) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, estudianteId);
            stmt.setDate(2, fecha);
            stmt.setBoolean(3, asistio);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar una asistencia
    // Se espera que la fecha se pase en formato java.sql.Date
    // Solo los usuarios de tipo "Profesor" o "SuperUsuario" pueden actualizar asistencia
    public boolean actualizarAsistencia(int idAsistencia, Date fecha, boolean asistio, String tipoUsuario) {
        if (!tipoUsuario.equals("Profesor") && !tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo Profesores y SuperUsuario pueden actualizar asistencia.");
            return false;
        }
        String sql = "UPDATE asistencia SET fecha = ?, asistio = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fecha);
            stmt.setBoolean(2, asistio);
            stmt.setInt(3, idAsistencia);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una asistencia
    // Solo el SuperUsuario puede eliminar registros de asistencia
    public boolean eliminarAsistencia(int idAsistencia, String tipoUsuario) {
        if (!tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede eliminar asistencia.");
            return false;
        }
        String sql = "DELETE FROM asistencia WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAsistencia);
            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}



