/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Client;
import modele.Commande;
import modele.Produit;
import modele.ProduitCommande;
import modele.Restaurant;
import service.ServiceMetier;

/**
 *
 * @author tthibault
 */

public class CreerProduitAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String denomination = request.getParameter("denomination");
        String description = request.getParameter("description");
        float prix = Float.parseFloat(request.getParameter("prix"));
        float poids = Float.parseFloat(request.getParameter("poids"));
        long idRestaurant = Long.parseLong(request.getParameter("idRestaurant"));
        
        Produit produit = null;
        Restaurant resto = null;
        
        try 
        {
            resto = ServiceMetier.TrouverRestaurantParId(idRestaurant);
            if(resto != null)
            {
                // Le service métier pour associer un produit a un restaurant est manquant. On crée donc le produit sans restaurant.
                produit = ServiceMetier.CreerProduit(denomination, description, prix, poids);  
            }
        } 
        catch (Throwable ex) 
        {
            Logger.getLogger(ModifInfosAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("restaurant", produit);
    }
}
