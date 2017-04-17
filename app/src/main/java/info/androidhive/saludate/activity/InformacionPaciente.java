package info.androidhive.saludate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.Paciente;

/**
 * Created by Usuario on 18/11/2016.
 */

public class InformacionPaciente extends AppCompatActivity {

    TextView txtNombre;
    TextView txtApPaterno;
    TextView txtApMaterno;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_paciente);

        inicializar();
    }

    public void inicializar(){

        Paciente paciente = (Paciente) getIntent().getSerializableExtra("EXTRA_PATIENT_INFO");
        TextView nombre = (TextView) findViewById(R.id.nombreUsuario);
        TextView dni = (TextView) findViewById(R.id.nroDNI);
        TextView genero = (TextView) findViewById(R.id.genero);
        TextView fechaNac = (TextView) findViewById(R.id.fechaNac);
        TextView telefono = (TextView) findViewById(R.id.movil);


        nombre.setText(paciente.getPaciente_nombre()+" "+paciente.getPaciente_apellido_paterno()+" "+paciente.getPaciente_apellido_materno());
        dni.setText(paciente.getPaciente_dni());
        genero.setText(paciente.getPaciente_genero());
        fechaNac.setText(paciente.getPaciente_fecha_nacimiento());
        telefono.setText(paciente.getPaciente_telefono());

    }


}
