package com.example.ejercicioevaluable2;


public class PreguntaActivity {

    private String texto_pregunta;
    private String respuesta_correcta;
    private String respuesta_incorrecta_1;
    private String respuesta_incorrecta_2;

    // Constructor
    public PreguntaActivity(String texto_pregunta, String respuesta_correcta, String respuesta_incorrecta_1, String respuesta_incorrecta_2) {
        this.texto_pregunta = texto_pregunta;
        this.respuesta_correcta = respuesta_correcta;
        this.respuesta_incorrecta_1 = respuesta_incorrecta_1;
        this.respuesta_incorrecta_2 = respuesta_incorrecta_2;
    }

    public String getTexto_pregunta() {
        return texto_pregunta;
    }
    public String getRespuesta_correcta() {
        return respuesta_correcta;
    }
    public String getRespuesta_incorrecta_1() {
        return respuesta_incorrecta_1;
    }
    public String getRespuesta_incorrecta_2() {
        return respuesta_incorrecta_2;
    }

}