package info.androidhive.saludate.clases_proyecto;

/**
 * Created by Usuario on 19/11/2016.
 */

import android.os.Parcelable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Cita{
    private int id_cita;
    private int horariodoctor;
    private int especialidaddoctor;
    private int paciente;
    private String cita_descripcion;
    private String notas_adicionales;

    public Cita(){}

    public int getHorariodoctor() {
        return horariodoctor;
    }

    public void setHorariodoctor(int horariodoctor) {
        this.horariodoctor = horariodoctor;
    }

    public int getEspecialidaddoctor() {
        return especialidaddoctor;
    }

    public void setEspecialidaddoctor(int especialidaddoctor) {
        this.especialidaddoctor = especialidaddoctor;
    }

    public int getPaciente() {
        return paciente;
    }

    public void setPaciente(int paciente) {
        this.paciente = paciente;
    }

    public String getCita_descripcion() {
        return cita_descripcion;
    }

    public void setCita_descripcion(String cita_descripcion) {
        this.cita_descripcion = cita_descripcion;
    }

    public String getNotas_adicionales() {
        return notas_adicionales;
    }

    public void setNotas_adicionales(String notas_adicionales) {
        this.notas_adicionales = notas_adicionales;
    }

    public static ArrayList<Cita> obtenerCitas(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Cita>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public int getId_cita() {
        return id_cita;
    }

    public void setId_cita(int id_cita) {
        this.id_cita = id_cita;
    }
}
