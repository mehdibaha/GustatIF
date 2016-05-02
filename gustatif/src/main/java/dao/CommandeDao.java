package dao;

import modele.Commande;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import modele.ProduitCommande;


public class CommandeDao {
    
    public void create(Commande commande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(commande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Commande update(Commande commande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            commande = em.merge(commande);
        }
        catch(Exception e){
            throw e;
        }
        return commande;
    }
    
    public Commande findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Commande commande = null;
        try {
            commande = em.find(Commande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return commande;
    }
    
    public List<Commande> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Commande c ORDER BY c.id");
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return commandes;
    }
    
    public List<Commande> findByClientId(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Commande c WHERE c.client.id = :id").setParameter("id", id);   
            commandes = (List<Commande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return commandes;
    }
    
    public int getCommandesEnCours()
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        int nombre = 0;
        try {
            Query q = em.createQuery("SELECT count(c) FROM Commande c WHERE c.status = :status").setParameter("status", Commande.CommandeStatus.ENCOURS);   
            nombre = (int) q.getFirstResult();
        }
        catch(Exception e) {
            throw e;
        }     
        return nombre;
    }
}