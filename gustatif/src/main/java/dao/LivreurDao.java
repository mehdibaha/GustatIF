package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import modele.Livreur;

public class LivreurDao {
    
    public void create(Livreur livreur) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(livreur);
        } catch(Exception e) {
            throw e;
        }
    }
    
    public Livreur update(Livreur livreur) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            livreur = em.merge(livreur);
        }
        catch(Exception e){
            throw e;
        }
        return livreur;
    }
    
    public Livreur findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Livreur livreur = null;
        try {
            livreur = em.find(Livreur.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return livreur;
    }
    
    public List<Livreur> findAll(int type) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livreur> livreurs = null;
        try {
            Query q;
            if (type == Livreur.DRONE_TYPE) {
                q = em.createQuery("SELECT l FROM Drone l ORDER BY l.id");
            } else if ( type == Livreur.VELO_TYPE) {
                q = em.createQuery("SELECT l FROM Velo l ORDER BY l.id");
            } else { // == All
                q = em.createQuery("SELECT l FROM Livreur l ORDER BY l.id");
            }
            livreurs = (List<Livreur>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return livreurs;
    }
    
    public List<Livreur> findDispo(int type) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livreur> livreurs = null;
        try {
            Query q;
            if (type == Livreur.DRONE_TYPE) {
                q = em.createQuery("SELECT l FROM Drone l WHERE l.dispo=:type ORDER BY l.id");
                q.setParameter("type", true);
            } else if ( type == Livreur.VELO_TYPE) {
                q = em.createQuery("SELECT l FROM Velo l WHERE l.dispo=:type ORDER BY l.id");
                q.setParameter("type", true);
            } else { // == All
                q = em.createQuery("SELECT l FROM Livreur l WHERE l.dispo=:type ORDER BY l.id");
                q.setParameter("type", true);
            }
            livreurs = (List<Livreur>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return livreurs;
    }
    
    public void lockLivreur(Livreur l) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.lock(l, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }
}
