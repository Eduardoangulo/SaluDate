package info.androidhive.saludate.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.Cita;
import info.androidhive.saludate.clases_proyecto.Doctor;
import info.androidhive.saludate.clases_proyecto.Especialidad;
import info.androidhive.saludate.clases_proyecto.EspecialidadDoctor;
import info.androidhive.saludate.clases_proyecto.Horario;
import info.androidhive.saludate.clases_proyecto.HorarioDoctor;
import info.androidhive.saludate.clases_proyecto.HttpRequest;
import info.androidhive.saludate.clases_proyecto.Paciente;
import info.androidhive.saludate.clases_proyecto.ViewCita;

import static android.R.attr.id;
import static info.androidhive.materialtabs.R.drawable.telefono;
import static info.androidhive.materialtabs.R.id.fechaNac;
import static info.androidhive.materialtabs.R.id.genero;
import static info.androidhive.saludate.fragments.TwoFragment.citasTotal;

/**
 * Created by Usuario on 19/11/2016.
 */

public class RegistrarCita extends Activity{


    int idEsp;

    int idPac;

    int idHorario;

    int idDoc;

    int idEspDoc;

    int idHoraDoc;

    int idCita;

    Cita cita;

    ViewCita citaDatos;

    ArrayList<Paciente> pacientes;
    ArrayList<Paciente> pacientes_aux;

    ArrayList<Doctor> doctores;
    ArrayList<Doctor> doctores_aux;

    ArrayList<Especialidad> especialidad;
    ArrayList<Especialidad> especialidad_aux;

    ArrayList<Horario> horarios;
    ArrayList<Horario> horarios_aux;

    ArrayList<EspecialidadDoctor> especDoctores;
    ArrayList<EspecialidadDoctor> especDoctores_aux;

    ArrayList<HorarioDoctor> horarioDoctores;
    ArrayList<HorarioDoctor> horarioDoctores_aux;

    TextView txtPaciente;
    TextView txtEspecialidad;
    TextView txtDoctor;
    TextView txtHorario;

    EditText editDescripcion;
    EditText editAdicionales;

    String descripcion;
    String adicionales;

    Paciente pac;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_cita);

        citaDatos = new ViewCita();

        txtPaciente = (TextView) findViewById(R.id.textViewPaciente);
        txtEspecialidad = (TextView) findViewById(R.id.textViewEspecialidad);
        txtDoctor = (TextView) findViewById(R.id.textViewDoctor);
        txtHorario = (TextView) findViewById(R.id.textViewHorario);

        editDescripcion = (EditText) findViewById(R.id.textArea_1);
        editAdicionales = (EditText) findViewById(R.id.textArea_2);

        Bundle bundle = getIntent().getExtras();
        idEsp = bundle.getInt("idEsp");
        idPac = bundle.getInt("idPac");
        idHorario = bundle.getInt("idHorario");
        idDoc = bundle.getInt("idDoc");

        new RegistrarCita.getPacientes().execute("http://35.164.146.218:8000/rest/patients/");
        new RegistrarCita.getEspecialidad().execute("http://35.164.146.218:8000/rest/especialidad/");
        new RegistrarCita.getDoctor().execute("http://35.164.146.218:8000/rest/doctors/");
        new RegistrarCita.getHorario().execute("http://35.164.146.218:8000/rest/horarios/");
        new RegistrarCita.getEspecialidadDoctor().execute("http://35.164.146.218:8000/rest/especialidadDoctor/");
        new RegistrarCita.getHorarioDoctor().execute("http://35.164.146.218:8000/rest/horariosDoctor/");

        Button button_registro = (Button) findViewById(R.id.buttonResgistro);
        button_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final ProgressDialog progressDialog = ProgressDialog.show(RegistrarCita.this, "", "Registrando...");
                //Toast.makeText(RegistrarCita.this, "Se ha registrado correctamente", Toast.LENGTH_LONG).show();
                Thread mythread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            sleep(2500);
                            idCita = idPac*100 + idEspDoc*10 + idHoraDoc;
                            descripcion = editDescripcion.getText().toString().trim();
                            adicionales = editAdicionales.getText().toString().trim();

                            cita = new Cita();

                            cita.setId_cita(idCita);
                            cita.setPaciente(idPac);
                            cita.setEspecialidaddoctor(idEspDoc);
                            cita.setHorariodoctor(idHoraDoc);
                            cita.setCita_descripcion(descripcion);
                            cita.setNotas_adicionales(adicionales);

                            new RegistrarCita.registrarCita().execute();

                            Intent main = new Intent(RegistrarCita.this, MainActivity.class);
                            main.putExtra("DatosCita", (Parcelable) citaDatos);
                            main.putExtra("EXTRA_PATIENT", (Parcelable) pac);
                            startActivity(main);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                };
                mythread.start();


            }
        });
    }

    private class getPacientes extends AsyncTask<String, Void, String> {
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
                pacientes = Paciente.obtenerPacientes(result);
                pacientes_aux = new ArrayList<>();

                for(int i=0;i<pacientes.size();i++){
                    if(pacientes.get(i).getId() == idPac){
                        txtPaciente.setText(pacientes.get(i).getPaciente_apellido_paterno()+" "+pacientes.get(i).getPaciente_apellido_materno()+", "+pacientes.get(i).getPaciente_nombre());
                        pac = pacientes.get(i);
                    }
                }
            }
        }
    }

    private class getEspecialidad extends AsyncTask<String, Void, String> {
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
                especialidad = Especialidad.obtenerEspecialidades(result);
                especialidad_aux = new ArrayList<>();

                for(int i=0;i<especialidad.size();i++){
                    if(especialidad.get(i).getId() == idEsp){
                        txtEspecialidad.setText(especialidad.get(i).getEspecialidad_nombre());
                        citaDatos.setEspecialidad(especialidad.get(i).getEspecialidad_nombre());
                    }
                }
            }
        }
    }

    private class getDoctor extends AsyncTask<String, Void, String> {
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
                doctores = Doctor.obtenerDoctores(result);
                doctores_aux = new ArrayList<>();

                for(int i=0;i<doctores.size();i++){
                    if(doctores.get(i).getId() == idDoc){
                        txtDoctor.setText("Dr.(a) "+ doctores.get(i).getDoctor_apellido_paterno());
                        citaDatos.setDoctor("Dr.(a) "+ doctores.get(i).getDoctor_apellido_paterno());
                    }
                }
            }
        }
    }

    private class getHorario extends AsyncTask<String, Void, String> {
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

                for(int i=0;i<horarios.size();i++){
                    if(horarios.get(i).getId() == idHorario){
                        txtHorario.setText(horarios.get(i).getHora_inicio()+" - "+horarios.get(i).getHora_fin());
                        citaDatos.setHorario(horarios.get(i).getHora_inicio()+" - "+horarios.get(i).getHora_fin());
                    }
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

                for(int i=0;i<especDoctores.size();i++){
                    if(especDoctores.get(i).getEspecialidad() == idEsp && especDoctores.get(i).getDoctor() == idDoc){
                        idEspDoc = especDoctores.get(i).getId();
                    }
                }
            }
        }
    }

    private class getHorarioDoctor extends AsyncTask<String, Void, String> {
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

                for(int i=0;i<horarioDoctores.size();i++){
                    if(horarioDoctores.get(i).getHorario() == idHorario && horarioDoctores.get(i).getDoctor() == idDoc){
                        idHoraDoc = horarioDoctores.get(i).getId();
                        citaDatos.setFecha(horarioDoctores.get(i).getFecha_disponibilidad());
                    }
                }
            }
        }
    }

    private class registrarCita extends AsyncTask<Void, Void, Boolean> {
        public Boolean doInBackground(Void... params){
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://35.164.146.218:8000/rest/citas/");
            httpPost.setHeader("Content-Type", "application/json");
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("id_cita", cita.getId_cita());
                jsonObject.put("horariodoctor", cita.getHorariodoctor());
                jsonObject.put("especialidaddoctor", cita.getEspecialidaddoctor());
                jsonObject.put("paciente", cita.getPaciente());
                jsonObject.put("cita_descripcion", cita.getCita_descripcion());
                jsonObject.put("notas_adicionales", cita.getNotas_adicionales());

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                httpClient.execute(httpPost);

                return true;

            }catch(org.json.JSONException e){
                return false;
            }catch(java.io.UnsupportedEncodingException e){
                return false;
            }catch(org.apache.http.client.ClientProtocolException e){
                return false;
            }catch (java.io.IOException e){
                return false;
            }

        }

        public void onPostExecute(Boolean result){
            if(result){

                Toast.makeText(RegistrarCita.this, "Se ha registrado correctamente", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(RegistrarCita.this, "Problemas al insertar", Toast.LENGTH_LONG).show();
            }
        }
    }

}
