package py.com.inclass.facade;

import py.com.inclass.entities.Horario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Persona;

@Stateless 
public class HorarioFacade  extends AbstractFacade<Horario>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public HorarioFacade() {
        super(Horario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Horario
    public List<Horario> getAll() {
        try {
            Query query = em.createQuery("select u from Horario u order by u.idHorario asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los horarios de clases.", e);
        }
        return null;
    }
    
    public List<Horario> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Horario u where u.estado = 1 order by u.idHorario asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los horarios de clases activos.", e);
        }
        return null;
    }
    
    public List<Horario> getHorariosActivosPorPersona(Persona persona) {
        try {
            Query query = em.createQuery("select u from Horario u where u.estado = 1 and u.idPersona = :persona order by u.idHorario asc");
            query.setParameter("persona", persona);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los horarios de clases activos de la persona: "+persona.getApellido() + " " + persona.getNombre(), e);
        }
        return null;
    }
   
}