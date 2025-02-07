package eduplan.controlador;

import eduplan.modelo.DatabaseConnection;
import eduplan.modelo.Horario;
import eduplan.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorHorarios {

    // Método para registrar un nuevo horario
    public boolean registrarHorario(int profesorId, String materia, String dia, String hora) {
        String sql = "INSERT INTO horarios (profesor_id, materia, dia, hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profesorId);
            stmt.setString(2, materia);
            stmt.setString(3, dia);
            stmt.setString(4, hora);
            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener la lista de horarios
    public List<Horario> obtenerHorarios() {
        List<Horario> listaHorarios = new ArrayList<>();
        String sql = "SELECT h.id, h.materia, h.dia, h.hora, u.id AS profesor_id, u.nombre AS profesor_nombre " +
                "FROM horarios h JOIN usuarios u ON h.profesor_id = u.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario profesor = new Usuario(
                        rs.getInt("profesor_id"),
                        rs.getString("profesor_nombre"),
                        null, null, "Profesor"
                );
                Horario horario = new Horario(
                        rs.getInt("id"),
                        profesor,
                        rs.getString("materia"),
                        rs.getString("dia"),
                        rs.getString("hora")
                );
                listaHorarios.add(horario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaHorarios;
    }

    // Método para actualizar un horario (solo para SuperUsuario, la validación se realiza en el controlador)
    public boolean actualizarHorario(int idHorario, String materia, String dia, String hora, String tipoUsuario) {
        if (!tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede actualizar horarios.");
            return false;
        }
        String sql = "UPDATE horarios SET materia = ?, dia = ?, hora = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, materia);
            stmt.setString(2, dia);
            stmt.setString(3, hora);
            stmt.setInt(4, idHorario);
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un horario (solo para SuperUsuario)
    public boolean eliminarHorario(int idHorario, String tipoUsuario) {
        if (!tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede eliminar horarios.");
            return false;
        }
        String sql = "DELETE FROM horarios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idHorario);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // MÉTODO NUEVO: Obtener la lista de profesores
    public List<Usuario> obtenerProfesores() {
        List<Usuario> profesores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM usuarios WHERE tipo = 'Profesor'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                profesores.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        null, null, "Profesor"
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profesores;
    }
}



