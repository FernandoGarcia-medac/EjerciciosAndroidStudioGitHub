package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import com.example.myapplication.DBHelper.RankingEntry;
import java.util.List;
import com.example.myapplication.DBHelper.RankingEntry;

public class FinalActivity extends AppCompatActivity {

    private TextView tvResultado;
    private TextView tvNotaFinal;
    private ImageView ivResultado;
    private Button btnVolver;
    private LinearLayout llRankingContenedor;

    private DBHelper dbHelper;
    private static final int RANKING_LIMIT = 5;

    public static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    public static final String EXTRA_PUNTUACION = "EXTRA_PUNTUACION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        dbHelper = new DBHelper(this);

        tvResultado = findViewById(R.id.tv_mensaje);
        tvNotaFinal = findViewById(R.id.tv_puntuacion);
        ivResultado = findViewById(R.id.iv_resultado);
        btnVolver = findViewById(R.id.btn_volver_quiz);
        llRankingContenedor = findViewById(R.id.ll_ranking_contenedor);

        Intent intent = getIntent();
        String nombreUsuario = "Fer";
        int notaFinal = 0;

        if (intent.hasExtra(EXTRA_NOMBRE_USUARIO)) {
            nombreUsuario = intent.getStringExtra(EXTRA_NOMBRE_USUARIO);
        }
        if (intent.hasExtra(EXTRA_PUNTUACION)) {
            notaFinal = intent.getIntExtra(EXTRA_PUNTUACION, 0);
        }


        dbHelper.guardarResultado(nombreUsuario, notaFinal);


        mostrarRanking(nombreUsuario);


        if (notaFinal >= 5) {
            tvResultado.setText(nombreUsuario + " APROBADO");
            ivResultado.setImageResource(R.drawable.ok);
            tvNotaFinal.setText("Nota " + notaFinal + " / 10");
        } else {
            tvResultado.setText(nombreUsuario + ". SUSPENSO");
            ivResultado.setImageResource(R.drawable.caca);
            tvNotaFinal.setText("Nota " + notaFinal + " / 10");
        }

        btnVolver.setOnClickListener(v -> {
            Intent mainIntent = new Intent(FinalActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        });
    }

    private void mostrarRanking(String nombreJugadorActual) {

        List<RankingEntry> rankingList = dbHelper.obtenerRanking(RANKING_LIMIT);

        llRankingContenedor.removeAllViews();

        int topCount = 0;
        for (RankingEntry resultado : rankingList) {


            if (topCount >= RANKING_LIMIT) break;

            TextView tvEntry = new TextView(this);

            String texto = (topCount + 1) + ". " + resultado.nombre + " - " + resultado.puntuacion + "/10";
            tvEntry.setText(texto);

            tvEntry.setTextSize(18);
            tvEntry.setTextColor(Color.WHITE);
            tvEntry.setPadding(0, 8, 0, 8);


            if (topCount == 0) {
                tvEntry.setTypeface(null, Typeface.BOLD);
                tvEntry.setTextSize(20);
            }

            else if (resultado.nombre.equals(nombreJugadorActual)) {
                tvEntry.setTypeface(null, Typeface.BOLD);
            }

            llRankingContenedor.addView(tvEntry);
            topCount++;
        }
    }
}