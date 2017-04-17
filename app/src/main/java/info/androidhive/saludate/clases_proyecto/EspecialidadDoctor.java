package info.androidhive.saludate.clases_proyecto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Usuario on 19/11/2016.
 */

public class EspecialidadDoctor implements Parcelable, Serializable {

    private int id;
    private int especialidad;
    private int doctor;

    public int getEspecialidad() {
        return especialidad;
    }

    public int getDoctor() {
        return doctor;
    }

    public static ArrayList<EspecialidadDoctor> obtenerEspecialidadDoctores(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<EspecialidadDoctor>>(){}.getType();
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
        dest.writeInt(especialidad);
        dest.writeInt(doctor);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<EspecialidadDoctor> CREATOR = new Parcelable.Creator<EspecialidadDoctor>() {

        @Override
        public EspecialidadDoctor createFromParcel(Parcel in) {
            return new EspecialidadDoctor(in);
        }

        @Override
        public EspecialidadDoctor[] newArray(int size) {
            return new EspecialidadDoctor[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private EspecialidadDoctor(Parcel parcel) {
        //mData = in.readInt();
        id = parcel.readInt();
        especialidad = parcel.readInt();
        doctor = parcel.readInt();
    }

    public int getId() {
        return id;
    }
}
