/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modele.Restaurant;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Produit;

/**
 *
 * @author tthibault
 */
public class GetPlatsAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        List<Produit> produits = null;
        Restaurant resto = null;
        long idResto;
        try {
            idResto = Long.parseLong(request.getParameter("idRestaurant"));
            resto = this.serviceMetier.TrouverRestaurantParId(idResto);
            produits = this.serviceMetier.ListerProduitsRestaurant(resto);
        } catch (Throwable ex) {
            Logger.getLogger(FicheRestaurantAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("plats", produits);
    }
}
