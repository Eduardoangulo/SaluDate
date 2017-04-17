package info.androidhive.saludate.clases_proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;

import static info.androidhive.materialtabs.R.drawable.especialidad;

/**
 * Created by Usuario on 19/11/2016.
 */

public class HorarioAdapter extends BaseAdapter {

    Context context;
    ArrayList<ViewHorario> horarioArrayList;

    public HorarioAdapter(Context context, ArrayList<ViewHorario> horarioArrayList){
        this.context = context;
        this.horarioArrayList = horarioArrayList;
    }


    @Override
    public int getCount() {
        return this.horarioArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.horarioArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_horario, parent, false);

        TextView id = (TextView) view.findViewById(R.id.textView5);
        TextView hora_inicio = (TextView) view.findViewById(R.id.textView6);
        TextView hora_fin = (TextView) view.findViewById(R.id.textView7);
        TextView fecha = (TextView)view.findViewById(R.id.fechaTextView);

        ViewHorario horario = this.horarioArrayList.get(position);

        if(horario != null){
            id.setText("ID: " + horario.getId());
            hora_inicio.setText("" + horario.getHora_inicio());
            hora_fin.setText("" + horario.getHora_fin());
            fecha.setText(""+horario.getFecha());
        }

        return view;
    }
}