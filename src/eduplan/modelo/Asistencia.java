package eduplan.modelo;

import java.sql.Date;

public class Asistencia {
    private int id;
    private Usuario estudiante;
    private Date fecha;
    private boolean asistio;

    // Constructor completo
    public Asistencia(int id, Usuario estudiante, Date fecha, boolean asistio) {
        this.id = id;
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.asistio = asistio;
    }

    // Constructor sin id (para inserciones, el id se asigna automáticamente en la BD)
    public Asistencia(Usuario estudiante, Date fecha, boolean asistio) {
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.asistio = asistio;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    @Override
    public String toString() {
        return "Asistencia{" +
                "id=" + id +
                ", estudiante=" + (estudiante != null ? estudiante.getNombre() : "N/A") +
                ", fecha=" + fecha +
                ", asistio=" + (asistio ? "Sí" : "No") +
                '}';
    }
}
