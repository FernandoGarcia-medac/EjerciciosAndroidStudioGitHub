package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

public class Pregunta5Activity extends AppCompatActivity {

    private String nombreUsuario;
    private int puntuacionRecibida;
    private Button btnSiguiente;
    private TextView tvProgreso;
    private TextView tvPregunta;
    private RadioGroup rgRespuestas;

    private final String RESPUESTA_CORRECTA = "Estadio de La Cartuja";

    public static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    public static final String EXTRA_PUNTUACION = "EXTRA_PUNTUACION";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta5);

        btnSiguiente = findViewById(R.id.btn_siguiente);
        tvProgreso = findViewById(R.id.tv_progreso);
        tvPregunta = findViewById(R.id.tv_pregunta);
        rgRespuestas = findViewById(R.id.radioGroup_vertical);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOMBRE_USUARIO) && intent.hasExtra(EXTRA_PUNTUACION)) {
            nombreUsuario = intent.getStringExtra(EXTRA_NOMBRE_USUARIO);
            puntuacionRecibida = intent.getIntExtra(EXTRA_PUNTUACION, 0);
        }

        tvProgreso.setText("Pregunta 5/5");

        btnSiguiente.setText("Finalizar Quiz");

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgRespuestas.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(Pregunta5Activity.this, "Debes seleccionar una respuesta.", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedButton = findViewById(selectedId);
                String respuestaElegida = selectedButton.getText().toString();

                if (respuestaElegida.equals(RESPUESTA_CORRECTA)) {
                    puntuacionRecibida += 1;
                }

                int notaFinal = puntuacionRecibida * 2;

                Intent intentFinal = new Intent(Pregunta5Activity.this, FinalActivity.class);

                intentFinal.putExtra(EXTRA_NOMBRE_USUARIO, nombreUsuario);
                intentFinal.putExtra(EXTRA_PUNTUACION, notaFinal);

                startActivity(intentFinal);
                finish();
            }
        });
    }
}