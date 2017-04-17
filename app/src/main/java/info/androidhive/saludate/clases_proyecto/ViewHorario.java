package info.androidhive.saludate.clases_proyecto;

/**
 * Created by Usuario on 27/11/2016.
 */

public class ViewHorario {

    private int id;
    private String hora_inicio;
    private String hora_fin;
    private String fecha;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}