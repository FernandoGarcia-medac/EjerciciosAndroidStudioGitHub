package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Muestra el resultado final del quiz, guarda la puntuación en la base de datos
 * y muestra el ranking de los mejores jugadores.
 */
public class FinalActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    // Constantes mínimas para el paso de datos (las metemos aquí para no crear la clase Constantes)
    private static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    private static final String EXTRA_PUNTUACION_ACTUAL = "EXTRA_PUNTUACION_ACTUAL";
    private static final int NOTA_APROBADO = 5;
    private static final int RANKING_LIMIT = 5;

    // Componentes UI
    private TextView tvMensajeFinal;
    private TextView tvNotaObtenida;
    private ImageView ivResultadoImagen;
    private LinearLayout llRankingContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // Inicializar la base de datos
        dbHelper = new DBHelper(this);

        // Inicializar componentes UI
        tvMensajeFinal = findViewById(R.id.tv_mensaje_final);
        tvNotaObtenida = findViewById(R.id.tv_nota_obtenida);
        ivResultadoImagen = findViewById(R.id.iv_resultado_imagen);
        llRankingContenedor = findViewById(R.id.ll_ranking_contenedor);

        // 1. Obtener datos del Intent
        Intent intent = getIntent();
        String nombreUsuario = intent.getStringExtra(EXTRA_NOMBRE_USUARIO);
        int puntuacionFinal = intent.getIntExtra(EXTRA_PUNTUACION_ACTUAL, 0);

        if (nombreUsuario == null) {
            nombreUsuario = "Jugador Anónimo";
        }

        // 2. Mostrar el resultado y aplicar el Requisito Extra (aprobado/suspenso)
        mostrarResultado(nombreUsuario, puntuacionFinal);

        // 3. Guardar el resultado en la base de datos
        dbHelper.guardarResultado(nombreUsuario, puntuacionFinal);

        // 4. Cargar y mostrar el ranking
        mostrarRanking();
    }

    /**
     * Muestra la nota, el mensaje y la imagen dependiendo de si el jugador aprueba o suspende.
     */
    private void mostrarResultado(String nombre, int puntuacion) {
        tvNotaObtenida.setText(String.format("Nota Final: %d/10", puntuacion));

        if (puntuacion >= NOTA_APROBADO) {
            // Aprobado (Requisito Extra: Color verde, mensaje positivo, imagen de éxito)
            tvMensajeFinal.setText(String.format("¡Felicidades, %s! Has APROBADO.", nombre));
            ivResultadoImagen.setImageResource(R.drawable.aprobado); // Debes tener un drawable 'aprobado.png' o similar
            tvMensajeFinal.setTextColor(ContextCompat.getColor(this, R.color.colorAprobado)); // Define colorAprobado en colors.xml
        } else {
            // Suspenso (Requisito Extra: Color rojo, mensaje negativo, imagen de fallo)
            tvMensajeFinal.setText(String.format("Lo siento, %s. Has SUSPENDIDO.", nombre));
            ivResultadoImagen.setImageResource(R.drawable.suspenso); // Debes tener un drawable 'suspenso.png' o similar
            tvMensajeFinal.setTextColor(ContextCompat.getColor(this, R.color.colorSuspenso)); // Define colorSuspenso en colors.xml
        }
    }

    /**
     * Obtiene el ranking de la BBDD y lo pinta en el LinearLayout.
     */
    private void mostrarRanking() {
        // Limpiar vistas anteriores
        llRankingContenedor.removeAllViews();

        // Título del Ranking
        TextView tvTituloRanking = new TextView(this);
        tvTituloRanking.setText("TOP 5 JUGADORES");
        tvTituloRanking.setTextSize(20);
        tvTituloRanking.setGravity(Gravity.CENTER);
        llRankingContenedor.addView(tvTituloRanking);

        // Obtener la lista del DBHelper
        List<DBHelper.RankingEntry> ranking = dbHelper.obtenerRanking(RANKING_LIMIT);

        if (ranking.isEmpty()) {
            TextView tvNoData = new TextView(this);
            tvNoData.setText("Aún no hay resultados en el ranking.");
            tvNoData.setGravity(Gravity.CENTER);
            llRankingContenedor.addView(tvNoData);
            return;
        }

        // Mostrar cada entrada del ranking
        int posicion = 1;
        for (DBHelper.RankingEntry entry : ranking) {
            TextView tvEntry = new TextView(this);
            tvEntry.setText(String.format("%d. %s - %d/10", posicion++, entry.nombre, entry.puntuacion));
            tvEntry.setTextSize(18);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            tvEntry.setLayoutParams(params);

            llRankingContenedor.addView(tvEntry);
        }
    }
}
