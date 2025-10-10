package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int contador = 0;
    private TextView contadorTextView;
    private Button botonContar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //lo asocia a java con la interfaz

        // Asocia los objetos con los elementos del layout
        contadorTextView = findViewById(R.id.textView_numero);
        botonContar = findViewById(R.id.button_contador);

        botonContar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                contadorTextView.setText(String.valueOf(contador));
            }
        });
    }
}
