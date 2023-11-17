package app.server.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorFecha {
    public static String obtenerFechaYHora(){
        LocalDateTime actualidad = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        String fecha_voto = actualidad.format(formato);
        return fecha_voto;
    }
}
