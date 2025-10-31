package com.example.ejercicio8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText apellido;
    private Button botonComprobar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.editTextText);
        apellido = findViewById(R.id.editTextText2);
        botonComprobar = findViewById(R.id.button);

        //Inicializar el helper de la bd
        dbHelper = new DatabaseHelper(this);


        botonComprobar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String Tnombre = nombre.getText().toString();
            String Tapellido = apellido.getText().toString();

            // Ejemplo de comprobación
            boolean existe = dbHelper.existeProfesor(Tnombre, Tapellido);
            if (existe) {
                Toast.makeText(MainActivity.this, "Profesor encontrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No existe ese profesor", Toast.LENGTH_SHORT).show();
            }

        }
    });

    }


}