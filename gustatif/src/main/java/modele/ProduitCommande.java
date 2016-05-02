package modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class ProduitCommande implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int quantitee;
    @ManyToOne
    private Produit produit;
    
    public ProduitCommande(){
    }
    
    public ProduitCommande(int quantitee, Produit produit){
        this.quantitee=quantitee;
        this.produit=produit;
    }

    public Long getId() {
        return id;
    }

    public int getQuantitee() {
        return quantitee;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setQuantitee(int quantitee) {
        this.quantitee = quantitee;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "ProduitCommande{" + "id=" + id + ", quantitee=" + quantitee + ", produit=" + produit + '}';
    }
}
