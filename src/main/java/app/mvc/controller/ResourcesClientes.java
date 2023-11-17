package app.mvc.controller;

import com.google.gson.JsonObject;

public class ResourcesClientes {

    public JsonObject votar(String registro){
        JsonObject jsonObject  = new JsonObject();
        jsonObject.addProperty("servicio", "ejecutar");
        jsonObject.addProperty("variables", 2);
        jsonObject.addProperty("variable1", "servicio");
        jsonObject.addProperty("valor1", "votar");
        jsonObject.addProperty("variable2", registro);
        jsonObject.addProperty("valor2", "1");
        return jsonObject;
    }

    public JsonObject contar(){
        JsonObject jsonObject  = new JsonObject();
        jsonObject.addProperty("servicio", "contar");
        jsonObject.addProperty("variables", 0);
        return jsonObject;
    }
}
