package app.mvc.controller;

import app.Exceptions.NullJsonException;
import app.mvc.Cliente;

import app.mvc.model.Bitacora;
import app.mvc.model.GraficaModelo;

import app.mvc.view.GraficaBarrasVista;
import app.mvc.view.GraficaPastelVista;
import app.mvc.view.VotacionesVista;
import com.google.gson.JsonObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;

public class ControladorVotaciones implements ActionListener {
    private VotacionesVista votacionesVista;

    private JsonObject jsonProductos;
    private Cliente cliente;

    private ArrayList<ProductoCliente> productosArray;

    public ControladorVotaciones(VotacionesVista votacionesVista, Cliente cliente) {

        this.setCliente(cliente);

        this.votacionesVista = votacionesVista;
        try {
            cliente.sendMessageVotar("primero");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println("antes de cliente.contarProductos()");
            JsonObject clienteProductos = cliente.contarProductos();
            System.out.println("clienteProductos: " + clienteProductos);
            this.setJsonProductos(clienteProductos);
        } catch (NullJsonException e) {
            System.out.println("Error al obtener los productos en construc cliente.contarProductos()");
        } catch (Exception e) {
            System.out.println("ERROR DESCONOCIDO EN CONTROLADOR VOTACIONES");
        }

        iniciarComponentesGraficos();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Bitacora bitacora = new Bitacora();
        JsonObject productos = null;
        if (e.getSource() == votacionesVista.votarBtnProducto1) {
            JsonObject respuestaServer;
            try {
                respuestaServer = cliente.sendMessageVotar("primero");
                String votaciones = respuestaServer.get("respuesta1").getAsString();
                actualizarContadorEnPantalla(8, this.votacionesVista.producto1ContadorLabel);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == votacionesVista.votarBtnProducto2) {
            try {
                JsonObject respuestaServer = cliente.sendMessageVotar("segundo");
                Integer votaciones = respuestaServer.get("valor2").getAsInt();
                actualizarContadorEnPantalla(8, this.votacionesVista.producto2ContadorLabel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == votacionesVista.votarBtnProducto3) {
            try {
                JsonObject respuestaServer = cliente.sendMessageVotar("tercero");
                Integer votaciones = respuestaServer.get("valor2").getAsInt();
                actualizarContadorEnPantalla(8, this.votacionesVista.producto3ContadorLabel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == votacionesVista.verGraficasBtn) {
            try {
                System.out.println("1");
                productos = cliente.contarProductos();
                System.out.println("2");
                this.productosArray = crearArregloProductos();
                GraficaModelo graficaModelo = new GraficaModelo(this.productosArray);
                GraficaBarrasVista graficaBarrasVista = new GraficaBarrasVista();
                this.controladorGraficaBarras = new BarrasControl(graficaModelo, graficaBarrasVista);
                GraficaPastelVista graficaPastelVista = new GraficaPastelVista();
                controladorGraficaBarras.refresh(productos);
                this.controladorGraficaPastel = new PastelControl(graficaModelo, graficaPastelVista);
                controladorGraficaPastel.refresh(productos);
            } catch (Exception exception) {
                System.out.println("Error al obtener los productos");
            }

        }

        refresh(productos);
    }

    private PastelControl controladorGraficaPastel;
    private BarrasControl controladorGraficaBarras;

    private void refresh(JsonObject jsonProductos) {
        this.controladorGraficaPastel.refresh(jsonProductos);
        this.controladorGraficaBarras.refresh(jsonProductos);
    }

    private ArrayList crearArregloProductos() {
        ArrayList<ProductoCliente> productoClientes = new ArrayList<ProductoCliente>();
        productoClientes.add(new ProductoCliente(
                this.jsonProductos.get("respuesta1").getAsString(),
                this.jsonProductos.get("valor1").getAsInt()));
        productoClientes.add(new ProductoCliente(
                this.jsonProductos.get("respuesta2").getAsString(),
                this.jsonProductos.get("valor2").getAsInt()));
        productoClientes.add(new ProductoCliente(
                this.jsonProductos.get("respuesta3").getAsString(),
                this.jsonProductos.get("valor3").getAsInt()));
        return productoClientes;
    }

    private void iniciarComponentesGraficos() {
        this.votacionesVista.verGraficasBtn.addActionListener(this);
        this.votacionesVista.votarBtnProducto1.addActionListener(this);
        this.votacionesVista.votarBtnProducto2.addActionListener(this);
        this.votacionesVista.votarBtnProducto3.addActionListener(this);
        // Obtener los nombres de los archivos sin extensión y mostrar en pantalla
        String nombreProducto1 = this.jsonProductos.get("respuesta1").getAsString();
        this.votacionesVista.producto1Label.setText(nombreProducto1);
        String nombreProducto2 = this.jsonProductos.get("respuesta2").getAsString();
        ;
        this.votacionesVista.producto2Label.setText(nombreProducto2);
        String nombreProducto3 = this.jsonProductos.get("respuesta3").getAsString();
        ;
        this.votacionesVista.producto3Label.setText(nombreProducto3);
        // Contar cantidad de votos y mostrar en pantalla
        int votosPrimero = this.jsonProductos.get("valor1").getAsInt();
        int votosSegundo = this.jsonProductos.get("valor2").getAsInt();
        int votosTercero = this.jsonProductos.get("valor3").getAsInt();
        actualizarContadorEnPantalla(votosPrimero, this.votacionesVista.producto1ContadorLabel);
        actualizarContadorEnPantalla(votosSegundo, this.votacionesVista.producto2ContadorLabel);
        actualizarContadorEnPantalla(votosTercero, this.votacionesVista.producto3ContadorLabel);

    }

    private void actualizarContadorEnPantalla(Integer votos, JLabel contadorPorActualizar) {
        contadorPorActualizar.setText(votos.toString());
    }

    public VotacionesVista getVotacionesVista() {
        return votacionesVista;
    }

    public void setVotacionesVista(VotacionesVista votacionesVista) {
        this.votacionesVista = votacionesVista;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("El cliente no puede ser nulo");
        }
        this.cliente = cliente;
    }

    public void setJsonProductos(JsonObject jsonProductos) {
        if (jsonProductos == null) {
            throw new NullPointerException("El json de productos no puede ser nulo");
        }
        this.jsonProductos = jsonProductos;
    }

}
