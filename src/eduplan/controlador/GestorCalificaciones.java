package eduplan.controlador;

import eduplan.modelo.Calificacion;
import eduplan.modelo.DatabaseConnection;
import eduplan.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCalificaciones {

    // Método existente para obtener las calificaciones de un estudiante
    public List<Calificacion> obtenerCalificacionesPorEstudiante(int estudianteId) {
        List<Calificacion> calificaciones = new ArrayList<>();
        String query = "SELECT c.id, c.materia, c.nota, u.id AS estudiante_id, u.nombre AS estudiante_nombre " +
                "FROM calificaciones c JOIN usuarios u ON c.estudiante_id = u.id " +
                "WHERE c.estudiante_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, estudianteId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario estudiante = new Usuario(
                        rs.getInt("estudiante_id"),
                        rs.getString("estudiante_nombre"),
                        null, null, "Estudiante"
                );
                calificaciones.add(new Calificacion(
                        rs.getInt("id"),
                        estudiante,
                        rs.getString("materia"),
                        rs.getDouble("nota")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calificaciones;
    }

    // Nuevo método: Obtener todas las calificaciones (sin filtro)
    public List<Calificacion> obtenerCalificaciones() {
        List<Calificacion> calificaciones = new ArrayList<>();
        String query = "SELECT c.id, c.materia, c.nota, u.id AS estudiante_id, u.nombre AS estudiante_nombre " +
                "FROM calificaciones c JOIN usuarios u ON c.estudiante_id = u.id";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Usuario estudiante = new Usuario(
                        rs.getInt("estudiante_id"),
                        rs.getString("estudiante_nombre"),
                        null, null, "Estudiante"
                );
                calificaciones.add(new Calificacion(
                        rs.getInt("id"),
                        estudiante,
                        rs.getString("materia"),
                        rs.getDouble("nota")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calificaciones;
    }

    public boolean registrarCalificacion(int estudianteId, String materia, double nota, String tipoUsuario) {
        if (!tipoUsuario.equals("Profesor") && !tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo Profesores y SuperUsuario pueden registrar calificaciones.");
            return false;
        }
        String query = "INSERT INTO calificaciones (estudiante_id, materia, nota) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, estudianteId);
            ps.setString(2, materia);
            ps.setDouble(3, nota);
            int filasInsertadas = ps.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCalificacion(int idCalificacion, String materia, double nuevaNota, String tipoUsuario) {
        if (!tipoUsuario.equals("Profesor") && !tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo Profesores y SuperUsuario pueden actualizar calificaciones.");
            return false;
        }
        String sql = "UPDATE calificaciones SET materia = ?, nota = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, materia);
            stmt.setDouble(2, nuevaNota);
            stmt.setInt(3, idCalificacion);
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminarCalificacion(int idCalificacion, String tipoUsuario) {
        if (!tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede eliminar calificaciones.");
            return false;
        }
        String sql = "DELETE FROM calificaciones WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCalificacion);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}





