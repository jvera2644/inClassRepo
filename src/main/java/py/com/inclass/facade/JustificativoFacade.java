package py.com.inclass.facade;

import py.com.inclass.entities.Justificativo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class JustificativoFacade  extends AbstractFacade<Justificativo>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public JustificativoFacade() {
        super(Justificativo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Justificativo
    public List<Justificativo> getAll() {
        try {
            Query query = em.createQuery("select u from Justificativo u order by u.idJustificativo asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los justificativos del sistema.", e);
        }
        return null;
    }
    
    public List<Justificativo> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Justificativo u where u.estado = 1 order by u.idJustificativo asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los justificativos activos del sistema.", e);
        }
        return null;
    }
}