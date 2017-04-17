package info.androidhive.saludate.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import info.androidhive.materialtabs.R;
import info.androidhive.saludate.activity.ListaDoctores;
import info.androidhive.saludate.activity.ListaHorarios;
import info.androidhive.saludate.activity.RegistrarCita;
import info.androidhive.saludate.clases_proyecto.Cita;
import info.androidhive.saludate.clases_proyecto.CitaAdapter;
import info.androidhive.saludate.clases_proyecto.Doctor;
import info.androidhive.saludate.clases_proyecto.DoctorAdapter;
import info.androidhive.saludate.clases_proyecto.Especialidad;
import info.androidhive.saludate.clases_proyecto.EspecialidadDoctor;
import info.androidhive.saludate.clases_proyecto.Horario;
import info.androidhive.saludate.clases_proyecto.HorarioDoctor;
import info.androidhive.saludate.clases_proyecto.HttpRequest;
import info.androidhive.saludate.clases_proyecto.Paciente;
import info.androidhive.saludate.clases_proyecto.ViewCita;

import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

import static info.androidhive.materialtabs.R.id.listViewPaciente;


public class TwoFragment extends Fragment  {

    ArrayList<Paciente> pacientes;
    ArrayList<Paciente> pacientes_aux;

    ArrayList<Cita> citas;
    ArrayList<Cita> citas_aux;

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



    private Toolbar toolbarM;
    private Toolbar toolbarP;
    public static ActionMode mActionMode;
    //public CitaAdapter adapter;
    //private ArrayList citas;
    private int posicion;

    ListView listview;

    ViewCita citaDatos;

    public static  ArrayList<ViewCita> citasTotal = new ArrayList<>();

    int idPac;

    int idEsp;

    int idDoc;

    int idHor;


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_two, container, false);

        Bundle b = getArguments();

        //citaDatos = b.getParcelable("citaDatos");
        idPac = b.getInt("idPac");

        listview = (ListView) rootView.findViewById(R.id.list2);
        listview.canScrollVertically(0);
        listview.setEmptyView(rootView.findViewById(R.id.emptyElement));
        listview.setChoiceMode(CHOICE_MODE_SINGLE);
        rootView.setActivated(true);


        //citaDatos= new ViewCita();


        new TwoFragment.getEspecialidad().execute("http://35.164.146.218:8000/rest/especialidad/");
        new TwoFragment.getDoctor().execute("http://35.164.146.218:8000/rest/doctors/");
        new TwoFragment.getHorario().execute("http://35.164.146.218:8000/rest/horarios/");
        new TwoFragment.getEspecialidadDoctor().execute("http://35.164.146.218:8000/rest/especialidadDoctor/");
        new TwoFragment.getHorarioDoctor().execute("http://35.164.146.218:8000/rest/horariosDoctor/");
        new TwoFragment.getCitas().execute("http://35.164.146.218:8000/rest/citas/");


    listview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                           int pos, long id) {
                if (mActionMode != null) {return false;
                }
                    posicion=pos;
                    view.setSelected(true);
                    mActionMode = getActivity().startActionMode(mActionModeCallback);

                // Start the CAB using the ActionMode.Callback defined above
                return true;
            }
        });
        return rootView;
    }



    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_modificar, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(" Opciones");
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    crear_dialogo().show();
                    break;

            }
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };



/*Cerrar la action bar cuando cambio de fragment en el tab*/
    @Override
    public void onPause() {
        super.onPause();
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mActionMode != null && !isVisibleToUser) {
            mActionMode.finish();
        }
    }

    /* dialogo para eliminar cita */
    private Dialog crear_dialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage("¿Desea eliminar la cita programada?");
        builder.setPositiveButton("Aceptar",    new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mActionMode.finish();
            }
        });

        builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mActionMode.finish();
                dialog.cancel();
            }
        });

        return builder.create();
    }

    private class getCitas extends AsyncTask<String, Void, String> {
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
                citas = Cita.obtenerCitas(result);
                citas_aux = new ArrayList<>();

                for(int i=0;i<citas.size();i++){
                    Log.i("Usuariooooo  ","Citaaaaa:  "+ citas.get(i).getId_cita()+"    "+ idPac);
                    if(citas.get(i).getPaciente() == idPac){


                        citas_aux.add(citas.get(i));
                    }
                }

                for(int i=0; i<citas_aux.size();i++){
                    citaDatos= new ViewCita();
                    for(int j=0;j<especDoctores.size();j++){
                        if(especDoctores.get(j).getId() == citas_aux.get(i).getEspecialidaddoctor()){
                            for(int j1=0;j1<especialidad.size();j1++){
                                if(especDoctores.get(j).getEspecialidad() == especialidad.get(j1).getId()){
                                    citaDatos.setEspecialidad(especialidad.get(j1).getEspecialidad_nombre());
                                }
                            }

                            for(int j2=0;j2<doctores.size();j2++){
                                if(especDoctores.get(j).getDoctor() == doctores.get(j2).getId()){
                                    citaDatos.setDoctor("Dr(a) "+doctores.get(j2).getDoctor_apellido_paterno());
                                }
                            }
                        }
                    }

                    for(int k=0;k<horarioDoctores.size();k++){
                        if(horarioDoctores.get(k).getId() == citas_aux.get(i).getHorariodoctor()){
                            for(int k1=0;k1<horarios.size();k1++){
                                if(horarioDoctores.get(k).getHorario() == horarios.get(k1).getId()){
                                    citaDatos.setHorario(horarios.get(k1).getHora_inicio()+" - "+horarios.get(k1).getHora_fin());
                                    citaDatos.setFecha(horarioDoctores.get(k).getFecha_disponibilidad());
                                    citaDatos.setNotasAd(citas_aux.get(i).getNotas_adicionales());
                                    citaDatos.setDescripcion(citas_aux.get(i).getCita_descripcion());
                                }
                            }
                        }
                    }

                    Log.i("Acaaaaaaa  ","Citaaaaa222222:  "+ citaDatos.getEspecialidad());
                    citasTotal.add(citaDatos);
                }

                Log.i("Acaaaaaaa  ","Tamañooo citaaas:  "+ citasTotal.size());

                if (citasTotal.size() != 0){
                    CitaAdapter adapter = new CitaAdapter(getContext(), citasTotal);
                    listview.setAdapter(adapter);

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

            }
        }
    }

}



