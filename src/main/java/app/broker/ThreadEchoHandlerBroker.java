package app.broker;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ThreadEchoHandlerBroker implements Runnable {

    private Socket sockerServer;
    private List<JsonObject> serversServices;
    private String response;
    private Thread thread;

    public ThreadEchoHandlerBroker(Socket socketServer, List<JsonObject> serversServices) {
        this.serversServices = serversServices;
        this.sockerServer = socketServer;
    }

    @Override
    public void run() {
        try {
            // Pasarle el in y out al siguiente ilo para que pueda responerle al cliente
            PrintWriter out = new PrintWriter(this.sockerServer.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(this.sockerServer.getInputStream()));
            String request = in.readLine();
            Gson gson = new Gson();
            JsonObject requestJsonFromClient = gson.fromJson(request, JsonObject.class);
            System.out.println("Llegó la peticion del cliente al broker");
            System.out.println("Esta fue :" + request);

            /*
             * Vemos el servicio que solicita el usuario
             */
            String typeService = requestJsonFromClient.get("servicio").getAsString();
            System.out.println("El servicio del cliente fue : " + typeService);

            switch (typeService) {
                case "regisrar":
                    this.serversServices.add(requestJsonFromClient);
                    System.out.println("Se realizo con exito el registro");
                    break;
                case "votar":
                    System.out.println("Preparando la petición de votar del Broker para mandarlo al server");

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("servicio", "votar");
                    jsonObject.addProperty("variables", 1);
                    jsonObject.addProperty("variable1", requestJsonFromClient.get("variable2").getAsString());
                    jsonObject.addProperty("valor1", 1);

                    TEHBrokerServerRequest tehBrokerServerRequestVotar = new TEHBrokerServerRequest(1013, "127.0.0.1",
                            jsonObject);
                    Thread thread = new Thread(tehBrokerServerRequestVotar);
                    System.out.println("Iniciando Hilo");
                    thread.start();
                    try {
                        thread.join();
                    } catch (Exception e) {
                        System.out.println("Algo paso en el hilo");
                    }

                    System.out.println(tehBrokerServerRequestVotar.getResponse());
                    // Rebote no debe de pasar
                    out.println(tehBrokerServerRequestVotar.getResponse());
                    thread.interrupt();

                    this.response = tehBrokerServerRequestVotar.getResponse();
                    break;
                case "contar":

                    System.out.println("Preparando la petición de contar del Broker para mandarlo al server");
                    TEHBrokerServerRequest tehBrokerServerRequestContar = new TEHBrokerServerRequest(1013, "127.0.0.1",
                            requestJsonFromClient);
                    this.thread = new Thread(tehBrokerServerRequestContar);
                    System.out.println("Iniciando Hilo");
                    this.thread.start();
                    try {
                        this.thread.join();
                    } catch (Exception e) {
                        System.out.println("Algo paso en el hilo");
                    }

                    System.out.println(tehBrokerServerRequestContar.getResponse());
                    // Rebote no debe de pasar
                    out.println(tehBrokerServerRequestContar.getResponse());
                    this.thread.interrupt();
                    break;
                default:
                    System.out.println("Servicio desconocido");
                    break;
            }

            this.sockerServer.close();
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();    
        } 
    }
}
