package info.androidhive.saludate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.Doctor;
import info.androidhive.saludate.clases_proyecto.DoctorAdapter;
import info.androidhive.saludate.clases_proyecto.Especialidad;
import info.androidhive.saludate.clases_proyecto.EspecialidadAdapter;
import info.androidhive.saludate.clases_proyecto.EspecialidadDoctor;
import info.androidhive.saludate.clases_proyecto.HttpRequest;

import static android.R.attr.id;
import static info.androidhive.materialtabs.R.array.especialidades;
import static info.androidhive.materialtabs.R.id.listViewPaciente;
import static java.security.AccessController.getContext;

/**
 * Created by Usuario on 19/11/2016.
 */

public class ListaDoctores extends Activity {

    Spinner spinnerParametro;
    EditText dato;
    ListView listViewPaciente;

    int idEspec;

    int idPac;

    ArrayList<Doctor> doctores;
    ArrayList<Doctor> doctores_aux;

    ArrayList<EspecialidadDoctor> especDoctores;
    ArrayList<EspecialidadDoctor> especDoctores_aux;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_doctors);

        this.spinnerParametro = (Spinner) findViewById(R.id.spinnerPacienteParametros);
        this.dato = (EditText) findViewById(R.id.editTextDato);
        this.listViewPaciente = (ListView) findViewById(R.id.listViewPaciente);

        Bundle bundle = getIntent().getExtras();
        idEspec = bundle.getInt("infoEspec");
        idPac = bundle.getInt("idPac");


        Log.i("Especialidaaaaad  ","Id Espeeeeeeeccc:  "+ idEspec);

        new getEspecialidadDoctor().execute("http://35.164.146.218:8000/rest/especialidadDoctor/");
        new getDoctores().execute("http://35.164.146.218:8000/rest/doctors/");


        ImageView fragment1_buscar = (ImageView) findViewById(R.id.doctor_buscar);
        fragment1_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean encontrado = false;
                String busqueda = dato.getText().toString();
                ArrayList<Doctor> espe = new ArrayList<>();
                for(int i = 0; i<doctores_aux.size();i++)
                    if(busqueda.equals(doctores_aux.get(i).getDoctor_apellido_paterno())||busqueda.equals(doctores_aux.get(i).getDoctor_nombre())||busqueda.equals(doctores_aux.get(i).getDoctor_apellido_materno())){
                        espe.add(doctores_aux.get(i));
                        encontrado = true;
                    }
                DoctorAdapter adapter = new DoctorAdapter(ListaDoctores.this, espe);
                if(encontrado){
                    LinearLayout layout = (LinearLayout)findViewById(R.id.emptyElement);
                    layout.setVisibility(View.INVISIBLE);
                    listViewPaciente.setAdapter(adapter);
                    Toast.makeText(ListaDoctores.this,"Resultado encontrado",Toast.LENGTH_LONG).show();
                }
                else{
                    espe.clear();
                    adapter = new DoctorAdapter(ListaDoctores.this, espe);
                    LinearLayout layout = (LinearLayout)findViewById(R.id.emptyElement);
                    layout.setVisibility(View.VISIBLE);
                    listViewPaciente.setAdapter(adapter);
                    Toast.makeText(ListaDoctores.this,"No se encontraron resultados",Toast.LENGTH_LONG).show();
                } if(busqueda.isEmpty()){
                    LinearLayout layout = (LinearLayout)findViewById(R.id.emptyElement);
                    layout.setVisibility(View.INVISIBLE);
                    DoctorAdapter adapter2 = new DoctorAdapter(ListaDoctores.this, doctores_aux);
                    listViewPaciente.setAdapter(adapter2);
                }
            }
        });

    }

    private class getDoctores extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params){
            try{
                return HttpRequest.get(params[0]).accept("application/json").body();
            }catch(Exception e){
                return "";
            }

        }

        public void onPostExecute(String result){
            if(result.isEmpty()){
                //Toast.makeText(this,"No se generaron resultados",Toast.LENGTH_LONG).show();
            }else{
                doctores = Doctor.obtenerDoctores(result);
                doctores_aux = new ArrayList<>();

                for(int i=0; i< especDoctores.size();i++){
                    if(especDoctores.get(i).getEspecialidad() == idEspec){
                        Log.i("Especialidaaad Dooocc ","Id Doctooorr:  "+ especDoctores.get(i).getDoctor());
                        for(int j=0; j< doctores.size();j++){
                            Log.i("Doctoooor","Id Doctooorr:  "+ doctores.get(j).getId());
                            if(doctores.get(j).getId() == especDoctores.get(i).getDoctor()){
                                Log.i("Doctoooor ","Dooocc Namee:  "+ doctores.get(j).getDoctor_nombre());
                                doctores_aux.add(doctores.get(j));
                            }
                        }
                    }
                }


                if (doctores_aux.size() != 0){
                    DoctorAdapter adapter = new DoctorAdapter(ListaDoctores.this, doctores_aux);
                    listViewPaciente.setAdapter(adapter);

                    listViewPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            Intent intent = new Intent(ListaDoctores.this, ListaHorarios.class);
                            intent.putExtra("infoDoctor", ((Doctor) parent.getAdapter().getItem(position)).getId());
                            intent.putExtra("infoEspec", idEspec);
                            intent.putExtra("idPac",idPac);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    private class getEspecialidadDoctor extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params){
            try{
                return HttpRequest.get(params[0]).accept("application/json").body();
            }catch(Exception e){
                return "";
            }

        }

        public void onPostExecute(String result){
            if(result.isEmpty()){
                //Toast.makeText(.this,"No se generaron resultados",Toast.LENGTH_LONG).show();
            }else{
                especDoctores = EspecialidadDoctor.obtenerEspecialidadDoctores(result);
                especDoctores_aux = new ArrayList<>();

            }
        }
    }


}
