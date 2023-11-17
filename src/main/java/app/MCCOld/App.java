package app.MCCOld;

import app.MCCOld.client.Client;
import app.MCCOld.controller.Controller;
import app.MCCOld.view.UsuarioView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        UsuarioView usuarioView = new UsuarioView();
        Client client = new Client("127.0.0.1", 7777);
        Controller controller = new Controller(usuarioView, client);
        usuarioView.setVisible(true);
    }
}
