package info.androidhive.saludate.clases_proyecto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Usuario on 17/11/2016.
 */

public class Paciente implements Parcelable, Serializable{

    private int mData;

    private String paciente_nombre;
    private String paciente_apellido_materno;
    private String paciente_apellido_paterno;
    private String paciente_dni;
    private String paciente_domicilio;
    private String paciente_fecha_nacimiento;
    private String paciente_genero;
    private String paciente_lugar_nacimiento;
    private String paciente_estado_civil;
    private String paciente_telefono;
    private String paciente_ocupacion;
    private String paciente_contras_ingreso;
    private int id;

    public String getPaciente_nombre() {
        return paciente_nombre;
    }

    public void setPaciente_nombre(String paciente_nombre) {
        this.paciente_nombre = paciente_nombre;
    }

    public String getPaciente_apellido_materno() {
        return paciente_apellido_materno;
    }

    public void setPaciente_apellido_materno(String paciente_apellido_materno) {
        this.paciente_apellido_materno = paciente_apellido_materno;
    }

    public String getPaciente_apellido_paterno() {
        return paciente_apellido_paterno;
    }

    public void setPaciente_apellido_paterno(String paciente_apellido_paterno) {
        this.paciente_apellido_paterno = paciente_apellido_paterno;
    }

    public String getPaciente_dni() {
        return paciente_dni;
    }

    public void setPaciente_dni(String paciente_dni) {
        this.paciente_dni = paciente_dni;
    }

    public String getPaciente_domicilio() {
        return paciente_domicilio;
    }

    public void setPaciente_domicilio(String paciente_domicilio) {
        this.paciente_domicilio = paciente_domicilio;
    }

    public String getPaciente_fecha_nacimiento() {
        return paciente_fecha_nacimiento;
    }

    public void setPaciente_fecha_nacimiento(String paciente_fecha_nacimiento) {
        this.paciente_fecha_nacimiento = paciente_fecha_nacimiento;
    }

    public String getPaciente_genero() {
        return paciente_genero;
    }

    public void setPaciente_genero(String paciente_genero) {
        this.paciente_genero = paciente_genero;
    }

    public String getPaciente_lugar_nacimiento() {
        return paciente_lugar_nacimiento;
    }

    public void setPaciente_lugar_nacimiento(String paciente_lugar_nacimiento) {
        this.paciente_lugar_nacimiento = paciente_lugar_nacimiento;
    }

    public String getPaciente_estado_civil() {
        return paciente_estado_civil;
    }

    public void setPaciente_estado_civil(String paciente_estado_civil) {
        this.paciente_estado_civil = paciente_estado_civil;
    }

    public String getPaciente_telefono() {
        return paciente_telefono;
    }

    public void setPaciente_telefono(String paciente_telefono) {
        this.paciente_telefono = paciente_telefono;
    }

    public String getPaciente_ocupacion() {
        return paciente_ocupacion;
    }

    public void setPaciente_ocupacion(String paciente_ocupacion) {
        this.paciente_ocupacion = paciente_ocupacion;
    }

    public static ArrayList<Paciente> obtenerPacientes(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Paciente>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public String getPaciente_contras_ingreso() {
        return paciente_contras_ingreso;
    }

    public void setPaciente_contras_ingreso(String paciente_contras_ingreso) {
        this.paciente_contras_ingreso = paciente_contras_ingreso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(paciente_nombre);
        dest.writeString(paciente_apellido_materno);
        dest.writeString(paciente_apellido_paterno);
        dest.writeString(paciente_dni);
        dest.writeString(paciente_domicilio);
        dest.writeString(paciente_fecha_nacimiento);
        dest.writeString(paciente_genero);
        dest.writeString(paciente_lugar_nacimiento);
        dest.writeString(paciente_estado_civil);
        dest.writeString(paciente_telefono);
        dest.writeString(paciente_ocupacion);
        dest.writeString(paciente_contras_ingreso);
        dest.writeInt(id);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Paciente> CREATOR = new Parcelable.Creator<Paciente>() {

        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Paciente(Parcel parcel) {
        //mData = in.readInt();
        paciente_nombre = parcel.readString();
        paciente_apellido_materno = parcel.readString();
        paciente_apellido_paterno = parcel.readString();
        paciente_dni = parcel.readString();
        paciente_domicilio = parcel.readString();
        paciente_fecha_nacimiento = parcel.readString();
        paciente_genero = parcel.readString();
        paciente_lugar_nacimiento = parcel.readString();
        paciente_estado_civil = parcel.readString();
        paciente_telefono = parcel.readString();
        paciente_ocupacion = parcel.readString();
        paciente_contras_ingreso = parcel.readString();
        id = parcel.readInt();
    }

    public int getId() {
        return id;
    }
}
