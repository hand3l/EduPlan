package eduplan.controlador;

import eduplan.modelo.DatabaseConnection;
import eduplan.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuarios {

    // **Registrar un nuevo usuario**
    public boolean registrarUsuario(String nombre, String correo, String contraseña, String tipoUsuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, tipo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, contraseña); // Se recomienda encriptar en implementación real
            stmt.setString(4, tipoUsuario);

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // **Actualizar un usuario (solo SuperUsuario)**
    public boolean actualizarUsuario(int id, String nombre, String correo, String contraseña, String tipoUsuario, String usuarioActual) {
        if (!usuarioActual.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede actualizar usuarios.");
            return false;
        }

        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, contraseña = ?, tipo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, contraseña);
            stmt.setString(4, tipoUsuario);
            stmt.setInt(5, id);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // **Eliminar un usuario (solo SuperUsuario)**
    public boolean eliminarUsuario(int id, String usuarioActual) {
        if (!usuarioActual.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede eliminar usuarios.");
            return false;
        }

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // **Obtener todos los usuarios**
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, tipo FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        "", // No se devuelve la contraseña por seguridad
                        rs.getString("tipo")
                );
                listaUsuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }
}




