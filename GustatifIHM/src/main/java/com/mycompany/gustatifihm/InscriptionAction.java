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

/**
 *
 * @author tthibault
 */
public class InscriptionAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        String nom;
        String prenom;
        String adresse;
        String mail;
        String mdp;
        String mdp2;
        boolean conditions;
        boolean geoloc;
        Client client = null;
        
        try 
        {
            nom = request.getParameter("nom");
            prenom = request.getParameter("prenom");
            adresse = request.getParameter("adresse");
            mail = request.getParameter("mail");
            mdp = request.getParameter("mdp");
            mdp2 = request.getParameter("mdp");
            conditions = Boolean.parseBoolean(request.getParameter("conditions"));
            geoloc = Boolean.parseBoolean(request.getParameter("geoloc"));
            client = this.serviceMetier.CreerClient(nom,prenom,adresse,mail,mdp,mdp2,conditions,geoloc);
        } catch (Throwable ex) {
            Logger.getLogger(InscriptionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        request.setAttribute("client", client);
    }
}
