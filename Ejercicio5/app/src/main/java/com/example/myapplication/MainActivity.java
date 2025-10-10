package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
* Haz una aplicacion basica de "Hello World" con un fondo de color y texto
*
* Añade ahora varios botones, unos que sirvan para aumentar o disminuir el tamaño del texto,
* otros para cmabiar el color del fondo, y otro para que el saludo aparezca en un toast (mensaje flotante)
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}