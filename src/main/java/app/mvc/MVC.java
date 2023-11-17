
package app.mvc;

import app.mvc.controller.ControladorVotaciones;
import app.mvc.view.VotacionesVista;
import com.google.gson.JsonObject;

import java.io.File;

public class MVC {

    public static void main(String[] args) {
        Cliente cliente = new Cliente("127.0.0.1", 7777);
        VotacionesVista votacionesVista = new VotacionesVista();
        ControladorVotaciones controladorVotaciones = new ControladorVotaciones(votacionesVista, cliente);
        votacionesVista.setVisible(true);
    }
}
