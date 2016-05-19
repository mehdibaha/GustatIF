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

public class CreerCommandeAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        long idRestaurant = Long.parseLong(request.getParameter("idRestaurant"));
        String produits = request.getParameter("produitsCom");
        //System.out.println(produits);
        JsonArray entries = (JsonArray) new JsonParser().parse(produits);
        //System.out.println(entries);
        
        List<ProduitCommande> listProduits = new LinkedList<ProduitCommande> ();
        
        for (JsonElement entry : entries)
        {
            long id = ((JsonObject) entry).get("id").getAsLong();
            int quantite = ((JsonObject) entry).get("quantitee").getAsInt();
            
            Produit leProduit=null;
            if(quantite > 0)
            {
                try {
                    leProduit = ServiceMetier.TrouverProduitParId(id);
                } catch (Throwable ex) {
                    Logger.getLogger(CreerCommandeAction.class.getName()).log(Level.SEVERE, null, ex);
                }

                listProduits.add(new ProduitCommande(quantite, leProduit));
            }
        }
        
        //System.out.println(listProduits);
        
        long idClient = (Long) request.getSession(true).getAttribute("id");
        Commande commande = null;
        
        try 
        {
            Client client = ServiceMetier.TrouverClientParId(idClient);
            Restaurant resto = ServiceMetier.TrouverRestaurantParId(idRestaurant);
            commande = ServiceMetier.CreerCommande(client, resto, listProduits);
        } catch (Throwable ex) {
            Logger.getLogger(ModifInfosAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("commande", commande);
    }
}
