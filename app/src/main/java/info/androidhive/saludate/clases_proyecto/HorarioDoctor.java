package info.androidhive.saludate.clases_proyecto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static info.androidhive.materialtabs.R.drawable.especialidad;

/**
 * Created by Usuario on 19/11/2016.
 */

public class HorarioDoctor implements Parcelable, Serializable{

    private int id;
    private int horario;
    private int doctor;
    private String fecha_disponibilidad;


    public int getHorario() {
        return horario;
    }

    public int getDoctor() {
        return doctor;
    }

    public String getFecha_disponibilidad() {
        return fecha_disponibilidad;
    }

    public static ArrayList<HorarioDoctor> obtenerHorarioDoctores(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<HorarioDoctor>>(){}.getType();
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
        dest.writeInt(horario);
        dest.writeInt(doctor);
        dest.writeString(fecha_disponibilidad);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<HorarioDoctor> CREATOR = new Parcelable.Creator<HorarioDoctor>() {

        @Override
        public HorarioDoctor createFromParcel(Parcel in) {
            return new HorarioDoctor(in);
        }

        @Override
        public HorarioDoctor[] newArray(int size) {
            return new HorarioDoctor[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private HorarioDoctor(Parcel parcel) {
        //mData = in.readInt();
        id = parcel.readInt();
        horario = parcel.readInt();
        doctor = parcel.readInt();
        fecha_disponibilidad = parcel.readString();

    }

    public int getId() {
        return id;
    }
}
