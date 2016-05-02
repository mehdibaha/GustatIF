package demo;

import dao.JpaUtil;
import modele.Commande;
import static service.ServiceMetier.TerminerCommande;
import static service.ServiceMetier.TrouverCommandeParId;
import static util.Saisie.lireInteger;

public class ClotureLivraison {
    public static void main(String[] args) throws Throwable{
        JpaUtil.creerEntityManager();
        
        System.out.println("\n\n--- Cloture d'une Commande ---");
        Long id = new Long(lireInteger("Id commande : "));
        Commande c = TrouverCommandeParId(id);
        if (TerminerCommande(c)){
            System.out.println("Commande terminée");
        } else {
            System.out.println("/!\\ Commande non terminée");
        }
        System.out.println(c);
        
        JpaUtil.fermerEntityManager();
    }
}
