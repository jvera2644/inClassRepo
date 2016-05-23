/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.TipoDocumento;

/**
 *
 * @author Edu
 */

@Stateless
public class TipoDocumentoFacade extends AbstractFacade<TipoDocumento>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public TipoDocumentoFacade() {
        super(TipoDocumento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<TipoDocumento> getAll() {
        try {
            Query query = em.createQuery("select u from TipoDocumento u order by u.idTipoDocumento asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los tipos de documentos.", e);
        }
        return null;
    }
    
    public List<TipoDocumento> getAllActivos() {
        try {
            Query query = em.createQuery("select u from TipoDocumento u where u.estado = 1 order by u.idTipoDocumento asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los tipos de documentos activos.", e);
        }
        return null;
    }
}
