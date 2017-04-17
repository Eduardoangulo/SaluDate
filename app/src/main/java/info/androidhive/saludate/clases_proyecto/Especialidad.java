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

public class Especialidad implements Parcelable, Serializable{
    private int id;
    private int area;
    private String especialidad_nombre;
    private String espeialidad_descripcion;

    public int getId() {
        return id;
    }

    public int getArea() {
        return area;
    }

    public String getEspecialidad_nombre() {
        return especialidad_nombre;
    }

    public String getEspeialidad_descripcion() {
        return espeialidad_descripcion;
    }

    public static ArrayList<Especialidad> obtenerEspecialidades(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Especialidad>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(especialidad_nombre);
        dest.writeString(espeialidad_descripcion);
        dest.writeInt(area);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Especialidad> CREATOR = new Parcelable.Creator<Especialidad>() {

        @Override
        public Especialidad createFromParcel(Parcel in) {
            return new Especialidad(in);
        }

        @Override
        public Especialidad[] newArray(int size) {
            return new Especialidad[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Especialidad(Parcel parcel) {
        //mData = in.readInt();
        id = parcel.readInt();
        especialidad_nombre = parcel.readString();
        espeialidad_descripcion = parcel.readString();
        area = parcel.readInt();
    }
}
