package app.broker;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Broker {
    /*
    *
    * El siguiente código tiene la finalidad de registrar servidores, cuando se
    * invoque al servidor.
    *
    *  */
    private PrintWriter out;
    private BufferedReader in;
    private ServerSocket serverSocket;

    private int portBroker;
    private List<JsonObject> serversServices = new ArrayList<>();
    public Broker(int portBroker){
        /*
        * Las siguientes sentiencas inician el servidor puerto por el cual se estará
        * escuchan y los servidores deben de solicitar unirse mandando un Json
        * con la clave de "register" para su llave "servicio".
        * Por cada solicitud se creara un hilo el cual contendrás su input y registrara si el correspondiente tiene
        * el formato solicitado. Esto haciendolo en forma de hilos


            this.serverSocket = new ServerSocket(this.portBroker);
            this.serverSocket.setSoTimeout(100);

            while (!this.serverSocket.isClosed()){
                try {
                    Socket socketServer = this.serverSocket.accept();
                    ThreadEchoHandlerBroker threadEchoHandlerBroker = new ThreadEchoHandlerBroker(socketServer, serversServices);
                    Thread thread = new Thread(threadEchoHandlerBroker);
                    thread.start();
                } catch (SocketTimeoutException e){
                    System.out.println("Tiempo de espera agotado");
                    this.serverSocket.close();
                }

            }
            this.serverSocket.close();
        }catch (SocketTimeoutException e){
            System.out.println("Nothing request");
        } catch (SocketException e) {
            System.out.println("No se pudo iniciar el socket");
        } catch (IOException e) {
            System.out.println("Algo falló en el I/O");
        }
        upBrokerForPetitions();*/
    }

    public void upBrokerForPetitions(){
        try {
            this.serverSocket = new ServerSocket(7777);

            System.out.println("se a levantado el broker para peticiones del cliente");

            while (true) {
                Socket petitionSocket = serverSocket.accept();

                ThreadEchoHandlerBroker threadEchoHandlerServer =
                        new ThreadEchoHandlerBroker(petitionSocket, serversServices);

                Thread threadRequestBroker = new Thread(threadEchoHandlerServer);

                threadRequestBroker.start();
            }

    } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if (this.serverSocket != null && !this.serverSocket.isClosed()) {
                    this.serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Broker broker = new Broker(7777);
        broker.upBrokerForPetitions();
    }
}
