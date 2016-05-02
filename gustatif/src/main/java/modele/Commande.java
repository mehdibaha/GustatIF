package modele;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Commande implements Serializable {
    
    public enum CommandeStatus {
        VALIDATION, ENCOURS, LIVREE, ANNULEE
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    @Enumerated(EnumType.STRING)
    private CommandeStatus status;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToOne()
    private Livreur livreur;
    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    private List<ProduitCommande> produits;
    // Pas de vérification pour savoir si les produits commandés sont bien des produits 
    // du restaurant
    
    public Commande() {
    }
    
    public Commande(Client c, Restaurant r, Livreur l){
        dateDebut = new Date();
        status = CommandeStatus.VALIDATION;
        client = c;
        restaurant = r;
        livreur = l;
        produits = new LinkedList<ProduitCommande>();
    }
    
    public Commande(Client c, Restaurant r, Livreur l, List<ProduitCommande> lp){
        this(c, r, l);
        produits = lp;
    }

    public Long getId() {
        return id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public CommandeStatus getStatus() {
        return status;
    }

    public Client getClient() {
        return client;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public List<ProduitCommande> getProduits() {
        return produits;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setStatus(CommandeStatus status) {
        this.status = status;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public void setProduits(List<ProduitCommande> produits) {
        this.produits = produits;
    }
    
    public void addProduits(ProduitCommande p) {
        produits.add(p);
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", status=" + status + ", \n  client=" + client + ", \n  restaurant=" + restaurant + ", \n  livreur=" + livreur + ", \n  produits=" + produits + '}';
    }
    
    
            
}
