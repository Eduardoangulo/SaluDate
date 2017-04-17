package info.androidhive.saludate.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.HttpRequest;
import info.androidhive.saludate.clases_proyecto.Paciente;


public class Login extends Activity {


    public static final String EXTRA_PATIENT = "EXTRA_PATIENT";
    private static final int REQUEST_RESPONSE = 1;
    EditText dni;
    EditText password;
    private static ConnectivityManager manager;
    Button entrar;
    boolean d = false;
    boolean p = false;
    boolean m = false;
    boolean n = false;
    ArrayList<Paciente> pacientes;
    ArrayList<Paciente> pacientes_aux;
    Paciente pac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        password = (EditText) findViewById(R.id.txtPass);
        entrar = (Button) findViewById(R.id.entrar);
        /*para las validaciones*/
        dni = (EditText) findViewById(R.id.txtDNI);
        new Login.getPacientesLog().execute("http://35.164.146.218:8000/rest/patients/");


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedMobile(getApplicationContext()) == true || isConnectedWifi(getApplicationContext()) == true) {
                    if (p == true && d == true) {
                        final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "", "Cargando...");

                        Thread mythread = new Thread(){
                            @Override
                            public void run() {

                                try {
                                    sleep(3500);
                                    Intent main = new Intent(Login.this, MainActivity.class);
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
                    }else{
                        if ((p == false && d == false) && (m == true && n == true)) {
                            Toast.makeText(getApplication(), "DNI y Contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    dialogoLogin().show();

                }
            }

        });


        /*para has olvidado tu contraseña*/
        TextView forget = (TextView) findViewById(R.id.forgot);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f = new Intent(Login.this, ForgotPassword.class);
                startActivity(f);


            }
        });
    }

    /************************************/
    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isConnectedMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    private Dialog dialogoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Error al Entrar");
        builder.setMessage("No se puede iniciar sesión. Comprueba la conexión de red.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();


            }
        });


        return builder.create();
    }

    private class getPacientesLog extends AsyncTask<String, Void, String> {
        public String doInBackground(String... params){
            try{
                return HttpRequest.get(params[0]).accept("application/json").body();
            }catch(Exception e){
                return "";
            }

        }
        public void onPostExecute(String result){
            if(result.isEmpty()){
                Toast.makeText(Login.this,"No se pudo conectar al servidor",Toast.LENGTH_LONG).show();
            }else{
                pacientes = Paciente.obtenerPacientes(result);
                pacientes_aux = new ArrayList<>();
                dni.addTextChangedListener(new TextValidator(dni) {
                    @Override
                    public void validate(EditText editText, String text) {
                        //Implementamos la validación que queramos
                        for (int i=0; i<pacientes.size();i++){
                            if (text.equals(pacientes.get(i).getPaciente_dni())) {
                                p = true;
                                pac = pacientes.get(i);
                                break;
                            } else {
                                p = false;
                            }
                            Log.i("Usuariooooo  ","Nombreeee:  "+pacientes.get(i).getPaciente_dni());
                        }

                        if (text.length() > 1 && text.length() < 8) {
                            dni.setError("Faltan digitos");
                            m = true;
                        } else {
                            m = false;
                        }

                        Log.i("Usuariooooo  ","Estado Nombreeeee:  "+ p +"    "+m);
                    }
                });
                password.addTextChangedListener(new TextValidator(password) {
                    @Override
                    public void validate(EditText editText, String text) {
                        for (int i=0; i<pacientes.size();i++){
                            if (text.equals(pacientes.get(i).getPaciente_contras_ingreso())) {
                                d = true;
                                break;
                            } else {
                                d = false;
                            }
                            Log.i("Usuariooooo  ","Contraseñaaaaa:  "+pacientes.get(i).getPaciente_contras_ingreso());
                        }

                        //Implementamos la validación que queramos
                        if (text.length() > 1 && text.length() < 6) {
                            password.setError("La contraseña es muy corta");
                            n = true;
                        } else {
                            n = false;
                        }

                        Log.i("Usuariooooo  ","Estado Contraaaaaa:  "+ d +"    "+n);
                    }
                });
            }
        }
    }

}

