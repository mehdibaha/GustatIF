/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.JpaUtil;
import dao.*;
import java.util.LinkedList;
import java.util.List;
import modele.*;


/**
 *
 * @author hdav
 */
public class Test {
    public static void main(String args[]) throws Throwable
    {
        JpaUtil.creerEntityManager();
        
        // -- Livreur
        LivreurDao ldao = new LivreurDao();
        Drone d1 = new Drone(5.0, 40.0);
        Velo v1 = new Velo(15.0, "Toto");
        JpaUtil.ouvrirTransaction();
        ldao.create(d1);
        ldao.create(v1);
        JpaUtil.validerTransaction();
        System.out.println(ldao.findAll(Livreur.DRONE_TYPE));
        
        // -- Produit Commande
        ProduitDao pdao = new ProduitDao();
        ProduitCommandeDao pcdao = new ProduitCommandeDao();
        Produit p1 = pdao.findById(new Long(56));
        ProduitCommande pc1 = new ProduitCommande(3, p1);
        
        JpaUtil.ouvrirTransaction();
        pcdao.create(pc1);
        JpaUtil.validerTransaction();
        
        System.out.println("\n\n" + pcdao.findAll());
        
        // -- Commande
        ClientDao cdao = new ClientDao();
        RestaurantDao rdao = new RestaurantDao();
        Client c1 = cdao.findById(new Long(140));
        Restaurant r1 = rdao.findById(new Long(1));

        p1 = r1.getProduits().get(0);
        pc1 = new ProduitCommande(2, p1);
        p1 = r1.getProduits().get(1);
        ProduitCommande pc2 = new ProduitCommande(5, p1);
        p1 = r1.getProduits().get(2);
        ProduitCommande pc3 = new ProduitCommande(1, p1);
        
        CommandeDao comdao = new CommandeDao();
        JpaUtil.ouvrirTransaction();      
//        pcdao.create(pc1);        
//        pcdao.create(pc2);
//        pcdao.create(pc3);
        
        Commande com1 = new Commande(c1, r1, d1);
        com1.addProduits(pc1);
        com1.addProduits(pc2);
        com1.addProduits(pc3);
        com1.setStatus(Commande.CommandeStatus.LIVREE);
        comdao.create(com1);
        JpaUtil.validerTransaction();
        
        
        JpaUtil.fermerEntityManager();
    }
}
