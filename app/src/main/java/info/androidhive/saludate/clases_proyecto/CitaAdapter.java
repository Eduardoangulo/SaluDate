package info.androidhive.saludate.clases_proyecto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.activity.MainActivity;

import static android.R.attr.id;
import static info.androidhive.materialtabs.R.drawable.especialidad;

/**
 * Created by Usuario on 19/11/2016.
 */

public class CitaAdapter extends BaseAdapter {

    String mesCita;
    String mesCitac;
    ViewCita cita;
    Context context;
    String sdia;
    String subcadenadia;
    String sfecha;
    String subcadenafecha;
    String sdiac;
    String subcadenadiac;
    String sfechac;
    String subcadenafechac;
    public ArrayList<ViewCita> citaArrayList;

    public CitaAdapter(Context context, ArrayList<ViewCita> citaArrayList){
        this.context = context;
        this.citaArrayList = citaArrayList;
    }


    @Override
    public int getCount() {
        return this.citaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.citaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.adapter_list, parent, false);

        TextView diacita= (TextView) view.findViewById(R.id.dia);
        TextView mescita= (TextView) view.findViewById(R.id.mes);
        TextView especialidad = (TextView) view.findViewById(R.id.text_especialidad);
        TextView doctor = (TextView) view.findViewById(R.id.text_doctor);
        TextView horario = (TextView) view.findViewById(R.id.text_hora);
        //TextView fecha = (TextView) view.findViewById(R.id.text_fecha);
        ImageView descrip =(ImageView) view.findViewById(R.id.descripcion);

        cita = this.citaArrayList.get(position);



            sdia= cita.getFecha();
            subcadenadia=sdia.substring(8,10);
            sfecha= cita.getFecha();
            subcadenafecha=sfecha.substring(5,7);

            if(subcadenafecha.equals("01")){mesCita="ENE";}if(subcadenafecha.equals("02")){mesCita="FEB";
            }if(subcadenafecha.equals("03")){mesCita="MAR";}if(subcadenafecha.equals("04")){mesCita="ABR";
            }if(subcadenafecha.equals("05")){mesCita="MAY";}if(subcadenafecha.equals("06")){mesCita="JUN";
            }if(subcadenafecha.equals("07")){mesCita="JUL";}if(subcadenafecha.equals("08")){mesCita="AGT";
            }if(subcadenafecha.equals("09")){mesCita="SEP";}if(subcadenafecha.equals("10")){mesCita="OCT";
            }if(subcadenafecha.equals("11")){mesCita="NOV";}if(subcadenafecha.equals("12")){mesCita="DIC";}

            diacita.setText(subcadenadia);
            mescita.setText(mesCita);
            especialidad.setText(cita.getEspecialidad());
            doctor.setText( cita.getDoctor());
            horario.setText(cita.getHorario());
            //fecha.setText("Fecha: " + cita.getFecha());

        descrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Usuariooooo  ","CLICK:  "+"si");
                mostrarDescripcion(position).show();

            }
        });
        /* dialogo para mostrarDescripcion */


        return view;
    }
    /* dialogo para mostrarDescripcion */
    private Dialog mostrarDescripcion(int pos){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.descripcion_cita, null);
        builder.setCancelable(false);
        builder.setView(v);
        TextView diact = (TextView) v.findViewById(R.id.diacita);
        TextView mesct =(TextView) v.findViewById(R.id.mescita);
        TextView doct =(TextView) v.findViewById(R.id.doctor_cita);
        TextView notac =(TextView) v.findViewById(R.id.cita_nota);
        TextView descipcionc =(TextView) v.findViewById(R.id.descripcion_cita);

        cita = this.citaArrayList.get(pos);
        sdiac= cita.getFecha();
        subcadenadiac=sdiac.substring(8,10);
        sfechac= cita.getFecha();
        subcadenafechac=sfechac.substring(5,7);

        if(subcadenafechac.equals("01")){mesCitac="ENERO";}if(subcadenafechac.equals("02")){mesCitac="FEBRERO";
        }if(subcadenafechac.equals("03")){mesCitac="MARZO";}if(subcadenafechac.equals("04")){mesCitac="ABRIL";
        }if(subcadenafechac.equals("05")){mesCitac="MAYO";}if(subcadenafechac.equals("06")){mesCitac="JUNIO";
        }if(subcadenafechac.equals("07")){mesCitac="JULIO";}if(subcadenafechac.equals("08")){mesCitac="AGOSTO";
        }if(subcadenafechac.equals("09")){mesCitac="SEPTIEMBRE";}if(subcadenafechac.equals("10")){mesCitac="OCTUBRE";
        }if(subcadenafechac.equals("11")){mesCitac="NOVIEMBRE";}if(subcadenafechac.equals("12")){mesCitac="DICIEMBRE";}
        diact.setText(subcadenadiac);
        mesct.setText(mesCitac);;
        doct.setText(cita.getDoctor());
        descipcionc.setText("Descripcion : "+cita.getDescripcion());
        notac.setText("Notas : "+cita.getNotasAd());


        builder.setPositiveButton("Aceptar",    new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();

            }
        });


        return builder.create();
    }


}
