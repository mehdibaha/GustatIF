/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import modele.*;
import dao.*;
import java.util.List;
import static service.ServiceMetier.*;

/**
 *
 * @author jkancel
 */
public class TestJustin {
    public static void main(String args[]) throws Throwable
    {
        JpaUtil.creerEntityManager();
        //Client client= CreerClient("didi","douillet","7 avenue Jean Capelle, Villeurbanne","mail@testons.com","azerty","azerty",true,true); 
        //RenvoyerMotDePasse("mail@testfinal.com");
        //Restaurant restaurant = CreerRestaurant("La tulipe","Un très joli cadre tulipé","9 avenue Jean Capelle, Villeurbanne");
        //Produit produit = CreerProduit("Colombo cabri","Un colombo des îles",(float) 25,(float) 25);
        //Client clientParId = TrouverClientParId(new Long(1000));
        //ModifierInfoPersos(clientParId,"jeanfi","dupied","7 avenue Jean Capelle, Villeurbanne","mail@testattor.com","azerty","azerty",true,true);
        //Client clientCo = ConnecterClient("nolmeadamarais1551@gmail.com","azerty");
        //List<Restaurant> restoListe = ListerRestaurants();
        //List<Commande> commandeClientListe = ListerCommandesClient(clientParId);
        //List<Produit> produitsResto = ListerProduitsRestaurant(restoListe.get(0));
        //Commande commandeParId = TrouverCommandeParId(new Long(1154));
        //Restaurant restoParId = TrouverRestaurantParId(new Long(3));
        //List<Produit> produitsAll = ListerProduits();
        //List<Commande> commandesAll = ListerCommandes();
        //System.out.println(commandesAll);
        //List<Livreur> livreursAll = ListerLivreurs();
        //List<Client> clientsAll = ListerClients();
        //int totalCommandesCours = NbCommandeEnCours();
        //double prixTotal = CalculerPrixTotal(commandeParId);
        JpaUtil.fermerEntityManager();
    }
}
