package service;

import com.google.maps.model.LatLng;
import dao.*;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import modele.*;
import static service.ServiceTechnique.*;
import static util.GeoTest.getLatLng;

public class ServiceMetier {
    
    // ----------- Services Commande
    /**
     * Calcul poid total + prix total
     * Création commande -> status VALIDATION
     * Persistence de produits + commande
     * 
     * @param client client de la commande
     * @param restaurant restaurant de la commande
     * @param produits Liste des association produit/quantitée
     * @return La commande créée ou null
     */
    public static Commande CreerCommande(Client client, Restaurant restaurant, List<ProduitCommande> produits) throws Throwable{
        ProduitDao pDao = new ProduitDao();
        LivreurDao lDao = new LivreurDao();
        CommandeDao cDao = new CommandeDao();
        
        Double poidTotal = 0.0;
        Double prixTotal = 0.0;
        Produit p;
        for (ProduitCommande pc : produits){
            if ((p = pDao.findById(pc.getProduit().getId()))!=null){
                poidTotal += p.getPoids()*pc.getQuantitee();
                prixTotal += p.getPrix()*pc.getQuantitee();
            }
        }
        Livreur livreur = null;
        boolean livreurOk = false;
        while (!livreurOk){
            try {
                livreur = TrouverLivreurIdeal(restaurant, client, poidTotal);
                if (livreur != null){
                    JpaUtil.ouvrirTransaction();
                    lDao.lockLivreur(livreur);
                    livreur.setDispo(false);
                    lDao.update(livreur);
                    JpaUtil.validerTransaction();
                    livreurOk=true;
                }
            } catch (OptimisticLockException|RollbackException  e) {
                System.out.println("Problème de concurence, on réessaye...");
            }
        }
        if (livreur != null) {
            JpaUtil.ouvrirTransaction();
            Commande c = new Commande(client, restaurant, livreur, produits);
            cDao.create(c);
            JpaUtil.validerTransaction();
            return c;
        }
        
        System.out.println("Impossible d'établir la commande.");
        return null;
    }
    
    /**
     * Valide la commande : passe la commande de l'état VALIDATION à ENCOURS
     * @param commande commande à valider
     * @return true si réussie, false sinon
     */
    public static boolean ValiderCommande(Commande commande) throws Throwable{
        CommandeDao cDao = new CommandeDao();
        
        if (commande.getStatus() == Commande.CommandeStatus.VALIDATION){
            commande.setStatus(Commande.CommandeStatus.ENCOURS);
            JpaUtil.ouvrirTransaction();
            cDao.update(commande);
            JpaUtil.validerTransaction();
            if (commande.getLivreur() instanceof Velo) {
                System.out.println(GenererMailLivreurVelo(commande));
            }
            return true;
        }
        System.out.println("La commande n'a pas été validée.");
        return false;
    }
    
    /**
     * Annule la commande et libère le livreur -> livreur dispo
     * @param commande
     * @return true si réussie, false sinon
     */
    public static boolean AnnulerCommande(Commande commande) throws Throwable{
        CommandeDao cDao = new CommandeDao();
        LivreurDao lDao = new LivreurDao();
        
        if (commande.getStatus() == Commande.CommandeStatus.VALIDATION){
            commande.setStatus(Commande.CommandeStatus.ANNULEE);
            Livreur livreur = commande.getLivreur();
            JpaUtil.ouvrirTransaction();
            if (livreur != null) {
                livreur.setDispo(true);
                lDao.update(livreur);
            }
            cDao.update(commande);
            JpaUtil.validerTransaction();
            return true;
        }
        System.out.println("La commande n'a pas été annulée.");
        return false;
    }
    
    /**
     * Termine une commande = la commande a bien été livrée.
     * @param commande
     * @return true si réussie, false sinon
     */
    public static boolean TerminerCommande(Commande commande) throws Throwable {
        CommandeDao cDao = new CommandeDao();
        LivreurDao lDao = new LivreurDao();
        
        if (commande.getStatus() == Commande.CommandeStatus.ENCOURS){
            commande.setStatus(Commande.CommandeStatus.LIVREE);
            commande.setDateFin(new Date());
            Livreur livreur = commande.getLivreur();
            JpaUtil.ouvrirTransaction();
            if (livreur != null) {
                livreur.setDispo(true);
                lDao.update(livreur);
            }
            cDao.update(commande);
            JpaUtil.validerTransaction();
            return true;
        }
        System.out.println("La commande n'a pas été terminée.");
        return false;
    }
    
    // ------------ Services Livreurs
    public static Velo AjouterVelo(Double chargeMax, String nom, String adresse){
        LatLng coordonees;
        if ((coordonees = getLatLng(adresse)) != null){
             LivreurDao ldao = new LivreurDao();
             Velo v = new Velo(chargeMax, nom);
             v.setLatitude(coordonees.lat);
             v.setLongitude(coordonees.lng);
             JpaUtil.ouvrirTransaction();
             ldao.create(v);
             JpaUtil.validerTransaction();
             return v;
        }
        return null;
    }
    
    public static Drone AjouterDrone(Double chargeMax, Double vitesseMoy, String adresse){
        LatLng coordonees;
        if ((coordonees = getLatLng(adresse)) != null){
             LivreurDao ldao = new LivreurDao();
             Drone d = new Drone(chargeMax, vitesseMoy);
             d.setLatitude(coordonees.lat);
             d.setLongitude(coordonees.lng);
             JpaUtil.ouvrirTransaction();
             ldao.create(d);
             JpaUtil.validerTransaction();
             return d;
        }
        return null;
    }
    
     /**
     *
     * @param nom Nom spécifié dans le champ correspondant
     * @param prenom prenom spécifié dans le champ correspondant
     * @param adresse adresse spécifié dans le champ correspondant
     * @param mail mail spécifié dans le champ correspondant
     * @param mdp mot de passe spécifié dans le champ correspondant
     * @param mdp2 confirmation de mot de passe 
     * @param conditions true si checkbox "termes et conditions" cochée
     * @param geoloc true si checkbox "accepter géolocalisation" cochée
     * @return client le Client créé
     * @throws Throwable
     */
    public static Client CreerClient(String nom, String prenom, String adresse, String mail, String mdp, String mdp2, boolean conditions, boolean geoloc) throws Throwable
    {
        if(geoloc && conditions && VerificationMdp(mdp,mdp2))
        {
            
            ClientDao cdao = new ClientDao();
            List<Client> clients = cdao.findByMail(mail);
            if(clients.isEmpty())
            {
                LatLng coords = getLatLng(adresse);
                if (coords !=null) {
                    Client client = new Client(nom,prenom,adresse,mail);
                    client.setMotPasse(mdp);
                    client.setCoordonnees(coords);
                    JpaUtil.ouvrirTransaction();
                    cdao.create(client);
                    JpaUtil.validerTransaction();
                    System.out.println("Bonjour " + client.getPrenom() +",");
                    System.out.println("Nous vous confirmons votre inscription au service Gustat'IF. Votre numéro de client est : "+ client.getId() +".");
                    return client;
                } else {
                    System.out.println("Adresse postale invalide");
                    System.out.println("Bonjour " + nom +",");
                    System.out.println("Votre inscription au service Gustat'IF a malencontreusement échoué..Merci de recommencer ultérieurement.");
                }
            }
            else
            {
                System.out.println("Adresse mail déjà utililsée");
                System.out.println("Bonjour " + nom +",");
                System.out.println("Votre inscription au service Gustat'IF a malencontreusement échoué..Merci de recommencer ultérieurement.");
            }
        }
        else
        {
            System.out.println("Les mots de passe ne correspondent pas");
        }
        return null;
    }

    /**
     * 
     * @param denomination Denomination du restaurant
     * @param description Description du restaurant
     * @param adresse adresse du restaurant
     * @return restaurant le Restaurant créé
     * @throws Throwable 
     */
    public static Restaurant CreerRestaurant(String denomination, String description, String adresse) throws Throwable
    {
        RestaurantDao rdao = new RestaurantDao();
        Restaurant restaurant = new Restaurant(denomination, description, adresse);
        LatLng coords = getLatLng(adresse);
        restaurant.setCoordonnees(coords);
        JpaUtil.ouvrirTransaction();
        rdao.create(restaurant);
        JpaUtil.validerTransaction();
        System.out.println("Restaurant " + restaurant.getId() + " créé avec succcès");
        return restaurant;
    }
    /**
     * 
     * @param denomination Denomination du produit
     * @param description Description du produit
     * @param prix prix du prodduit
     * @param poids poids du produit
     * @return produit le Produit crée
     * @throws Throwable 
     */
    public static Produit CreerProduit(String denomination, String description, Float prix, Float poids) throws Throwable
    {
        ProduitDao pdao = new ProduitDao();
        Produit produit = new Produit(denomination, description, prix, poids);
        JpaUtil.ouvrirTransaction();
        pdao.create(produit);
        JpaUtil.validerTransaction();
        System.out.println("Produit " + produit.getId() + " créé avec succès");
        return produit;
    }
    
    
    /**
     *
     * @param client client dont on veut modifier les information personnelles
     * @param nom nouveau nom du client
     * @param prenom nouveau prénom du client
     * @param adresse nouvelle adresse du client
     * @param mail nouveau mail du client
     * @param mdp nouveau mot de passe client
     * @param mdp2 confirmation nouveau mot de passe client
     * @param conditions true si checkbox "termes et conditions" cochée
     * @param geoloc true si checkbox "accepter géolocalisation" cochée
     * @throws Throwable
     */
    public static void ModifierInfoPersos(Client client, String nom, String prenom, String adresse, String mail, String mdp, String mdp2, boolean conditions, boolean geoloc) throws Throwable
    {
        if(geoloc && conditions && VerificationMdp(mdp,mdp2))
        {
            
            ClientDao cdao = new ClientDao();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setAdresse(adresse);
            client.setMotPasse(mdp);
            LatLng coords = getLatLng(adresse);
            client.setCoordonnees(coords);
            JpaUtil.ouvrirTransaction();
            cdao.update(client);
            JpaUtil.validerTransaction();
        }
        else
        {
            System.out.println("Les mots de passe ne correspondent pas");
        }
    }
    
    /**
     *
     * @param mail mail où envoyer le nouveau mot de passe
     * @throws Throwable
     */
    public static void RenvoyerMotDePasse(String mail) throws Throwable
    {
        ClientDao cdao = new ClientDao();
        List<Client> clients = cdao.findByMail(mail);
        if(clients.isEmpty())
        {
            System.out.println("Adresse mail incorrecte");
        }
        else
        {
            System.out.println("Bonjour,");
            System.out.println("Vous avez (encore?) oublié voter mot de passe? Permettez-nous de vous le rappeler!");
            System.out.println("Mot de passe : "+clients.get(0).getMotPasse());
            System.out.println("L'équipe Gustat'if vous remercie de votre confiance.");
        }
    }
    
    /**
     *
     * @param mail mail fourni par l'utilisateur 
     * @param mdp mot de passe fourni par l'utilisateur
     * @return client si le mail et le mot de passe correspondent
     * @throws Throwable
     */
    public static Client ConnecterClient(String mail, String mdp) throws Throwable
    {
        ClientDao cdao = new ClientDao();
        List<Client> clients = cdao.findByMail(mail);
        if(clients.isEmpty())
        {
            System.out.println("Adresse mail incorrecte");
        }
        else
        {
            String rightMdp = (clients.get(0)).getMotPasse();
            if(VerificationMdp(mdp,rightMdp))
            {
                return clients.get(0);
            }
            else
            {
                System.out.println("Mot de passe incorrect");
            }
        }
        return null;
    }
    
     /**
     *@return La liste des restaurants 
     * @throws java.lang.Throwable
     */
    public static List<Restaurant> ListerRestaurants() throws Throwable
    {
        JpaUtil.creerEntityManager();
        RestaurantDao rdao = new RestaurantDao();
        List<Restaurant> restaurants = rdao.findAll();
        JpaUtil.fermerEntityManager();
        return restaurants;
    }
    
     /**
     * @param client le client dont on veut avori l'historique des commandes
     *@return la liste des commandes de l'utilisateur
     * @throws java.lang.Throwable
     * *
     */
    public static List<Commande> ListerCommandesClient(Client client) throws Throwable
    {
        CommandeDao cdao = new CommandeDao();
        List<Commande> commandes = cdao.findByClientId(client.getId());
        return commandes;
    }
    
     /**
     * @param restaurant le restaurant dont on veut connaitre les plats
     *@return la liste des plats (produits) disponibles
     * 
     */
    public static List<Produit> ListerProduitsRestaurant(Restaurant restaurant)
    {
        List<Produit> produit =restaurant.getProduits();
        return produit;
    }
    
     /**
     * @param commande la commande dont on veut calculer le prix total
     *@return le prix total de la commande
     * 
     */
    public static double CalculerPrixTotal(Commande commande)
    {
        List<ProduitCommande> pc = commande.getProduits();
        double prixTotal = 0.0;
        for( int i=0; i<pc.size(); i++)
        {
            prixTotal = prixTotal + pc.get(i).getQuantitee()*pc.get(i).getProduit().getPrix();
        }
        return prixTotal;
    }
    
    /**
     *
     * @param id l'id du client cherché
     * @return client le client trouvé
     * @throws Throwable
     */
    public static Client TrouverClientParId(Long id) throws Throwable
    {
         ClientDao cdao = new ClientDao();
         Client client = cdao.findById(id);
         if(client == null)
         {
             System.out.println("Client introuvable");
         }
         return client;
    }
    
    /**
     *
     * @param id l'id de la commande cherchée
     * @return commande la commande trouvée
     * @throws Throwable
     */
     public static Commande TrouverCommandeParId(Long id) throws Throwable
    {
         CommandeDao cdao = new CommandeDao();
         Commande commande = cdao.findById(id);
         if(commande == null)
         {
             System.out.println("Commande introuvable");
         }
         return commande;
    }
     
    /**
     *
     * @param id l'id du restaurant cherché
     * @return restaurant le restaurant trouvé
     * @throws Throwable
     */
    public static Restaurant TrouverRestaurantParId(Long id) throws Throwable
    {
         RestaurantDao rdao = new RestaurantDao();
         Restaurant restaurant = rdao.findById(id);
         if(restaurant == null)
         {
             System.out.println("Restaurant introuvable");
         }
         return restaurant;
    }
    
    /**
     * 
     * @return produits la liste de tous les produits
     * @throws Throwable 
     */
    public static List<Produit> ListerProduits() throws Throwable
    {
        ProduitDao pdao = new ProduitDao();
        List<Produit> produits = pdao.findAll(); 
        return produits;
    }
     /**
     * 
     * @return commandes la liste de toutes les commandes
     * @throws Throwable 
     */
    public static List<Commande> ListerCommandes() throws Throwable
    {
        CommandeDao cdao = new CommandeDao();
        List<Commande> commandes = cdao.findAll(); 
        return commandes;
    }
    
     /**
     * 
     * @return livreurs la liste de tous les livreurs
     * @throws Throwable 
     */
    public static List<Livreur> ListerLivreurs() throws Throwable
    {
        LivreurDao ldao = new LivreurDao();
        List<Livreur> livreurs = ldao.findAll(Livreur.ALL_TYPE); 
        return livreurs;
    }
    
     /**
     * 
     * @return clients la liste de tous les clients
     * @throws Throwable 
     */
    public static List<Client> ListerClients() throws Throwable
    {
        ClientDao cdao = new ClientDao();
        List<Client> clients = cdao.findAll();
        return clients;
    }
    /**
     * 
     * @return nombre le nombre de commandes en cours
     */
    public static int NbCommandeEnCours()
    {
        CommandeDao cdao = new CommandeDao();
        int nombre = cdao.getCommandesEnCours();
        return nombre;
    }
    
}
