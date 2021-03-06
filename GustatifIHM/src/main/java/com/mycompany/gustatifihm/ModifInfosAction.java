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
import service.ServiceMetier;

/**
 *
 * @author tthibault
 */
public class ModifInfosAction extends Action {
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
        long idClient = (Long) request.getSession(true).getAttribute("id");
        
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
            
            Client client = serviceMetier.TrouverClientParId(idClient);
            boolean state = ServiceMetier.ModifierInfoPersos(client,nom,prenom,adresse,mail,mdp,mdp2,conditions,geoloc);
            request.setAttribute("state", state);
            
        } catch (Throwable ex) {
            Logger.getLogger(ModifInfosAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
