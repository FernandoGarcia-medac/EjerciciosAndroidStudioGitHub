package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;

public class Pregunta1Activity extends AppCompatActivity {

    private String nombreUsuario;
    private Button btnSiguiente;
    private RadioGroup rgRespuestas;
    private TextView tvProgreso;
    private int puntuacionActual = 0;
    private final String RESPUESTA_CORRECTA = "Celta de Vigo";
    public static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    public static final String EXTRA_PUNTUACION = "EXTRA_PUNTUACION";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        btnSiguiente = findViewById(R.id.btn_siguiente);
        rgRespuestas = findViewById(R.id.radioGroup_vertical);
        tvProgreso = findViewById(R.id.tv_progreso);

        tvProgreso.setText("Pregunta 1/5");

        if (getIntent().hasExtra(EXTRA_NOMBRE_USUARIO)) {
            nombreUsuario = getIntent().getStringExtra(EXTRA_NOMBRE_USUARIO);
        }

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = rgRespuestas.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(Pregunta1Activity.this, "Debes seleccionar una respuesta.", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedButton = findViewById(selectedId);
                String respuestaElegida = selectedButton.getText().toString();

                if (respuestaElegida.equals(RESPUESTA_CORRECTA)) {
                    puntuacionActual += 1;
                }

                Intent intent = new Intent(Pregunta1Activity.this, Pregunta2Activity.class);
                intent.putExtra(EXTRA_NOMBRE_USUARIO, nombreUsuario);
                intent.putExtra(EXTRA_PUNTUACION, puntuacionActual);

                startActivity(intent);
                finish();
            }
        });
    }
}