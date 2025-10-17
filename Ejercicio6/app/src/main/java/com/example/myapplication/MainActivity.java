package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Crear los objetos en codigo (Button, EditText)
    //Asociarlos con findViewById
    // Crear los 3 listener para los 3 botones
        // Recoger lo 3 datos escritos en los campos
    //INTENT:
    private Button botonTlf;
    private Button botonMail;
    private Button botonUrl;
    private EditText textoTlf;
    private EditText textoMail;
    private EditText textoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonTlf = findViewById(R.id.botonLlamar);
        botonMail = findViewById(R.id.botonMail);
        botonUrl = findViewById(R.id.botonUrl);
        textoTlf = findViewById(R.id.textoLlamar);
        textoMail = findViewById(R.id.textoMail);
        textoUrl = findViewById(R.id.textoDireccion);

        //boton telefono (ACTION_DIAL)
        botonTlf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent tlfIntent = new Intent(Intent.ACTION_DIAL);
                tlfIntent.setData(Uri.parse("tel: " + textoTlf.getText()));
                startActivity(tlfIntent);
            }
        });

        botonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                startActivity(emailIntent);
            }
        });


    }
}
