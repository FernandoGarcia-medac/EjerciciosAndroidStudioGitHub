package com.example.ejercicioevaluable2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre_usuario;
    private Button btn_comenzar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre_usuario = findViewById(R.id.et_nombre_usuario);
        btn_comenzar = findViewById(R.id.btn_comenzar);

        btn_comenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreUsuario = et_nombre_usuario.getText().toString().trim();

                if (nombreUsuario.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, introduce tu nombre para comenzar.", Toast.LENGTH_SHORT).show();
                } else {
                    iniciarQuiz(nombreUsuario);
                }
            }
        });
    }

    private void iniciarQuiz(String nombre) {
        Intent intent = new Intent(MainActivity.this, PreguntaActivity.class);
        intent.putExtra("EXTRA_NOMBRE_USUARIO", nombre);

        startActivity(intent);
    }
}