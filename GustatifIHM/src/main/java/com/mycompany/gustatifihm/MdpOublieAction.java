/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tthibault
 */
public class MdpOublieAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        String mail;
        
        try 
        {
            mail = request.getParameter("mail");
            this.serviceMetier.RenvoyerMotDePasse(mail);
        } catch (Throwable ex) {
            Logger.getLogger(MdpOublieAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
