/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Client;
import modele.Commande;

/**
 *
 * @author tthibault
 */
public class ValiderCommandeAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        long idCommande = Long.parseLong(request.getParameter("idCommande"));
        
        try 
        {
            Commande commande = serviceMetier.TrouverCommandeParId(idCommande);
            this.serviceMetier.ValiderCommande(commande);
            
        } catch (Throwable ex) {
            Logger.getLogger(ModifInfosAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
