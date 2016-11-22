package py.com.inclass.facade;

import py.com.inclass.entities.Carrera;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class CarreraFacade  extends AbstractFacade<Carrera>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public CarreraFacade() {
        super(Carrera.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Carrera
    public List<Carrera> getAll() {
        try {
            Query query = em.createQuery("select u from Carrera u order by u.idCarrera asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las carreras.", e);
        }
        return null;
    }

    public List<Carrera> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Carrera u where u.estado = 1 order by u.idCarrera asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las carreras activas.", e);
        }
        return null;
    }
}