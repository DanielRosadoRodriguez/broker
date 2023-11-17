package app.server.controllerServer;

import app.mvc.RegistrosRuta;
import app.server.model.AdministradorProductoServer;
import app.server.model.Producto;
import app.server.model.dao.VotosDAO;
import app.server.resources.RegistrosRutaServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ControllerServer {
    private AdministradorProductoServer administradorProductoServer = new AdministradorProductoServer(
            new File(RegistrosRutaServer.primerRegistro),
            new File(RegistrosRutaServer.segudoRegistro),
            new File(RegistrosRutaServer.tercerRegistro)
    );

    private VotosDAO votosDAO = new VotosDAO();

    public Producto[] countAll(){
        Producto[] productos = new Producto[3];

        productos[0] = administradorProductoServer.getProducto1();
        productos[1] = administradorProductoServer.getProducto2();
        productos[2] = administradorProductoServer.getProducto3();
        return productos;
    }
}
