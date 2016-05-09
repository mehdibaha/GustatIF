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
public class ConnexionAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        String mail;
        String pass;
        Client client = null;
        
        try 
        {
            mail = request.getParameter("mail");
            pass = request.getParameter("pass");
            client = this.serviceMetier.ConnecterClient(mail, pass);
        } catch (Throwable ex) {
            Logger.getLogger(ConnexionAction.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        request.setAttribute("client", client);
    }
}
