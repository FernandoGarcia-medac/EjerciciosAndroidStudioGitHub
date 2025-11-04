package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
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

    // Constantes para recibir datos del Intent
    public static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    public static final String EXTRA_PUNTUACION_ACTUAL = "EXTRA_PUNTUACION_ACTUAL";
    private static final int NOTA_APROBADO = 5;
    private static final int RANKING_LIMIT = 5;

    // Componentes UI
    private TextView tvMensajeFinal;
    private TextView tvNotaObtenida;
    private ImageView ivResultadoImagen;
    private LinearLayout llRankingContenedor;
    private Button btnReiniciarQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // Inicializar la base de datos
        dbHelper = new DBHelper(this);

        // Referencias UI
        tvMensajeFinal = findViewById(R.id.tv_mensaje_final);
        tvNotaObtenida = findViewById(R.id.tv_nota_obtenida);
        ivResultadoImagen = findViewById(R.id.iv_resultado_imagen);
        llRankingContenedor = findViewById(R.id.ll_ranking_contenedor);
        btnReiniciarQuiz = findViewById(R.id.btn_reiniciar_quiz);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String nombreUsuario = intent.getStringExtra(EXTRA_NOMBRE_USUARIO);
        int puntuacionFinal = intent.getIntExtra(EXTRA_PUNTUACION_ACTUAL, 0);

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            nombreUsuario = "Jugador Anónimo";
        }

        // Mostrar resultado
        mostrarResultado(nombreUsuario, puntuacionFinal);

        // Guardar puntuación
        dbHelper.guardarResultado(nombreUsuario, puntuacionFinal);

        // Mostrar ranking
        mostrarRanking();

        // Botón reiniciar quiz
        btnReiniciarQuiz.setOnClickListener(v -> {
            Intent i = new Intent(FinalActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    /**
     * Muestra el mensaje e imagen de aprobado/suspenso.
     */
    private void mostrarResultado(String nombre, int puntuacion) {
        tvNotaObtenida.setText(String.format("Nota Final: %d/10", puntuacion));

        if (puntuacion >= NOTA_APROBADO) {
            tvMensajeFinal.setText(String.format("¡Felicidades, %s! Has APROBADO.", nombre));
            ivResultadoImagen.setImageResource(R.drawable.aprobado); // Imagen de aprobado
            tvMensajeFinal.setTextColor(ContextCompat.getColor(this, R.color.colorAprobado));
        } else {
            tvMensajeFinal.setText(String.format("Lo siento, %s. Has SUSPENDIDO.", nombre));
            ivResultadoImagen.setImageResource(R.drawable.suspenso); // Imagen de suspenso
            tvMensajeFinal.setTextColor(ContextCompat.getColor(this, R.color.colorSuspenso));
        }
    }

    /**
     * Carga el ranking desde la base de datos.
     */
    private void mostrarRanking() {
        llRankingContenedor.removeAllViews();

        TextView titulo = new TextView(this);
        titulo.setText("🏆 TOP 5 JUGADORES 🏆");
        titulo.setTextSize(20);
        titulo.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        titulo.setGravity(Gravity.CENTER);
        llRankingContenedor.addView(titulo);

        List<DBHelper.RankingEntry> ranking = dbHelper.obtenerRanking(RANKING_LIMIT);

        if (ranking == null || ranking.isEmpty()) {
            TextView tvNoData = new TextView(this);
            tvNoData.setText("Aún no hay resultados en el ranking.");
            tvNoData.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
            tvNoData.setGravity(Gravity.CENTER);
            llRankingContenedor.addView(tvNoData);
            return;
        }

        int posicion = 1;
        for (DBHelper.RankingEntry entry : ranking) {
            TextView tvEntry = new TextView(this);
            tvEntry.setText(String.format("%d. %s — %d/10", posicion++, entry.nombre, entry.puntuacion));
            tvEntry.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            tvEntry.setTextSize(18);
            tvEntry.setGravity(Gravity.CENTER_HORIZONTAL);

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
