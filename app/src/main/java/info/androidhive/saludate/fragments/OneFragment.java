package info.androidhive.saludate.fragments;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import info.androidhive.materialtabs.R;
import info.androidhive.saludate.activity.ListaDoctores;
import info.androidhive.saludate.activity.ListaEspecialidades;
import info.androidhive.saludate.activity.Login;
import info.androidhive.saludate.activity.MainActivity;
import info.androidhive.saludate.clases_proyecto.Doctor;
import info.androidhive.saludate.clases_proyecto.DoctorAdapter;
import info.androidhive.saludate.clases_proyecto.Especialidad;
import info.androidhive.saludate.clases_proyecto.EspecialidadAdapter;
import info.androidhive.saludate.clases_proyecto.EspecialidadDoctor;
import info.androidhive.saludate.clases_proyecto.HttpRequest;
import info.androidhive.saludate.clases_proyecto.Paciente;
import info.androidhive.saludate.clases_proyecto.PacienteAdapter;
import static android.R.attr.button;
import static info.androidhive.materialtabs.R.array.especialidades;
import static info.androidhive.materialtabs.R.id.listViewPaciente;
public class OneFragment extends Fragment {
    private Context context;
    public Spinner s1,s2,s3; //s1 especialidades, s2 doctores, s2 horarios
    int idPac;
    Spinner spinnerParametro;
    EditText dato;
    ListView listViewPaciente;
    //int posEsp;
    ArrayList<Especialidad> especialidades;
    ArrayList<Especialidad> especialidades_aux;
    ArrayList<Doctor> doctores;

    public OneFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();
        idPac = b.getInt("idPac");
        Log.i("Pacienteeee  ","Su idddddd:  "+ idPac);
        final View view = inflater.inflate(R.layout.list_especialidades, container, false);
        context = view.getContext();

        this.spinnerParametro = (Spinner) view.findViewById(R.id.spinnerPacienteParametros);
        this.dato = (EditText) view.findViewById(R.id.editTextDato);
        this.listViewPaciente = (ListView) view.findViewById(R.id.listViewPaciente);

        new getEspecialidades().execute("http://35.164.146.218:8000/rest/especialidad/");


        ImageView fragment1_buscar = (ImageView) view.findViewById(R.id.fragment1_buscar);
        fragment1_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean encontrado = false;
                String busqueda = dato.getText().toString();
                ArrayList<Especialidad> espe = new ArrayList<>();
                for(int i = 0; i<especialidades.size();i++)
                    if(busqueda.equals(especialidades.get(i).getEspecialidad_nombre())){
                        espe.add(especialidades.get(i));
                        encontrado = true;
                    }
                EspecialidadAdapter adapter = new EspecialidadAdapter(getContext(), espe);
                if(encontrado){
                    LinearLayout layout = (LinearLayout)view.findViewById(R.id.emptyElement);
                    layout.setVisibility(View.INVISIBLE);
                    listViewPaciente.setAdapter(adapter);
                    Toast.makeText(view.getContext(),"Resultado encontrado",Toast.LENGTH_LONG).show();
                }
                else{
                    espe.clear();
                    adapter = new EspecialidadAdapter(getContext(), espe);
                    LinearLayout layout = (LinearLayout)view.findViewById(R.id.emptyElement);
                    layout.setVisibility(View.VISIBLE);
                    listViewPaciente.setAdapter(adapter);
                    Toast.makeText(view.getContext(),"No se encontraron resultados",Toast.LENGTH_LONG).show();
                } if(busqueda.isEmpty()){
                    LinearLayout layout = (LinearLayout)view.findViewById(R.id.emptyElement);
                    layout.setVisibility(View.INVISIBLE);
                    EspecialidadAdapter adapter2 = new EspecialidadAdapter(getContext(), especialidades);
                    listViewPaciente.setAdapter(adapter2);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
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
                //Toast.makeText(InfoPaciente.this,"No se generaron resultados",Toast.LENGTH_LONG).show();
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
                    EspecialidadAdapter adapter = new EspecialidadAdapter(getContext(), especialidades);
                    listViewPaciente.setAdapter(adapter);
                    listViewPaciente.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            Intent intent = new Intent(getContext(), ListaDoctores.class);
                            intent.putExtra("infoEspec", ((Especialidad) parent.getAdapter().getItem(position)).getId());
                            intent.putExtra("idPac",idPac);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}