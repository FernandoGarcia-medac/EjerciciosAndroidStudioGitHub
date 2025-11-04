package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject; // Importamos JSONObject
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FinalActivity extends AppCompatActivity {

    private TextView tvResultado;
    private TextView tvNotaFinal;
    private ImageView ivResultado;
    private Button btnVolver;
    private LinearLayout llRankingContenedor;

    public static final String EXTRA_NOMBRE_USUARIO = "EXTRA_NOMBRE_USUARIO";
    public static final String EXTRA_PUNTUACION = "EXTRA_PUNTUACION";

    private static final String PREFS_NAME = "QuizPrefs";
    private static final String KEY_RANKING = "RankingList";

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // 1. Enlazar vistas
        tvResultado = findViewById(R.id.tv_mensaje);
        tvNotaFinal = findViewById(R.id.tv_puntuacion);
        ivResultado = findViewById(R.id.iv_resultado);
        btnVolver = findViewById(R.id.btn_volver_quiz);
        llRankingContenedor = findViewById(R.id.ll_ranking_contenedor);

        // 2. Recibir datos
        Intent intent = getIntent();
        String nombreUsuario = "Fer";
        int notaFinal = 0;

        if (intent.hasExtra(EXTRA_NOMBRE_USUARIO)) {
            nombreUsuario = intent.getStringExtra(EXTRA_NOMBRE_USUARIO);
        }
        if (intent.hasExtra(EXTRA_PUNTUACION)) {
            notaFinal = intent.getIntExtra(EXTRA_PUNTUACION, 0);
        }

        // 3. Guardar el nuevo resultado y mostrar el ranking
        guardarResultado(nombreUsuario, notaFinal);
        mostrarRanking(nombreUsuario); // Pasamos el nombre para que te resalte

        // 4. Actualizar el resultado visual (Aprobado/Suspenso)
        if (notaFinal >= 5) {
            tvResultado.setText("¡Felicidades, " + nombreUsuario + "! ¡APROBADO!");
            ivResultado.setImageResource(R.drawable.aprobado);
            tvNotaFinal.setText("Nota Final: " + notaFinal + " / 10");
            tvNotaFinal.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            tvResultado.setText("Lo siento, " + nombreUsuario + ". Has SUSPENDIDO.");
            ivResultado.setImageResource(R.drawable.caca);
            tvNotaFinal.setText("Nota Final: " + notaFinal + " / 10");
            tvNotaFinal.setTextColor(Color.parseColor("#F44336"));
        }

        // 5. Botón Volver
        btnVolver.setOnClickListener(v -> {
            Intent mainIntent = new Intent(FinalActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        });
    }

    /**
     * Guarda el resultado actual como JSON String en SharedPreferences.
     */
    private void guardarResultado(String nombre, int puntuacion) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String jsonRanking = prefs.getString(KEY_RANKING, null);

        ArrayList<String> ranking;
        if (jsonRanking == null) {
            ranking = new ArrayList<>();
        } else {
            // Deserializa la lista de JSON Strings
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ranking = gson.fromJson(jsonRanking, type);
        }

        // Crear el nuevo resultado como JSON String
        JSONObject nuevoResultado = new JSONObject();
        try {
            nuevoResultado.put("nombre", nombre);
            nuevoResultado.put("puntuacion", puntuacion);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        // Añade el nuevo resultado
        ranking.add(nuevoResultado.toString());

        // Ordenar la lista (comparando las puntuaciones dentro de cada JSON String)
        Collections.sort(ranking, new Comparator<String>() {
            @Override
            public int compare(String r1, String r2) {
                try {
                    int p1 = new JSONObject(r1).getInt("puntuacion");
                    int p2 = new JSONObject(r2).getInt("puntuacion");
                    // Orden descendente (de mayor a menor)
                    return Integer.compare(p2, p1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        // Limita la lista al Top 10
        if (ranking.size() > 10) {
            ranking = new ArrayList<>(ranking.subList(0, 10));
        }

        // Serializa la lista actualizada y guárdala
        String updatedJson = gson.toJson(ranking);
        prefs.edit().putString(KEY_RANKING, updatedJson).apply();
    }

    /**
     * Muestra el ranking Top 5 en el LinearLayout.
     */
    private void mostrarRanking(String nombreJugadorActual) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String jsonRanking = prefs.getString(KEY_RANKING, null);

        if (jsonRanking == null) return;

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> rankingStrings = gson.fromJson(jsonRanking, type);

        llRankingContenedor.removeAllViews(); // Limpiar vistas

        int topCount = 0;
        for (String resultadoString : rankingStrings) {
            if (topCount >= 5) break;

            try {
                JSONObject resultado = new JSONObject(resultadoString);
                String nombre = resultado.getString("nombre");
                int puntuacion = resultado.getInt("puntuacion");

                // Crear un TextView para la entrada
                TextView tvEntry = new TextView(this);

                // Formatear el texto
                String texto = (topCount + 1) + ". " + nombre + " - " + puntuacion + "/10";
                tvEntry.setText(texto);

                tvEntry.setTextSize(18);
                tvEntry.setTextColor(Color.WHITE);
                tvEntry.setPadding(0, 8, 0, 8);

                // Resaltar el primer puesto (Campeón)
                if (topCount == 0) {
                    tvEntry.setTextColor(Color.parseColor("#FFD700")); // Oro
                    tvEntry.setTypeface(null, Typeface.BOLD);
                    tvEntry.setTextSize(20);
                }
                // Resaltar tu entrada actual
                else if (nombre.equals(nombreJugadorActual) && puntuacion == resultado.getInt("puntuacion")) {
                    tvEntry.setTextColor(Color.parseColor("#1E88E5")); // Azul, destacando al jugador
                    tvEntry.setTypeface(null, Typeface.BOLD);
                }

                llRankingContenedor.addView(tvEntry);
                topCount++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}