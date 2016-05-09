package demo;

import dao.*;
import java.util.Arrays;
import java.util.List;
import modele.*;
import static service.ServiceMetier.*;
import static util.Saisie.*;

public class Main {
    
    public static int Menu(){
        System.out.println(
                "\n\n--- Menu ---\n"
              + " 1) Lister Restaurants\n"  
              + " 2) Lister Produits d'un restaurant\n"
              + " 3) Lister Client\n"
              + " 4) Créer Client\n"
              + " 5) Lister Commandes\n"
              + " 6) Créer Commande\n"
              + " 7) Valider Commande\n"
              + " 8) Annuler Commande\n"
              + " 9) Terminer Commande\n"
              + " 10) Lister Livreurs\n"
              + " 11) Quitter\n"
              + "------------"
        );
        return lireInteger("Votre choix : ");
    }
    
    public static void DisplayTab(List liste){
        for (Object o : liste){
            System.out.println(o);
        }
    }
    
    public static void ProduitsRestaurant() throws Throwable{
        System.out.println("\n\n--- Produits d'un restaurant ---");
        int id = lireInteger("Id du restaurant : ");
        Restaurant r = TrouverRestaurantParId(new Long(id));
        DisplayTab(ListerProduitsRestaurant(r));
    }
    
    public static void AddClient() throws Throwable{
        System.out.println("\n\n--- Création d'un client (simplifié) ---");
        String nom = lireChaine("Nom : ");
        String prenom = lireChaine("Prénom : ");
        String adr = lireChaine("Adresse (réelle) : ");
        String mail = lireChaine("Mail : ");
        String mdp = lireChaine("Mot de passe : ");
        Client c = CreerClient(nom, prenom, adr, mail, mdp, mdp, true, true);
        System.out.println(c);
    }
    
    public static void AddCommande() throws Throwable{
        System.out.println("\n\n--- Création d'une Commande (simplifié, produits ajoutés automatiquement)---");
        Long idClient = new Long(lireInteger("Id Client : "));
        Long idRest = new Long(lireInteger("Id Restaurant : "));
        Client client = TrouverClientParId(idClient);
        Restaurant rest = TrouverRestaurantParId(idRest);
        
        Produit p1 = rest.getProduits().get(0);
        ProduitCommande pc1 = new ProduitCommande(2, p1);
        Produit p2 = rest.getProduits().get(1);
        ProduitCommande pc2 = new ProduitCommande(1, p2);
        Produit p3 = rest.getProduits().get(2);
        ProduitCommande pc3 = new ProduitCommande(1, p3);
        ProduitCommande[] plist = {pc1, pc2, pc3};
        
        Commande c = CreerCommande(client, rest, Arrays.asList(plist));
        System.out.println(c);
        System.out.println("Prix Total : "+CalculerPrixTotal(c));
    }
    
    public static void ValiderC() throws Throwable{
        System.out.println("\n\n--- Validation d'une Commande ---");
        Long id = new Long(lireInteger("Id commande : "));
        Commande c = TrouverCommandeParId(id);
        if (ValiderCommande(c)){
            System.out.println("Commande validée");
        } else {
            System.out.println("/!\\ Commande non validée");
        }
        System.out.println(c);
    }
    
    public static void AnnulerC() throws Throwable{
        System.out.println("\n\n--- Annulation d'une Commande ---");
        Long id = new Long(lireInteger("Id commande : "));
        Commande c = TrouverCommandeParId(id);
        if (AnnulerCommande(c)){
            System.out.println("Commande annulée");
        } else {
            System.out.println("/!\\ Commande non annulée");
        }
        System.out.println(c);
    }
    
    public static void TerminerC() throws Throwable{
        System.out.println("\n\n--- Cloture d'une Commande ---");
        Long id = new Long(lireInteger("Id commande : "));
        Commande c = TrouverCommandeParId(id);
        if (TerminerCommande(c)){
            System.out.println("Commande terminée");
        } else {
            System.out.println("/!\\ Commande non terminée");
        }
        System.out.println(c);
    }
    
    public static void main(String args[]) throws Throwable{
        System.out.println(
                "=== Gustat'IF - CLI Demo ===\n"
              + " Ce programme en ligne de commande présente un exemple des fonctionnalitées de l'application Gustat'IF.\n"
              + "Veuillez vous référer au document de conception pour obtenir une liste complète des services.\n"
        );
        int choix = 0;
        while (choix!=12){
            choix = Menu();
            switch (choix){
                case 1:
                    List<Restaurant> liste = ListerRestaurants();
                    DisplayTab(liste);
                    break;
                case 2:
                    ProduitsRestaurant();
                    break;
                case 3:
                    DisplayTab(ListerClients());
                    break;
                case 4:
                    AddClient();
                    break;
                case 5:
                    DisplayTab(ListerCommandes());
                    break;
                case 6:
                    AddCommande();
                    break;
                case 7:
                    ValiderC();
                    break;
                case 8:
                    AnnulerC();
                    break;
                case 9:
                    TerminerC();
                    break;
                case 10:
                    DisplayTab(ListerLivreurs());
                    break;
                case 11:
                    Client client = ConnecterClient("asing8183@free.fr", "azerty");
                    System.out.println(TrouverClientParId(client.getId()));
                    break;
                case 12:
                    System.out.println("À bientôt...");
                    break;
                default:
                    System.out.println("Choix incorrect");
                    break;
            }
        }
    }
}
