package eduplan.modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contraseña;
    private String tipo; // "Estudiante", "Profesor" o "SuperUsuario"

    // **Constructor vacío**
    public Usuario() {}

    // **Constructor con parámetros**
    public Usuario(int id, String nombre, String correo, String contraseña, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }

    // **Getters y Setters**
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // **Representación en listas y combos**
    @Override
    public String toString() {
        return nombre + " (" + tipo + ")";
    }
}



