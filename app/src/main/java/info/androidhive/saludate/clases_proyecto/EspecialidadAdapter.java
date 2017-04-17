package info.androidhive.saludate.clases_proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;

import static info.androidhive.materialtabs.R.drawable.doctor;

/**
 * Created by Usuario on 19/11/2016.
 */

public class EspecialidadAdapter extends BaseAdapter {

    Context context;
    ArrayList<Especialidad> especialidadArrayList;

    public EspecialidadAdapter(Context context, ArrayList<Especialidad> especialidadArrayList){
        this.context = context;
        this.especialidadArrayList = especialidadArrayList;
    }


    @Override
    public int getCount() {
        return this.especialidadArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.especialidadArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_especialidad, parent, false);

        TextView id = (TextView) view.findViewById(R.id.textView5);
        TextView nombre = (TextView) view.findViewById(R.id.textView6);
        TextView descripcion = (TextView) view.findViewById(R.id.textView7);

        Especialidad especialidad = this.especialidadArrayList.get(position);

        if(especialidad != null){
            id.setText("ID: " + especialidad.getId());
            nombre.setText("" + especialidad.getEspecialidad_nombre());
            descripcion.setText("" + especialidad.getEspeialidad_descripcion());
        }

        return view;
    }
}
