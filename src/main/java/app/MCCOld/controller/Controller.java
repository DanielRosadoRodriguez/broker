package app.MCCOld.controller;

import app.MCCOld.view.UsuarioView;
import app.MCCOld.client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private UsuarioView vistaUsuario;
    private Client client;
    public Controller(UsuarioView vistaUsuario, Client client) {
        this.vistaUsuario = vistaUsuario;
        this.vistaUsuario.subirBoton.addActionListener(this);
        this.vistaUsuario.listarBoton.addActionListener(this);
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vistaUsuario.listarBoton){
            System.out.println("Ey");
        } else if (e.getSource() == this.vistaUsuario.subirBoton) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    client.sendMessage();
                    return null;
                }
            };
            worker.execute();
        }
    }
}
