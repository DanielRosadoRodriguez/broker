package app.mvc.controller;

import app.mvc.Cliente;
import app.mvc.model.GraficaModelo;

import app.mvc.view.GraficaBarrasVista;
import com.google.gson.JsonObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class BarrasControl implements ActionListener {

    public GraficaModelo modelo;
    public GraficaBarrasVista vista;

    public BarrasControl(GraficaModelo modelo, GraficaBarrasVista vista) {
        this.modelo = modelo;
        this.vista = vista;
        cargarVista();
        this.vista.getBtnCerrar().addActionListener(this);
    }

    public void cargarVista() {
        this.vista.setVisible(true);
        this.vista.listado = modelo.productos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.getBtnCerrar())) {
            this.vista.listado = modelo.productos;
            this.vista.setVisible(false);
        }
    }
    
       private ArrayList productos;
    
    public void refresh(JsonObject productosActualizados) {
        ArrayList<ProductoCliente> productos = new ArrayList<ProductoCliente>();

        ProductoCliente p1 = new ProductoCliente(
                productosActualizados.get("respuesta1").getAsString(),
                productosActualizados.get("valor1").getAsInt()
        );
        ProductoCliente p2 = new ProductoCliente(
                productosActualizados.get("respuesta2").getAsString(),
                productosActualizados.get("valor2").getAsInt()
        );
        ProductoCliente p3 = new ProductoCliente(
                productosActualizados.get("respuesta3").getAsString(),
                productosActualizados.get("valor3").getAsInt()
        );
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);

        this.modelo = new GraficaModelo(productos);
        this.vista.listado = modelo.productos;
        this.vista.repaint();

    }
}