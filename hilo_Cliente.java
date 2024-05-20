import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class hilo_Cliente extends Thread {

    private Socket socket_cliente;
    private List<Pregunta> preguntas;

    public hilo_Cliente(Socket socket_cliente) {
        this.socket_cliente = socket_cliente;
        this.preguntas = crearPreguntas();
    }

    private List<Pregunta> crearPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Cuál es la capital de Francia?", "Paris"));
        preguntas.add(new Pregunta("¿Cuántos planetas hay en el sistema solar?", "8"));
        preguntas.add(new Pregunta("¿Cuál es el elemento químico con símbolo 'O'?", "Oxigeno"));
        preguntas.add(new Pregunta("¿Quién escribió 'Don Quijote'?", "Cervantes"));
        preguntas.add(new Pregunta("¿En qué año llegó el hombre a la luna?", "1969"));
        return preguntas;
    }

    public void run() {
        PrintWriter salida = null;
        BufferedReader entrada = null;
        try {
            entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
            salida = new PrintWriter(socket_cliente.getOutputStream(), true);

            int puntaje = 0; // Inicializa el puntaje

            for (Pregunta pregunta : this.preguntas) {
                // Enviar pregunta al cliente
                salida.println(pregunta.getPregunta());

                // Leer respuesta del cliente
                String respuestaCliente = entrada.readLine();

                // Evaluar respuesta y enviar resultado al cliente
                if (respuestaCliente != null && respuestaCliente.equalsIgnoreCase(pregunta.getRespuestaCorrecta())) {
                    salida.println("Correcto");
                    puntaje++; // Incrementar el puntaje si la respuesta es correcta
                } else {
                    salida.println("Incorrecto, la respuesta correcta es: " + pregunta.getRespuestaCorrecta());
                }
            }
            // Enviar el puntaje final al cliente
            salida.println("No hay más preguntas. Gracias por participar.");
            salida.println("Tu puntaje final es: " + puntaje);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (entrada != null) entrada.close();
                if (salida != null) salida.close();
                if (socket_cliente != null && !socket_cliente.isClosed()) socket_cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Clase Pregunta para representar cada pregunta y su respuesta correcta
    private static class Pregunta {
        private String pregunta;
        private String respuestaCorrecta;

        public Pregunta(String pregunta, String respuestaCorrecta) {
            this.pregunta = pregunta;
            this.respuestaCorrecta = respuestaCorrecta;
        }

        public String getPregunta() {
            return pregunta;
        }

        public String getRespuestaCorrecta() {
            return respuestaCorrecta;
        }
    }
}
