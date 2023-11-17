package app.server.resources;

import com.google.gson.JsonObject;

public class JsonRequestsBroker {
    //Poner los datos actuales a la hora de una ejecución
    public static JsonObject registerRequest(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("servicio", "registrar");
        jsonObject.addProperty("variables", 4);
        jsonObject.addProperty("variable1", "servidor");
        jsonObject.addProperty("valor1", "148.209.67.45");
        jsonObject.addProperty("variable2", "puerto");
        jsonObject.addProperty("valor2", 75);
        return jsonObject;
    }
}
