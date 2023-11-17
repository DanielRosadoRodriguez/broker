package app.MCCOld.client;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Arrays;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String ip, int port){
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (ConnectException e){
            System.out.println(e.getMessage() + " ;" + e.getCause() + " ;" + " ;" + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        SwingUtilities.invokeLater(() -> {
            JsonObject message = new JsonObject();
            message.addProperty("servicio", "votar");
            message.addProperty("text", "Este es mi mensaje para el servidor");
            Gson gson = new Gson();
            out.println(gson.toJson(message));
            String resp;

            try {
                String respuestaServidor = in.readLine();
                System.out.println(respuestaServidor);
                JsonObject jsonRespuestaServer = gson.fromJson(respuestaServidor, JsonObject.class);
                String tipoRespuestaServidor = jsonRespuestaServer.get("type").getAsString();
                if (tipoRespuestaServidor.equals("response")) {
                    String mensaje = jsonRespuestaServer.get("text").getAsString();
                    // Actualiza la interfaz de usuario con la respuesta del servidor
                    // Puedes mostrar el mensaje en un cuadro de texto, etiqueta, etc.
                    System.out.println(mensaje);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Maneja la excepción según tus necesidades
            } finally {
                try {
                    // Cierra la conexión en el hilo de fondo
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // Maneja la excepción según tus necesidades
                }
            }
        });

    }
}
