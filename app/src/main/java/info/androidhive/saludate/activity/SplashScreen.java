package info.androidhive.saludate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import info.androidhive.materialtabs.R;

/**
 * Created by Jair Barzola on 20-Oct-16.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread mythread = new Thread(){
            @Override
            public void run() {

                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            };
                mythread.start();

            }
}
