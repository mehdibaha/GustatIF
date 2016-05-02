/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.ClientDao;
import dao.JpaUtil;
import dao.RestaurantDao;
import java.util.Arrays;
import modele.*;
import static service.ServiceMetier.*;

/**
 *
 * @author hdav
 */
public class TestServiceCommande {
    
    public static void main(String[] args) throws Throwable {
        JpaUtil.creerEntityManager();
        RestaurantDao rdao = new RestaurantDao();
        Restaurant r1 = rdao.findById(new Long(1));
        ClientDao cdao = new ClientDao();
        Client c1 = cdao.findById(new Long(140));
        
        Produit p1 = r1.getProduits().get(0);
        ProduitCommande pc1 = new ProduitCommande(2, p1);
        Produit p2 = r1.getProduits().get(1);
        ProduitCommande pc2 = new ProduitCommande(1, p2);
        Produit p3 = r1.getProduits().get(2);
        ProduitCommande pc3 = new ProduitCommande(1, p3);
        
        ProduitCommande[] plist = {pc1, pc2, pc3};
        
        Commande com1 = CreerCommande(c1, r1, Arrays.asList(plist));
        System.out.println(com1);
        ValiderCommande(com1);
        System.out.println(com1);
        TerminerCommande(com1);
        System.out.println(com1);
        
        JpaUtil.fermerEntityManager();
    }
}
