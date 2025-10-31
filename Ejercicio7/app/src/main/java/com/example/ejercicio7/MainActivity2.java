package com.example.ejercicio7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private Button button2Volver;
    private TextView textView3;
    private TextView textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Asocia los elementos de la interfaz con las variables Java
        button2Volver = findViewById(R.id.button2Volver);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);

        //METODO 1 DE RECOGER DATOS (PUTEXTRA)
        /*
        //Obten el Intent y el valor de "palabra"
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellidos = intent.getStringExtra("apellidos");

        //Configura el texto del TextView
        textView3.setText(nombre);
        textView4.setText(apellidos);
        */

        //METODO 2 DE RECOGER DATOS (CLASE STATIC)
        /*
        textView3.setText( Almacen.getNombre());
        textView4.setText( Almacen.getApellido());

         */

        //METODO 3 DE RECOGER DATOS (SharedPreferecnes)
        SharedPreferences prefs = getSharedPreferences("Datos", MODE_PRIVATE);
        textView3.setText( prefs.getString("apellido", ""));
        textView4.setText( prefs.getString("nombre", ""));





    }
}