package com.mycompany.gustatifihm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import modele.Client;
import modele.Commande;
import modele.Drone;
import modele.Livreur;
import modele.Produit;
import modele.ProduitCommande;
import modele.Restaurant;
import modele.Velo;
import service.ServiceMetier;

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
            jsonRestaurant.addProperty("adresse", r.getAdresse());
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
    
    public void printState(PrintWriter out, boolean state)
    {
        JsonObject jsonState = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        jsonState.addProperty("state", state);
        String json = gson.toJson(jsonState);      
        out.println(json);
    }
    
    public void printCreerCommande(PrintWriter out, Commande commande)
    {
        JsonObject jsonState = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        if(commande == null)
        {
            jsonState.addProperty("retour", -1);
        }
        else
        {
            jsonState.addProperty("retour", commande.getId());
        }
        String json = gson.toJson(jsonState);      
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
        //System.out.print(commande);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonCommande = new JsonObject();

        jsonCommande.addProperty("id", commande.getId());
        jsonCommande.addProperty("dateDebut", commande.getDateDebut().toString());
        
        String zeroMonth ="";
        if(commande.getDateDebut().getMonth() < 9)
            zeroMonth = "0";
        jsonCommande.addProperty("dateJour", commande.getDateDebut().getDate()+"/"+zeroMonth+(commande.getDateDebut().getMonth()+1)+"/20"+(commande.getDateDebut().getYear()-100));
        
        String etat = commande.getStatus().toString();
        if(etat == "ENCOURS")
        {
            jsonCommande.addProperty("etat", "En cours");   
        }
        else if(etat == "ANNULEE")
        {
            jsonCommande.addProperty("etat", "Annulée");   
        } 
        else if(etat == "VALIDATION")
        {
            jsonCommande.addProperty("etat", "Non Confirmée");   
        }
        else if(etat == "LIVREE")
        {
          jsonCommande.addProperty("etat", "Livrée");   
        }
        
        Date dateFin = commande.getDateFin();
        if(dateFin != null)
        {
            jsonCommande.addProperty("dateFin", dateFin.toString());
            
            String zeroMin ="";
            if(commande.getDateFin().getMinutes() < 10)
                zeroMin = "0";
            jsonCommande.addProperty("heureLivraison", commande.getDateFin().getHours()+"h"+zeroMin+commande.getDateFin().getMinutes());
        }
        else
        {
            jsonCommande.addProperty("dateFin", "Non Livré");
            jsonCommande.addProperty("heureLivraison", "Non Livré");
        }
        
        jsonCommande.addProperty("nomRestaurant", commande.getRestaurant().getDenomination());
        jsonCommande.addProperty("nomClient", commande.getClient().getPrenom()+" "+commande.getClient().getNom());
        jsonCommande.addProperty("nomLivreur", commande.getLivreur().getId());
        jsonCommande.addProperty("prixTotal", ServiceMetier.CalculerPrixTotal(commande));
        
        String zeroMin ="";
        if(commande.getDateDebut().getMinutes() < 10)
                zeroMin = "0";
        
        jsonCommande.addProperty("heureCommande", commande.getDateDebut().getHours()+"h"+zeroMin+commande.getDateDebut().getMinutes());
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
        //System.out.println(json);
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
            
            String zeroMonth ="";
            if(c.getDateDebut().getMonth() < 9)
                zeroMonth = "0";
                
            jsonCommande.addProperty("dateJour", c.getDateDebut().getDate()+"/"+zeroMonth+(c.getDateDebut().getMonth()+1)+"/20"+(c.getDateDebut().getYear()-100));
            
            Date dateFin = c.getDateFin();
            if(dateFin != null)
            {
                jsonCommande.addProperty("dateFin", dateFin.toString());
            }
            else
            {
                jsonCommande.addProperty("dateFin", "Non Livrée");
            }
            
            String etat = c.getStatus().toString();
            
            if(etat == "ENCOURS")
            {
                jsonCommande.addProperty("etat", "En cours");   
            }
            else if(etat == "ANNULEE")
            {
                jsonCommande.addProperty("etat", "Annulée");   
            } 
            else if(etat == "VALIDATION")
            {
                jsonCommande.addProperty("etat", "Non Confirmée");   
            }
            else if(etat == "LIVREE")
            {
                jsonCommande.addProperty("etat", "Livrée");   
            }
            
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
                jsonLivreur.addProperty("type", "Drone");
                jsonLivreur.addProperty("vitesse", ((Drone)l).getVitesseMoy());
                jsonLivreur.addProperty("nom", "-");
            }
            jsonLivreur.addProperty("latitude", l.getLatitude());
            jsonLivreur.addProperty("longitude", l.getLongitude());
            
            String dispo = l.isDispo() ? "Disponible" : "Non disponible";
            jsonLivreur.addProperty("dispo", dispo);
            
            jsonListe.add(jsonLivreur);
        }
        
        JsonObject container = new JsonObject();
        container.add("livreurs", jsonListe);
        String json = gson.toJson(container);
        out.println(json);
    }
    
    public void printInfosClient(PrintWriter out, Client c)
    {
        JsonObject jsonClient = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        jsonClient.addProperty("nom", c.getNom());
        jsonClient.addProperty("prenom", c.getPrenom());
        jsonClient.addProperty("adresse", c.getAdresse());
        jsonClient.addProperty("mail", c.getMail());
        
        String json = gson.toJson(jsonClient);      
        out.println(json);
    }
}
