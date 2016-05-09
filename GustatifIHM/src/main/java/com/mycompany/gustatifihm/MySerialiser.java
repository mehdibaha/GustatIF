package com.mycompany.gustatifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.List;
import modele.Client;
import modele.Restaurant;

public class MySerialiser {
    public void printListRestaurants(PrintWriter out, List<Restaurant> restaurants)
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
    
    public void printFicheRestaurant(PrintWriter out, Restaurant r)
    {
        JsonObject jsonRestaurant = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        jsonRestaurant.addProperty("denomination", r.getDenomination());
        jsonRestaurant.addProperty("adresse", r.getAdresse());
        jsonRestaurant.addProperty("description", r.getDescription());
        
        String json = gson.toJson(jsonRestaurant);      
        out.println(json);
    }
    
    public void printConnexionFail(PrintWriter out)
    {
        JsonObject jsonFail = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        jsonFail.addProperty("state", false);
        String json = gson.toJson(jsonFail);      
        out.println(json);
    }
    
    public void printConnexion(PrintWriter out, Client c)
    {
        JsonObject jsonConnexion = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        jsonConnexion.addProperty("id", c.getId());
        String json = gson.toJson(jsonConnexion);      
        out.println(json);
    }
}
