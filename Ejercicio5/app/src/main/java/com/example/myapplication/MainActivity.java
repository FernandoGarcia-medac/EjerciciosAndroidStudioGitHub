package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.widget.LinearLayout;

/*
 * Haz una aplicacion basica de "Hello World" con un fondo de color y texto.
 * Añade ahora varios botones:
 *   - unos que sirvan para aumentar o disminuir el tamaño del texto,
 *   - otro para cambiar el color del fondo,
 *   - y otro para que el saludo aparezca en un toast (mensaje flotante).
 */

public class MainActivity extends AppCompatActivity {

    private TextView textoSaludo;
    private Button botonAumentar, botonDisminuir, botonFondo, botonToast;
    private LinearLayout layoutPrincipal;

    private float tamañoTexto = 24f;  //tamaño inicial del texto
    private int colorActual = 0;      //para alternar los colores de fondo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos
        textoSaludo = findViewById(R.id.textView_saludo);
        botonAumentar = findViewById(R.id.button_aumentar);
        botonDisminuir = findViewById(R.id.button_disminuir);
        botonFondo = findViewById(R.id.button_fondo);
        botonToast = findViewById(R.id.button4);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);

        //Botón para aumentar el tamaño del texto
        botonAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tamañoTexto += 4f;
                textoSaludo.setTextSize(tamañoTexto);
            }
        });

        //disminuir el tamaño del texto
        botonDisminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tamañoTexto > 8f) {
                    tamañoTexto -= 4f;
                    textoSaludo.setTextSize(tamañoTexto);
                }
            }
        });

        //Botón para cambiar el color del fondo (va rotando entre varios colores)
        botonFondo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorActual = (colorActual + 1) % 4; //Cambia entre 4 colores
                switch (colorActual) {
                    case 0:
                        layoutPrincipal.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        layoutPrincipal.setBackgroundColor(Color.CYAN);
                        break;
                    case 2:
                        layoutPrincipal.setBackgroundColor(Color.MAGENTA);
                        break;
                    case 3:
                        layoutPrincipal.setBackgroundColor(Color.YELLOW);
                        break;
                }
            }
        });

        //Botón para mostrar el saludo en un Toast
        botonToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, textoSaludo.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
