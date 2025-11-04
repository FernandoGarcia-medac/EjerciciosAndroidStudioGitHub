package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pregunta2Activity extends AppCompatActivity {

    private String nombreUsuario;
    private int puntuacionRecibida;
    private Button btnSiguiente;
    private TextView tvProgreso;
    private TextView tvPregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        btnSiguiente = findViewById(R.id.btn_siguiente);
        tvProgreso = findViewById(R.id.tv_progreso);
        tvPregunta = findViewById(R.id.tv_pregunta);

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_NOMBRE_USUARIO") && intent.hasExtra("EXTRA_PUNTUACION")) {
            nombreUsuario = intent.getStringExtra("EXTRA_NOMBRE_USUARIO");
            puntuacionRecibida = intent.getIntExtra("EXTRA_PUNTUACION", 0);
        }

        tvProgreso.setText("Pregunta 2/5");
        tvPregunta.setText("¿Qué equipo logró el récord de puntos en una temporada de LaLiga (100 puntos)?");

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intentSiguiente = new Intent(Pregunta2Activity.this, Pregunta3Activity.class);

                intentSiguiente.putExtra("EXTRA_NOMBRE_USUARIO", nombreUsuario);
                intentSiguiente.putExtra("EXTRA_PUNTUACION", puntuacionRecibida);

                startActivity(intentSiguiente);
                finish();
            }
        });
    }
}
