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
import modele.Livreur;
import modele.Produit;
import modele.ProduitCommande;
import modele.Restaurant;
import service.ServiceMetier;

/**
 *
 * @author tthibault
 */

public class CreerLivreurAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String type = request.getParameter("type");
        String adresse = request.getParameter("adresse");
        double chargeMax = Double.parseDouble(request.getParameter("chargeMax"));
        double vitesse = Double.parseDouble(request.getParameter("vitesse"));
        String nom = request.getParameter("nom");
        
        Livreur livreur = null;
        
        try 
        {
            if(type=="drone")
            {
                livreur = ServiceMetier.AjouterDrone(chargeMax, vitesse, adresse);
            }
            else
            {
                livreur = ServiceMetier.AjouterVelo(chargeMax, nom, adresse);
            }
        } 
        catch (Throwable ex) 
        {
            Logger.getLogger(ModifInfosAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("livreur", livreur);
    }
}
