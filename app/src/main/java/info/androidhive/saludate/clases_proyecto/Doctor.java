package info.androidhive.saludate.clases_proyecto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Usuario on 18/11/2016.
 */

public class Doctor implements Parcelable, Serializable{

    private String doctor_nombre;
    private String doctor_apellido_materno;
    private String doctor_apellido_paterno;
    private String doctor_dni;
    private String doctor_domicilio;
    private String doctor_fecha_nacimiento;
    private String doctor_genero;
    private String doctor_lugar_nacimiento;
    private String doctor_telefono;
    private String doctor_correo;
    private int id;

    public String getDoctor_nombre() {
        return doctor_nombre;
    }

    public String getDoctor_apellido_materno() {
        return doctor_apellido_materno;
    }

    public String getDoctor_apellido_paterno() {
        return doctor_apellido_paterno;
    }

    public String getDoctor_dni() {
        return doctor_dni;
    }

    public String getDoctor_domicilio() {
        return doctor_domicilio;
    }

    public String getDoctor_fecha_nacimiento() {
        return doctor_fecha_nacimiento;
    }

    public String getDoctor_genero() {
        return doctor_genero;
    }

    public String getDoctor_lugar_nacimiento() {
        return doctor_lugar_nacimiento;
    }

    public String getDoctor_telefono() {
        return doctor_telefono;
    }

    public String getDoctor_correo() {
        return doctor_correo;
    }

    public int getId() {
        return id;
    }

    public static ArrayList<Doctor> obtenerDoctores(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Doctor>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(doctor_nombre);
        dest.writeString(doctor_apellido_materno);
        dest.writeString(doctor_apellido_paterno);
        dest.writeString(doctor_dni);
        dest.writeString(doctor_domicilio);
        dest.writeString(doctor_fecha_nacimiento);
        dest.writeString(doctor_genero);
        dest.writeString(doctor_lugar_nacimiento);
        dest.writeString(doctor_telefono);
        dest.writeString(doctor_correo);
        dest.writeInt(id);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {

        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Doctor(Parcel parcel) {
        //mData = in.readInt();
        doctor_nombre = parcel.readString();
        doctor_apellido_materno = parcel.readString();
        doctor_apellido_paterno = parcel.readString();
        doctor_dni = parcel.readString();
        doctor_domicilio = parcel.readString();
        doctor_fecha_nacimiento = parcel.readString();
        doctor_genero = parcel.readString();
        doctor_lugar_nacimiento = parcel.readString();
        doctor_telefono = parcel.readString();
        doctor_correo = parcel.readString();
        id = parcel.readInt();
    }
}
