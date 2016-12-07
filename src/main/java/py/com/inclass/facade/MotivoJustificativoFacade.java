package py.com.inclass.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.MotivoJustificativo;

@Stateless 
public class MotivoJustificativoFacade  extends AbstractFacade<MotivoJustificativo>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public MotivoJustificativoFacade() {
        super(MotivoJustificativo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //MotivoJustificativo
    public List<MotivoJustificativo> getAll() {
        try {
            Query query = em.createQuery("select u from MotivoJustificativo u order by u.idMotivo asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los motivos de justificación.", e);
        }
        return null;
    }

    public List<MotivoJustificativo> getAllActivos() {
        try {
            Query query = em.createQuery("select u from MotivoJustificativo u where u.estado = 1 order by u.idMotivo asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los motivos de justificación activos.", e);
        }
        return null;
    } 
}