import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class cliente {
    public static void main(String[] args) {

        Socket socket_cliente;
        try {
            socket_cliente = new Socket("localhost", 12345);
            System.out.println("Cliente conectado");
            while (true) {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket_cliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socket_cliente.getOutputStream(), true);
                // escribir datos mensaje de salida
                String mensaje_enviar = "Hola soy el cliente";
                salida.println(mensaje_enviar);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
