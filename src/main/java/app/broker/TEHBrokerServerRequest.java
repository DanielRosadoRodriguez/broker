package app.broker;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TEHBrokerServerRequest implements Runnable{
    private int portServer;
    private String idServer;

    private String response;
    private JsonObject requestToServer;
    public TEHBrokerServerRequest(int portServer, String idServer, JsonObject requestToServer) {
        this.portServer = portServer;
        this.idServer = idServer;

        this.requestToServer = requestToServer;
    }

    @Override
    public void run() {
        try(Socket serverRequestSocket = new Socket(this.idServer,
                this.portServer)){
            System.out.println("Realizando conección de Broker al server");
            PrintWriter out = new PrintWriter(serverRequestSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverRequestSocket.getInputStream()));

            out.println(this.requestToServer);
            String respuestaServer = in.readLine();
            this.response = respuestaServer;
            System.out.println("Este es la siguiente respuesta del server al broker: ");
            System.out.println(respuestaServer);
            System.out.println("... Enviando la respuesta al cliente");
            in.close();
            out.close();
        } catch (UnknownHostException e) {
            System.out.println();
        } catch (IOException e) {
            System.out.println();
        }
    }

    public String getResponse() {
        return response;
    }
}
