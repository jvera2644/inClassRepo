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
import py.com.inclass.entities.Persona;

/**
 *
 * @author Edu
 */

@Stateless
public class PersonaFacade extends AbstractFacade<Persona>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public PersonaFacade() {
        super(Persona.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Persona> getAll() {
        try {
            Query query = em.createQuery("select u from Persona u order by u.idPersona asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las personas del sistema.", e);
        }
        return null;
    }
    
    public List<Persona> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Persona u where u.estado = 1 order by u.idPersona asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las personas del sistema.", e);
            throw new RuntimeException("Error al obtener todas las personas del sistema.", e);
        }
    }
    
    public Persona findByNroDocumento(String nroDocumento) {
        try {
            Query query = em.createQuery("select u from Persona u where u.numeroDocumento like :numeroDocumento");
            query.setParameter("numeroDocumento", "%" + nroDocumento + "%");
            Persona p = (Persona) query.getSingleResult();
            return p;
        } catch (NoResultException ne) {
            logger.warn("No existe persona con numero de documento " + nroDocumento, ne);
            return null;
        } catch (Exception e) {
            logger.error("Error al obtener persona con numero de documento " + nroDocumento, e);
            return null;
        }
    }
    
    
    public List<Persona> findByNroDocumentoOnComplete(String nroDocumento) {
        try {
            Query query = em.createQuery("select u from Persona u where u.numeroDocumento like :numeroDocumento");
            query.setParameter("numeroDocumento", "%" + nroDocumento + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener persona con numero de documento " + nroDocumento, e);
            return null;
        }
    }
}
