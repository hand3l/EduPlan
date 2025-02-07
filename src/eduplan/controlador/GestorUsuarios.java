package eduplan.controlador;

import eduplan.modelo.DatabaseConnection;
import eduplan.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {

    // **Registrar un nuevo usuario en la base de datos**
    public boolean registrarUsuario(String nombre, String correo, String contraseña, String tipo) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, tipo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, contraseña);
            stmt.setString(4, tipo);

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0; // Retorna `true` si se insertó correctamente

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // **Autenticar usuario en el inicio de sesión**
    public Usuario autenticarUsuario(String correo, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            rs.getString("tipo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna `null` si no se encuentra usuario
    }

    // **Obtener lista de usuarios registrados**
    public List<Usuario> obtenerUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, tipo FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        null, // No traer contraseña por seguridad
                        rs.getString("tipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    // **Eliminar usuario (solo permitido para el SuperUsuario)**
    public boolean eliminarUsuario(int idUsuario, String tipoUsuario) {
        if (!tipoUsuario.equals("SuperUsuario")) {
            System.out.println("Acceso denegado. Solo el SuperUsuario puede eliminar usuarios.");
            return false;
        }

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0; // Retorna `true` si se eliminó correctamente

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}




