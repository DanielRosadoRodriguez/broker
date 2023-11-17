package app.mvc;

import app.Exceptions.NullJsonException;
import app.mvc.controller.ResourcesClientes;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class Cliente {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ResourcesClientes resourcesClientes = new ResourcesClientes();
    private JsonObject response;

    public Cliente(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (ConnectException e) {
            System.out.println(e.getMessage() + " ;" + e.getCause() + " ;" + " ;" + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject sendMessageVotar(String registro) throws Exception {
        try {
            /*
             * Este metodo solo manda el servicio directo al server
             */
            JsonObject message = this.resourcesClientes.votar(registro);
            System.out.println("json votar: " + message);
            Gson gson = new Gson();
            out.println(gson.toJson(message));
            // AQUI ES EL PROBLEMAAA

            String respuestaServidor = in.readLine();
            System.out.println("despues del in (ya no imprime)");

            System.out.println("Respuesta del broker al cliente para votar");
            System.out.println("respuesta del servidor" + ":" + respuestaServidor);

            this.response = gson.fromJson(respuestaServidor, JsonObject.class);
            System.out.println("despues del gson (ya no imprime)");
            return this.response;
        } catch (Exception e) {
            throw new Exception("UNDEFINED EXCEPTION - Al votar");
        }
    }

    public JsonObject contarProductos() throws NullJsonException {
        try {
            /*
             * Este metodo solo manda el servicio directo al server
             */
            JsonObject message = this.resourcesClientes.contar();
            Gson gson = new Gson();
            out.println(gson.toJson(message));
            String respuestaServidor = in.readLine();

            System.out.println("Respuesta del broker al cliente para contar");
            System.out.println(respuestaServidor);

            this.response = gson.fromJson(respuestaServidor, JsonObject.class);

            return this.response;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new NullJsonException("Contar productos json es null");
        }

    }
}
