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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Client;
import modele.Commande;
import modele.Produit;
import modele.ProduitCommande;
import modele.Restaurant;

/**
 *
 * @author tthibault
 */

public class CreerCommandeAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        long idRestaurant = Long.parseLong(request.getParameter("idRestaurant"));
        String produits = request.getParameter("produitsCom");
        System.out.println(produits);
        JsonArray entries = (JsonArray) new JsonParser().parse(produits);
        System.out.println(entries);
        
        for (JsonElement entry : entries)
        {
            long id = ((JsonObject) entry).get("id").getAsLong();
            int quantite = ((JsonObject) entry).get("quantitee").getAsInt();
            
            System.out.println("-> "+id+" ("+quantite+")");
        }
        
        /*
        long idClient = (Long) request.getSession(true).getAttribute("id");
        
        try 
        {
            Client client = serviceMetier.TrouverClientParId(idClient);
            Restaurant resto = serviceMetier.TrouverRestaurantParId(idRestaurant);
            Commande commande = serviceMetier.CreerCommande(client, resto, listProduits);
            this.serviceMetier.ValiderCommande(commande);
            
        } catch (Throwable ex) {
            Logger.getLogger(ModifInfosAction.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
