package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PreguntaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);


        if (getIntent().hasExtra("EXTRA_NOMBRE_USUARIO")) {
            String nombreUsuario = getIntent().getStringExtra("EXTRA_NOMBRE_USUARIO");
        }

    }
}