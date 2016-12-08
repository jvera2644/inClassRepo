package py.com.inclass.facade;

import py.com.inclass.entities.Inscripcion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Horario;

@Stateless 
public class InscripcionFacade  extends AbstractFacade<Inscripcion>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public InscripcionFacade() {
        super(Inscripcion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Inscripcion
    public List<Inscripcion> getAll() {
        try {
            Query query = em.createQuery("select u from Inscripcion u order by u.idInscripcion asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las inscripciones.", e);
        }
        return null;
    }
    
    public List<Inscripcion> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Inscripcion u where u.estado = 1 order by u.idInscripcion asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las inscripciones.", e);
        }
        return null;
    }
    
    public List<Inscripcion> getInscriptosEnHorario(Horario horario) {
        try {
            Query query = em.createQuery("select u from Inscripcion u where u.idHorario = :horario and u.estado = 1 order by u.idInscripcion asc");
            query.setParameter("horario", horario);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las inscripciones en el horario: "+horario.getIdHorario(), e);
        }
        return null;
    }
}