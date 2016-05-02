package demo;

import dao.CommandeDao;
import dao.JpaUtil;
import dao.LivreurDao;
import dao.ProduitDao;
import java.util.List;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import modele.*;
import static service.ServiceTechnique.TrouverLivreurIdeal;
import static util.Saisie.lireChaine;

public class TestServiceConcurent {
    public static Commande TestCreerCommande(Client client, Restaurant restaurant, List<ProduitCommande> produits) throws Throwable{
        ProduitDao pDao = new ProduitDao();
        LivreurDao lDao = new LivreurDao();
        CommandeDao cDao = new CommandeDao();
        
        Double poidTotal = 0.0;
        Double prixTotal = 0.0;
        Produit p;
        for (ProduitCommande pc : produits){
            if ((p = pDao.findById(pc.getProduit().getId()))!=null){
                poidTotal += p.getPoids()*pc.getQuantitee();
                prixTotal += p.getPrix()*pc.getQuantitee();
            }
        }
        Livreur livreur = null;
        boolean livreurOk = false;
        while (!livreurOk){
            try {
                livreur = TrouverLivreurIdeal(restaurant, client, poidTotal);
                lireChaine("Attente test concurence");
                if (livreur != null){
                    JpaUtil.ouvrirTransaction();
                    lDao.lockLivreur(livreur);
                    livreur.setDispo(false);
                    lDao.update(livreur);
                    JpaUtil.validerTransaction();
                    livreurOk=true;
                }
            } catch (OptimisticLockException|RollbackException  e) {
                System.out.println("Problème de concurence, on réessaye...");
            }
        }
        if (livreur != null) {
            JpaUtil.ouvrirTransaction();
            Commande c = new Commande(client, restaurant, livreur, produits);
            cDao.create(c);
            JpaUtil.validerTransaction();
            return c;
        }
        
        System.out.println("Impossible d'établir la commande.");
        return null;
    }
}
