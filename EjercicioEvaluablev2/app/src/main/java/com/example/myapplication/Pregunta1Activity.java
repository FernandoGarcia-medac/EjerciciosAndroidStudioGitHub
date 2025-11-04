package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pregunta1Activity extends AppCompatActivity {

    private String nombreUsuario;
    private Button btnSiguiente;
    private int puntuacionActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        btnSiguiente = findViewById(R.id.btn_siguiente);

        if (getIntent().hasExtra("EXTRA_NOMBRE_USUARIO")) {
            nombreUsuario = getIntent().getStringExtra("EXTRA_NOMBRE_USUARIO");
        }

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Pregunta1Activity.this, Pregunta2Activity.class);
                intent.putExtra("EXTRA_NOMBRE_USUARIO", nombreUsuario);
                intent.putExtra("EXTRA_PUNTUACION", puntuacionActual);

                startActivity(intent);

            }
        });
    }
}
