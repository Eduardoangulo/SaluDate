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
 * Created by Usuario on 18/11/2016.
 */

public class PacienteAdapter extends BaseAdapter {
    Context context;
    ArrayList<Paciente> pacienteArrayList;

    public PacienteAdapter(Context context, ArrayList<Paciente> pacienteArrayList){
        this.context = context;
        this.pacienteArrayList = pacienteArrayList;
    }


    @Override
    public int getCount() {
        return this.pacienteArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.pacienteArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.informacion_paciente, parent, false);

        TextView nombre = (TextView) view.findViewById(R.id.nombreUsuario);
        TextView dni = (TextView) view.findViewById(R.id.nroDNI);
        TextView genero = (TextView) view.findViewById(R.id.genero);
        TextView fechaNac = (TextView) view.findViewById(R.id.fechaNac);
        TextView telefono = (TextView) view.findViewById(R.id.movil);



        Paciente paciente = this.pacienteArrayList.get(position);

        if(paciente != null){
            nombre.setText(paciente.getPaciente_nombre()+" "+paciente.getPaciente_apellido_paterno()+" "+paciente.getPaciente_apellido_materno());
            dni.setText(paciente.getPaciente_dni());
            genero.setText(paciente.getPaciente_genero());
            fechaNac.setText(paciente.getPaciente_fecha_nacimiento());
            telefono.setText(paciente.getPaciente_telefono());

        }

        return view;
    }
}
