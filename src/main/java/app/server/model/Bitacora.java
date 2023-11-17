package app.server.model;

import app.mvc.RegistrosRuta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Bitacora {
    private File archivoBitacora = new File(RegistrosRuta.bitacora);
    
    public void escribir(String clase, String descripcion){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoBitacora, true))) {
            writer.write(clase.toString() + " " + descripcion + " "+ " " + GestorFecha.obtenerFechaYHora() + '\n');
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
