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

public class Horario implements Parcelable, Serializable{

    private int id;
    private String hora_inicio;
    private String hora_fin;

    public int getId() {
        return id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public static ArrayList<Horario> obtenerHorarios(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Horario>>(){}.getType();
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
        dest.writeString(hora_inicio);
        dest.writeString(hora_fin);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Horario> CREATOR = new Parcelable.Creator<Horario>() {

        @Override
        public Horario createFromParcel(Parcel in) {
            return new Horario(in);
        }

        @Override
        public Horario[] newArray(int size) {
            return new Horario[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Horario(Parcel parcel) {
        //mData = in.readInt();
        id = parcel.readInt();
        hora_inicio = parcel.readString();
        hora_fin = parcel.readString();
    }
}
