package py.com.inclass.facade;

import py.com.inclass.entities.Barrio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class BarrioFacade  extends AbstractFacade<Barrio>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public BarrioFacade() {
        super(Barrio.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Barrio
    public List<Barrio> getAll() {
        try {
            Query query = em.createQuery("select u from Barrio u order by u.idBarrio asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los Barrios.", e);
        }
        return null;
    }

    public List<Barrio> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Barrio u where u.estado = 1 order by u.idBarrio asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los barrios activos.", e);
            throw new RuntimeException("Error al obtener los barrios activos.", e);
        }
        //return null;
    }
}