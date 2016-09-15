package py.com.inclass.facade;

import py.com.inclass.entities.Ciudad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class CiudadFacade  extends AbstractFacade<Ciudad>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public CiudadFacade() {
        super(Ciudad.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Ciudad
    public List<Ciudad> getAll() {
        try {
            Query query = em.createQuery("select u from Ciudad u order by u.idCiudad asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las Ciudad.", e);
        }
        return null;
    }
    
    public List<Ciudad> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Ciudad u where u.estado = 1 order by u.idCiudad asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las ciudades activas.", e);
            throw new RuntimeException("Error al obtener las ciudades activas.", e);
        }
        //return null;
    }
}