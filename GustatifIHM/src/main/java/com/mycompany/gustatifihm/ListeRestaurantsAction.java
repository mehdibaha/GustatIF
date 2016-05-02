/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gustatifihm;
import javax.servlet.http.HttpServletRequest;
import modele.Restaurant;
import service.ServiceMetier;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author tthibault
 */
public class ListeRestaurantsAction extends Action {
    @Override
    public void execute(HttpServletRequest request)
    {
        List<Restaurant> restaurants = null;
        try {
            restaurants = this.serviceMetier.ListerRestaurants();
        } catch (Throwable ex) {
            Logger.getLogger(ListeRestaurantsAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("restaurants", restaurants);
    }
}
