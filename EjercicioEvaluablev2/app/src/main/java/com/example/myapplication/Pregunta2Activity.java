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
import android.media.MediaPlayer;

public class Pregunta2Activity extends AppCompatActivity {

    private String nombreUsuario;
    private int puntuacionRecibida;
    private Button btnSiguiente;
    private TextView tvProgreso;
    private TextView tvPregunta;
    private RadioGroup rgRespuestas;

    private final String RESPUESTA_CORRECTA = "Real Madrid";

    public static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    public static final String EXTRA_PUNTUACION = "EXTRA_PUNTUACION";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta2);

        btnSiguiente = findViewById(R.id.btn_siguiente);
        tvProgreso = findViewById(R.id.tv_progreso);
        tvPregunta = findViewById(R.id.tv_pregunta);
        rgRespuestas = findViewById(R.id.radioGroup_vertical);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NOMBRE_USUARIO) && intent.hasExtra(EXTRA_PUNTUACION)) {
            nombreUsuario = intent.getStringExtra(EXTRA_NOMBRE_USUARIO);
            puntuacionRecibida = intent.getIntExtra(EXTRA_PUNTUACION, 0);
        }

        tvProgreso.setText("Pregunta 2/5");

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgRespuestas.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(Pregunta2Activity.this, "Debes seleccionar una respuesta.", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedButton = findViewById(selectedId);
                String respuestaElegida = selectedButton.getText().toString();

                if (respuestaElegida.equals(RESPUESTA_CORRECTA)) {
                    puntuacionRecibida += 1;
                    playSound(R.raw.acierto);
                } else {
                    playSound(R.raw.mal);
                }

                Intent intentSiguiente = new Intent(Pregunta2Activity.this, Pregunta3Activity.class);

                intentSiguiente.putExtra(EXTRA_NOMBRE_USUARIO, nombreUsuario);
                intentSiguiente.putExtra(EXTRA_PUNTUACION, puntuacionRecibida);

                startActivity(intentSiguiente);
                finish();
            }
        });
    }

    private void playSound(int resourceId) {
        MediaPlayer mp = MediaPlayer.create(this, resourceId);
        mp.start();
        mp.setOnCompletionListener(MediaPlayer::release);
    }
}