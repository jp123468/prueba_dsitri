import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class servidor {

    public static void main(String[] args) throws IOException {

        try (ServerSocket socket_servidor = new ServerSocket(12345)) {
            System.out.println("Esperando Conexiones....");
            while (true) {
                // esperar y aceptar respuesta
                Socket socket_cliente = socket_servidor.accept();

                // hilo para cada cliente
                hilo_Cliente hilo = new hilo_Cliente(socket_cliente);
                hilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
