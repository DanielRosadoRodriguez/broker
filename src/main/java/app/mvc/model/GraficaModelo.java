package app.mvc.model;

import app.mvc.controller.ProductoCliente;
import app.server.model.Producto;

import java.util.ArrayList;

public class GraficaModelo {
    
    public ArrayList<ProductoCliente> productos = new ArrayList<>();

    public GraficaModelo(ArrayList<ProductoCliente> productos) {
        this.productos = productos;
    }

}
