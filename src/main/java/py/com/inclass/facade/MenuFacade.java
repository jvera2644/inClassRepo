/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Menu;

/**
 *
 * @author Edu
 */

@Stateless
public class MenuFacade extends AbstractFacade<Menu> {
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    
    public MenuFacade() {
        super(Menu.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Menu> getAll() {
        try{
            Query query = em.createQuery("select u from Menu u order by u.idMenu desc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al buscar todos los menus del sistema.", e);
            return null;
        }
        
    }
    
    public Menu findByNombre(String nombre) {
        try {
            Query query = em.createQuery("select u from Menu u where UPPER(u.nombre) = UPPER(:nombre)");
            query.setParameter("nombre", nombre);
            return (Menu) query.getSingleResult();
        }catch(NoResultException nre){
            logger.warn("No se encuentra el menu: "+nombre, nre);
            return null;
        } catch (Exception e) {
            logger.error("Error al buscar el menu: "+nombre, e);
            return null;
        }
    }
    
}
