package app.server;


import app.server.resources.JsonRequestsBroker;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Server {

    private Integer port;

    public Server(int port){
        this.port = port;
    }

    public void registerServer(){
        try ( Socket brokerSocket = new Socket("127.0.0.1", 7777) ) {
            PrintWriter out = new PrintWriter( brokerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    brokerSocket.getInputStream()
            ));
            JsonObject requestResgisterJsonObject = JsonRequestsBroker.registerRequest();
            System.out.println("request: " + requestResgisterJsonObject);
            out.println(requestResgisterJsonObject);
            String response = in.readLine();
            System.out.println("response " + response);
        } catch (UnknownHostException e) {
            System.out.println("Connection to broker fail : " + e.getCause());
        } catch (IOException e) {
            System.out.println("Something fail in/out " + e.getMessage());
        }
    }

    public void upServer(){
        try(
                ServerSocket serverSocket = new ServerSocket(this.port);
            ){
            registerServer();
            while (true) {
                Socket petitionSocket = serverSocket.accept();

                ThreadEchoHandlerServer threadEchoHandlerServer =
                        new ThreadEchoHandlerServer(petitionSocket);

                Thread threadRequestServer = new Thread(threadEchoHandlerServer);
                try{
                    threadRequestServer.start();
                    threadRequestServer.join();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (IOException e){
            System.out.println("");
        }
    }


    public static void main(String[] args) {
        Server server = new Server(1013);
        server.registerServer();
        server.upServer();
    }
}
