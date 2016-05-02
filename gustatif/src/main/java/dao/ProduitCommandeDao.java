package dao;

import modele.ProduitCommande;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


public class ProduitCommandeDao {
    
    public void create(ProduitCommande pcommande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(pcommande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public ProduitCommande update(ProduitCommande pcommande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            pcommande = em.merge(pcommande);
        }
        catch(Exception e){
            throw e;
        }
        return pcommande;
    }
    
    public ProduitCommande findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        ProduitCommande pcommande = null;
        try {
            pcommande = em.find(ProduitCommande.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return pcommande;
    }
    
    public List<ProduitCommande> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<ProduitCommande> pcommandes = null;
        try {
            Query q = em.createQuery("SELECT pc FROM ProduitCommande pc ORDER BY pc.id");
            pcommandes = (List<ProduitCommande>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return pcommandes;
    }
}
