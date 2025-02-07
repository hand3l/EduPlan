package eduplan.modelo;

public class Horario {
    private int id;
    private Usuario profesor;
    private String materia;
    private String dia;
    private String hora;

    // **Constructor**
    public Horario(int id, Usuario profesor, String materia, String dia, String hora) {
        this.id = id;
        this.profesor = profesor;
        this.materia = materia;
        this.dia = dia;
        this.hora = hora;
    }

    // **Getters y Setters**
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    // **Método toString para visualización**
    @Override
    public String toString() {
        return materia + " - " + dia + " " + hora + " (Prof. " + profesor.getNombre() + ")";
    }
}

