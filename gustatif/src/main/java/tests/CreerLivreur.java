/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.JpaUtil;
import dao.LivreurDao;
import modele.Drone;
import modele.Velo;
import static service.ServiceMetier.*;

/**
 *
 * @author hdav
 */
public class CreerLivreur {
    
    public static void main(String args[]){
        
        int nbVelo = 40;
        String[] adressesVelo = {"Place Bellecour, Lyon", "La Part-Dieu, Lyon", "Vieux Lyon, Lyon"};
        Double chargeVelo = 5000.0;
        
        int nbDrone = 10;
        String adresseDrone = "La Part-Dieu, Lyon";
        Double chargeDrone = 1200.0;
        
        JpaUtil.creerEntityManager();
        
        //Création Vélos
        for (int i = 0; i<nbVelo; i++){
            AjouterVelo(chargeVelo, "Velo_"+Integer.toString(i), adressesVelo[i%adressesVelo.length]);
        }
        
        //Création Drone
        for (int i = 0; i<nbDrone; i++){
            AjouterDrone(chargeDrone, 40.0, adresseDrone);
        }
        
        JpaUtil.fermerEntityManager();
    }
}
