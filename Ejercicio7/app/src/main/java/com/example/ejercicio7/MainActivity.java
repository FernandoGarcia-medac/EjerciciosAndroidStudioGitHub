package com.example.ejercicio7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private Button buttonIr;
    private EditText editTextText;
    private EditText editTextText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociar objetos java a XML
        buttonIr = findViewById(R.id.buttonIr);
        editTextText = findViewById(R.id.editTextText);
        editTextText2 = findViewById(R.id.editTextText2);

    //3. Eventos, logica, conexiones a bbdd, etc, etc ,etc
    buttonIr.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //1. Recojo los textos escritos en los campos
            String nombre = editTextText.getText().toString();
            String apellido = editTextText2.getText().toString();

            //METODO 1 DE PASO DE DATOS: PUTEXTRA
            /*
            //2. Crea el intent para que lance la segunda pantalla
            Intent abrirPantalla = new Intent(MainActivity.this, MainActivity2.class);

            //3. Enviar datos con el método putExtra de la clase Intent
            abrirPantalla.putExtra("nombre", nombre);
            abrirPantalla.putExtra("apellidos", apellido);

            //4. Lanza la activity
            startActivity(abrirPantalla);
             */

            //METODO 2 DE PASO DE DATOS: CLASE STATIC

            //2.
            /*
            Intent abrirPantalla = new Intent(MainActivity.this, MainActivity2.class);

            Almacen.setNombre(nombre);
            Almacen.setApellido(apellido);

             */

            //METODO 3 DE PASO DE DATOS: sharedPreferences (XML)
            Intent abrirPantalla = new Intent(MainActivity.this, MainActivity2.class);


            SharedPreferences prefs = getSharedPreferences("Datos", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", nombre);
            editor.putString("apellido", apellido);
            editor.apply();

            //Tras pasar datos, lanzamos activity
            startActivity(abrirPantalla);


        }
    });


    }
}