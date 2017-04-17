package info.androidhive.saludate.clases_proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;

/**
 * Created by Usuario on 19/11/2016.
 */

public class DoctorAdapter extends BaseAdapter{

    Context context;
    ArrayList<Doctor> doctorArrayList;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctorArrayList){
        this.context = context;
        this.doctorArrayList = doctorArrayList;
    }


    @Override
    public int getCount() {
        return this.doctorArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.doctorArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_doctor, parent, false);

        TextView nombre = (TextView) view.findViewById(R.id.textView5);
        TextView apPaterno = (TextView) view.findViewById(R.id.textView6);
        TextView apMaterno = (TextView) view.findViewById(R.id.textView7);

        Doctor doctor = this.doctorArrayList.get(position);

        if(doctor != null){
            nombre.setText("" + doctor.getDoctor_nombre());
            apPaterno.setText("" + doctor.getDoctor_apellido_paterno());
            apMaterno.setText("" + doctor.getDoctor_apellido_materno());
        }

        return view;
    }

}
