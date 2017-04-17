package info.androidhive.saludate.clases_proyecto;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by Usuario on 19/11/2016.
 */

public class ViewCita implements Parcelable, Serializable {

    private String especialidad;
    private String doctor;
    private String horario;
    private String fecha;
    private String notasAd;
    private String descripcion;

    public ViewCita(){

    }
    public String getNotasAd() {
        return notasAd;
    }

    public void setNotasAd(String notasAd) {
        this.notasAd = notasAd;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(especialidad);
        dest.writeString(doctor);
        dest.writeString(horario);
        dest.writeString(fecha);
        dest.writeString(notasAd);
        dest.writeString(descripcion);

        //out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ViewCita> CREATOR = new Parcelable.Creator<ViewCita>() {

        @Override
        public ViewCita createFromParcel(Parcel in) {
            return new ViewCita(in);
        }

        @Override
        public ViewCita[] newArray(int size) {
            return new ViewCita[0];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private ViewCita(Parcel parcel) {
        //mData = in.readInt();
        especialidad = parcel.readString();
        doctor = parcel.readString();
        horario = parcel.readString();
        fecha = parcel.readString();
        notasAd = parcel.readString();
        descripcion = parcel.readString();
    }


}