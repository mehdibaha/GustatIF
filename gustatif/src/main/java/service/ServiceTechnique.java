package service;

import com.google.maps.model.LatLng;
import dao.LivreurDao;
import java.text.SimpleDateFormat;
import java.util.List;
import modele.*;
import static service.ServiceMetier.CalculerPrixTotal;
import static util.GeoTest.getFlightDistanceInKm;
import static util.GeoTest.getTripDurationByBicycleInMinute;

public class ServiceTechnique {
    
    public static Livreur TrouverLivreurIdeal(Restaurant restaurant, Client client, Double poidTotal) throws Throwable{
        LivreurDao lDao = new LivreurDao();
        List<Livreur> livreurs = (lDao.findDispo(Livreur.ALL_TYPE));
        
        if (livreurs.size() > 0 && restaurant != null && client != null){
            Livreur livreurMin = livreurs.get(0);
            Double dureeMin = Double.MAX_VALUE;
            Double duree = 0.0;
            
            for ( Livreur l : livreurs){
                if (l.getChargeMax() >= poidTotal) {
                    LatLng crdnLivreur = new LatLng(l.getLatitude(), l.getLongitude());
                    LatLng crdnRestaurant = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
                    LatLng crdnClient = new LatLng(client.getLatitude(), client.getLongitude());
                    if (l instanceof Drone){
                        Drone d = (Drone) l;
                        double distance = getFlightDistanceInKm(crdnLivreur, crdnRestaurant);
                        distance += getFlightDistanceInKm(crdnRestaurant, crdnClient);
                        duree = distance / d.getVitesseMoy();
                    } else {
                        duree = getTripDurationByBicycleInMinute(crdnLivreur, crdnClient, crdnRestaurant);
                    }
                    if (duree < dureeMin){
                        dureeMin = duree;
                        livreurMin = l;
                    }
                }
            }
            return livreurMin;
        }
        return null;
    }
    
    public static boolean VerificationMdp(String mdp, String mdp2)
    {
        return mdp.equals(mdp2);
    }
    
    public static String GenererMailLivreurVelo(Commande commande){
        String mail = "";
        Velo v = (Velo) commande.getLivreur();
        mail += "\nBonjour "+v.getName()+",\n";
        mail += "    Merci d'effectuer cette livraison dès maintenant, tout en respectant le code de la route ;-D\n";
        mail += "Le Chef\n\n";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMM Y 'à' H'h'm");
        mail += "Détails de la livraison\n"
                + "  - Date/heure : "+ dateFormat.format(commande.getDateDebut())+"\n"
                + "  - Livreur : "+v.getName()+" (n°"+v.getId()+")\n"
                + "  - Restaurant : "+commande.getRestaurant().getDenomination()+"\n"
                + "  - Client :\n"
                + "           "+commande.getClient().getPrenom()+" "+commande.getClient().getNom()+"\n"
                + "           "+commande.getClient().getAdresse()+"\n\n"
                + "Commande :\n";
                for (ProduitCommande p : commande.getProduits()){
                    mail+="  - "+p.getQuantitee()+" "+p.getProduit().getDenomination()+" : "+p.getQuantitee()+" x "+p.getProduit().getPrix()+"€\n";
                }
                mail+="\nTOTAL : "+CalculerPrixTotal(commande)+"€\n\n";
        return mail;
    }
}
