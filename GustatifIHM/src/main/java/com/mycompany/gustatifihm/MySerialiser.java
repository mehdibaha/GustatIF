package com.mycompany.gustatifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.List;
import modele.Restaurant;

public class MySerialiser {
    public static void printListRestaurants(PrintWriter out, List<Restaurant> restaurants)
    {
        JsonArray jsonListe = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        for (Restaurant r : restaurants)
        {
            JsonObject jsonRestaurant = new JsonObject();
            
            jsonRestaurant.addProperty("id", r.getId());
            jsonRestaurant.addProperty("denomination", r.getDenomination());
            jsonRestaurant.addProperty("description", r.getDescription());
            
            jsonListe.add(jsonRestaurant);
        }
        
        JsonObject container = new JsonObject();
        container.add("restaurants", jsonListe);
        String json = gson.toJson(container);
        out.println(json);
    }
}
