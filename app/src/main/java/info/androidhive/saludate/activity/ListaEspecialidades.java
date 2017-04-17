package info.androidhive.saludate.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.Doctor;
import info.androidhive.saludate.clases_proyecto.DoctorAdapter;
import info.androidhive.saludate.clases_proyecto.Especialidad;
import info.androidhive.saludate.clases_proyecto.EspecialidadAdapter;
import info.androidhive.saludate.clases_proyecto.HttpRequest;

import static info.androidhive.materialtabs.R.array.especialidades;

/**
 * Created by Usuario on 19/11/2016.
 */

public class ListaEspecialidades extends Activity {

    Spinner spinnerParametro;
    EditText dato;
    ListView listViewPaciente;

    ArrayList<Doctor> doctores;

    ArrayList<Especialidad> especialidades;
    ArrayList<Especialidad> especialidades_aux;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_especialidades);

        this.spinnerParametro = (Spinner) findViewById(R.id.spinnerPacienteParametros);
        this.dato = (EditText) findViewById(R.id.editTextDato);
        this.listViewPaciente = (ListView) findViewById(R.id.listViewPaciente);

        new ListaEspecialidades.getEspecialidades().execute("http://35.164.146.218:8000/rest/especialidad/");

    }

    private class getEspecialidades extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params){
            try{
                return HttpRequest.get(params[0]).accept("application/json").body();
            }catch(Exception e){
                return "";
            }

        }

        public void onPostExecute(String result){
            if(result.isEmpty()){
                Toast.makeText(ListaEspecialidades.this,"No se generaron resultados", Toast.LENGTH_LONG).show();
            }else{
                especialidades = Especialidad.obtenerEspecialidades(result);
                especialidades_aux = new ArrayList<>();

                if (spinnerParametro.getSelectedItem().equals("Listar Todo")){
                    especialidades_aux = especialidades;
                }else{
                    for(int i=0; i<doctores.size();i++){
                        switch(spinnerParametro.getSelectedItem().toString()){
                            case "ID":

                                break;
                            case "Nombre":
                                if (especialidades.get(i).getEspecialidad_nombre().equals(dato.getText().toString().trim())){
                                    especialidades_aux.add(especialidades.get(i));
                                }
                                break;
                            case "Descripcion":
                                if (especialidades.get(i).getEspeialidad_descripcion().equals(dato.getText().toString().trim())){
                                    especialidades_aux.add(especialidades.get(i));
                                }
                                break;
                        }
                    }
                }

                if (especialidades.size() != 0){
                    EspecialidadAdapter adapter = new EspecialidadAdapter(ListaEspecialidades.this, especialidades);
                    listViewPaciente.setAdapter(adapter);
                }

            }
        }
    }


}
