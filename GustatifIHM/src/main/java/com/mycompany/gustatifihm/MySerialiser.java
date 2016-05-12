package com.mycompany.gustatifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.List;
import modele.Client;
import modele.Commande;
import modele.Drone;
import modele.Livreur;
import modele.Produit;
import modele.ProduitCommande;
import modele.Restaurant;
import modele.Velo;

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
            jsonRestaurant.addProperty("latitude", r.getLatitude());
            jsonRestaurant.addProperty("longitude", r.getLongitude());
            
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
    
    public void printPlats(PrintWriter out, List<Produit> produits)
    {
        JsonArray jsonListe = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        for (Produit p : produits)
        {
            JsonObject jsonProduit = new JsonObject();
            
            jsonProduit.addProperty("id", p.getId());
            jsonProduit.addProperty("denomination", p.getDenomination());
            jsonProduit.addProperty("description", p.getDescription());
            jsonProduit.addProperty("prix", p.getPrix());
            jsonProduit.addProperty("poids", p.getPoids());
            
            jsonListe.add(jsonProduit);
        }
        
        JsonObject container = new JsonObject();
        container.add("produits", jsonListe);
        String json = gson.toJson(container);
        out.println(json);
    }
    
    public void printInfosCommande(PrintWriter out, Commande commande)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        JsonObject jsonCommande = new JsonObject();

        jsonCommande.addProperty("id", commande.getId());
        jsonCommande.addProperty("dateDebut", commande.getDateDebut().toString());
        jsonCommande.addProperty("dateFin", commande.getDateFin().toString());
        List<ProduitCommande> produits = commande.getProduits();
        
        JsonArray jsonListeProduits = new JsonArray();
        for (ProduitCommande p : produits)
        {
            JsonObject jsonProduit = new JsonObject();
            
            jsonProduit.addProperty("id", p.getProduit().getId());
            jsonProduit.addProperty("denomination", p.getProduit().getDenomination());
            jsonProduit.addProperty("prix", p.getProduit().getPrix());
            jsonProduit.addProperty("quantite", p.getQuantitee());
            
            jsonListeProduits.add(jsonProduit);
        }

        JsonObject containerProduits = new JsonObject();
        containerProduits.add("produits", jsonListeProduits);
        
        jsonCommande.add("produits", jsonListeProduits);

        JsonObject container = new JsonObject();
        container.add("commande", jsonCommande);
        String json = gson.toJson(container);
        out.println(json);
    }
    
    public void printListCommandes(PrintWriter out, List<Commande> commandes)
    {
        JsonArray jsonListe = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        for (Commande c : commandes)
        {
            JsonObject jsonCommande = new JsonObject();
            
            jsonCommande.addProperty("id", c.getId());
            jsonCommande.addProperty("restaurant", c.getRestaurant().getDenomination());
            jsonCommande.addProperty("dateDebut", c.getDateDebut().toString());
            jsonCommande.addProperty("dateFin", c.getDateFin().toString());
            jsonCommande.addProperty("etat", c.getStatus().toString());
            jsonCommande.addProperty("latitude", c.getClient().getLatitude());
            jsonCommande.addProperty("longitude", c.getClient().getLongitude());
            
            jsonListe.add(jsonCommande);
        }
        
        JsonObject container = new JsonObject();
        container.add("commandes", jsonListe);
        String json = gson.toJson(container);
        out.println(json);
    }
    
    public void printListClients(PrintWriter out, List<Client> clients)
    {
        JsonArray jsonListe = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        for (Client c : clients)
        {
            JsonObject jsonClient = new JsonObject();
            
            jsonClient.addProperty("id", c.getId());
            jsonClient.addProperty("prenom", c.getPrenom());
            jsonClient.addProperty("nom", c.getNom());
            jsonClient.addProperty("adresse", c.getAdresse());
            jsonClient.addProperty("email", c.getMail());
            jsonClient.addProperty("latitude", c.getLatitude());
            jsonClient.addProperty("longitude", c.getLongitude());
            
            jsonListe.add(jsonClient);
        }
        
        JsonObject container = new JsonObject();
        container.add("clients", jsonListe);
        String json = gson.toJson(container);
        out.println(json);
    }
    
    public void printListLivreurs(PrintWriter out, List<Livreur> livreurs)
    {
        JsonArray jsonListe = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        for (Livreur l : livreurs)
        {
            JsonObject jsonLivreur = new JsonObject();
            
            jsonLivreur.addProperty("id", l.getId());
            jsonLivreur.addProperty("chargeMax", l.getChargeMax());
            
            if(l instanceof Velo)
            {
                jsonLivreur.addProperty("type", "Vélo");
                jsonLivreur.addProperty("vitesse", "-");
                jsonLivreur.addProperty("nom", ((Velo)l).getName());
            }
            else if(l instanceof Drone)
            { 
                jsonLivreur.addProperty("type", "Vélo");
                jsonLivreur.addProperty("vitesse", ((Drone)l).getVitesseMoy());
                jsonLivreur.addProperty("nom", "-");
            }
            jsonLivreur.addProperty("latitude", l.getLatitude());
            jsonLivreur.addProperty("longitude", l.getLongitude());
            jsonLivreur.addProperty("dispo", l.isDispo());
            
            jsonListe.add(jsonLivreur);
        }
        
        JsonObject container = new JsonObject();
        container.add("livreurs", jsonListe);
        String json = gson.toJson(container);
        out.println(json);
    }
}
