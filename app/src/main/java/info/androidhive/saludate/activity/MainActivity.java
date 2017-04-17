package info.androidhive.saludate.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.saludate.clases_proyecto.Paciente;
import info.androidhive.saludate.clases_proyecto.ViewCita;
import info.androidhive.saludate.fragments.OneFragment;
import info.androidhive.saludate.fragments.ThreeFragment;
import info.androidhive.saludate.fragments.TwoFragment;

import static info.androidhive.saludate.fragments.TwoFragment.citasTotal;
import static info.androidhive.saludate.fragments.TwoFragment.mActionMode;

public class MainActivity extends AppCompatActivity {//implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout linear;
    Paciente paciente;
    ViewCita citaDat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paciente = (Paciente) getIntent().getSerializableExtra("EXTRA_PATIENT");

        citaDat = (ViewCita) getIntent().getSerializableExtra("DatosCita");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {

        Fragment fr1 = new OneFragment();
        Bundle args = new Bundle();
        args.putInt("idPac", paciente.getId());
        fr1.setArguments(args);

        Fragment fr2 = new TwoFragment();
        Bundle args2 = new Bundle();
        args2.putInt("idPac", paciente.getId());
        args2.putParcelable("citaDatos", citaDat);
        fr2.setArguments(args2);

        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(fr1, "REGISTRO CITA");
        adapter.addFragment(fr2, "PROG CITAS");
        adapter.addFragment(new ThreeFragment(), "HISTORIAL CITAS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                System.exit(0);

                break;
            case R.id.info:
                InformaPacie().show();
                break;
            case R.id.about:
                Toast.makeText(this,"Acerca de la aplicacion", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*dialogo de informacion de paciente*/
    private Dialog InformaPacie() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.informacion_paciente, null);
        builder.setCancelable(false);
        builder.setView(v);

        TextView nombre = (TextView) v.findViewById(R.id.nombreUsuario);
        TextView dni = (TextView) v.findViewById(R.id.nroDNI);
        TextView genero = (TextView) v.findViewById(R.id.genero);
        TextView fechaNac = (TextView) v.findViewById(R.id.fechaNac);
        TextView telefono = (TextView) v.findViewById(R.id.movil);
        TextView citas = (TextView) v.findViewById(R.id.nroCita);

        nombre.setText(paciente.getPaciente_nombre()+" "+paciente.getPaciente_apellido_paterno()+" "+paciente.getPaciente_apellido_materno());
        dni.setText(paciente.getPaciente_dni());
        genero.setText(paciente.getPaciente_genero());
        fechaNac.setText(paciente.getPaciente_fecha_nacimiento());
        telefono.setText(paciente.getPaciente_telefono());
        citas.setText("Nro de citas : "+citasTotal.size());

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

