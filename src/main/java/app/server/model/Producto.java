package app.server.model;

import app.server.resources.RegistrosRutaServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;


public class Producto {
    
    private String nombre = null;
    private int votos = 0;
    private File archivo;

    
    public Producto(File archivo){
        this.nombre = obtenerNombreSinExtension(Path.of(archivo.getPath()));
        this.archivo = archivo;
        this.votos = contarVotos();
    }
    
    public Producto(String nombre, int votos){
        this.nombre  = nombre;
        this.votos = votos;
    }
    
    public int contarVotos(){
        int conteo_votos = 0;
        
        try ( Scanner br = new Scanner(new FileReader(archivo)) ) {
            String linea;

            //contamos cada linea del archivo como voto
            while (br.hasNext()) {
                br.next();
                conteo_votos++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conteo_votos;
    }

    private String obtenerNombreSinExtension(Path path) {
        String nombreArchivo = path.getFileName().toString();
        int indicePunto = nombreArchivo.lastIndexOf('.');
        if (indicePunto > 0) {
            return nombreArchivo.substring(0, indicePunto);
        }
        return nombreArchivo;
    }
    
    public void agregarVoto(){
        this.votos++;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public int getVotos() {
        return votos;
    }
    
    public void setArchivo(File archivo){
        archivo = archivo;
    }
    
    public File getArchivo(){
        return this.archivo;
    }
}


