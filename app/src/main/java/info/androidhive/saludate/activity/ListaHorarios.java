package info.androidhive.saludate.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.Doctor;
import info.androidhive.saludate.clases_proyecto.DoctorAdapter;
import info.androidhive.saludate.clases_proyecto.Especialidad;
import info.androidhive.saludate.clases_proyecto.EspecialidadDoctor;
import info.androidhive.saludate.clases_proyecto.Horario;
import info.androidhive.saludate.clases_proyecto.HorarioAdapter;
import info.androidhive.saludate.clases_proyecto.HorarioDoctor;
import info.androidhive.saludate.clases_proyecto.HttpRequest;
import info.androidhive.saludate.clases_proyecto.ViewHorario;

import static android.R.attr.id;

/**
 * Created by Usuario on 19/11/2016.
 */

public class ListaHorarios extends Activity{

    Spinner spinnerParametro;
    EditText dato;
    ListView listViewPaciente;

    boolean band = true;

    int idDoc;

    int idPac;

    int idEsp;


    ArrayList<Horario> horarios;
    ArrayList<Horario> horarios_aux;

    ArrayList<ViewHorario> horariosTotal = new ArrayList<>();

    ArrayList<HorarioDoctor> horarioDoctores;
    ArrayList<HorarioDoctor> horarioDoctores_aux;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_horarios);

        this.spinnerParametro = (Spinner) findViewById(R.id.spinnerPacienteParametros);
        this.dato = (EditText) findViewById(R.id.editTextDato);
        this.listViewPaciente = (ListView) findViewById(R.id.listViewPaciente);

        //new ListaEspecialidades.getEspecialidades().execute("http://192.168.1.33:8000/rest/especialidad/");

        Bundle bundle = getIntent().getExtras();
        idDoc = bundle.getInt("infoDoctor");
        idPac = bundle.getInt("idPac");
        idEsp = bundle.getInt("infoEspec");

        Log.i("Especialidaaaaad  ","Id Espeeeeeeeccc:  "+ idDoc);

        new ListaHorarios.getHorariosDoctor().execute("http://35.164.146.218:8000/rest/horariosDoctor/");
        new ListaHorarios.getHorarios().execute("http://35.164.146.218:8000/rest/horarios/");

        ImageView fragment1_buscar = (ImageView) findViewById(R.id.horario_buscar);
        fragment1_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean encontrado = false;
                String busqueda = dato.getText().toString();
                ArrayList<Horario> espe = new ArrayList<>();

                for(int i = 0; i<horarios_aux.size();i++)
                    if(busqueda.equals(horarios_aux.get(i).getHora_inicio())){
                        espe.add(horarios_aux.get(i));
                        encontrado = true;
                    }
                HorarioAdapter adapter = new HorarioAdapter(ListaHorarios.this, horariosTotal);
                if(encontrado){
                    LinearLayout layout = (LinearLayout)findViewById(R.id.emptyElement);
                    layout.setVisibility(View.INVISIBLE);
                    listViewPaciente.setAdapter(adapter);
                    Toast.makeText(ListaHorarios.this,"Resultado encontrado",Toast.LENGTH_LONG).show();
                }
                else{
                    espe.clear();
                    adapter = new HorarioAdapter(ListaHorarios.this, horariosTotal);
                    LinearLayout layout = (LinearLayout)findViewById(R.id.emptyElement);
                    layout.setVisibility(View.VISIBLE);
                    listViewPaciente.setAdapter(adapter);
                    Toast.makeText(ListaHorarios.this,"No se encontraron resultados",Toast.LENGTH_LONG).show();
                } if(busqueda.isEmpty()){
                    LinearLayout layout = (LinearLayout)findViewById(R.id.emptyElement);
                    layout.setVisibility(View.INVISIBLE);
                    HorarioAdapter adapter2 = new HorarioAdapter(ListaHorarios.this, horariosTotal);
                    listViewPaciente.setAdapter(adapter2);
                }
            }
        });

    }

    private class getHorarios extends AsyncTask<String, Void, String> {
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
                horarios = Horario.obtenerHorarios(result);
                horarios_aux = new ArrayList<>();

                for(int i=0; i< horarioDoctores.size();i++){
                    Log.i("Doctoooor","Id Doctooorr:  "+ horarioDoctores.get(i).getDoctor());
                    if(horarioDoctores.get(i).getDoctor() == idDoc && band){
                        //band = false;
                        Log.i("Doctoooor","Id Doctooorr:  "+ horarioDoctores.get(i).getDoctor());
                        for(int j=0; j< horarios.size();j++){
                            Log.i("    Horarioooo","Id horarioooo:  "+ horarios.get(j).getId());
                            if(horarios.get(j).getId() == horarioDoctores.get(i).getHorario()){
                                Log.i("            Horariooo ","horariooo:  "+ horarios.get(j).getHora_inicio());
                                ViewHorario horarioDatos = new ViewHorario();
                                horarioDatos.setId(horarios.get(j).getId());
                                horarioDatos.setHora_inicio(horarios.get(j).getHora_inicio());
                                horarioDatos.setHora_fin(horarios.get(j).getHora_fin());
                                horarioDatos.setFecha(horarioDoctores.get(j).getFecha_disponibilidad());
                                horariosTotal.add(horarioDatos);
                            }
                        }

                    }
                }

                Log.i("Horariooooo ","Tamañoooooo:  "+ horarios_aux.size());
                if (horariosTotal.size() != 0){
                    HorarioAdapter adapter = new HorarioAdapter(ListaHorarios.this, horariosTotal);
                    listViewPaciente.setAdapter(adapter);

                    listViewPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            Intent intent = new Intent(ListaHorarios.this, RegistrarCita.class);
                            intent.putExtra("idHorario", ((ViewHorario) parent.getAdapter().getItem(position)).getId());
                            intent.putExtra("idEsp", idEsp);
                            intent.putExtra("idPac",idPac);
                            intent.putExtra("idDoc",idDoc);
                            startActivity(intent);
                        }
                    });

                }

            }

        }


    }

    private class getHorariosDoctor extends AsyncTask<String, Void, String> {
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
                horarioDoctores = HorarioDoctor.obtenerHorarioDoctores(result);
                horarioDoctores_aux = new ArrayList<>();

            }
        }
    }
}

