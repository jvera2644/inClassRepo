package py.com.inclass.facade;

import py.com.inclass.entities.Clases;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Horario;
import py.com.inclass.entities.Persona;

@Stateless 
public class ClasesFacade  extends AbstractFacade<Clases>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public ClasesFacade() {
        super(Clases.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Clases
    public List<Clases> getAll() {
        try {
            Query query = em.createQuery("select u from Clases u order by u.idClases asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las clases.", e);
        }
        return null;
    }
    
    public boolean existeClaseIniciadaPorHorario(Horario h){
        try{
            Query query = em.createQuery("select u from Clases u where u.idHorario = :h and u.fechaHoraFin = null order by u.idClases asc");
            query.setParameter("h", h);
            Clases c = (Clases)query.getSingleResult();
            if(c != null){
                if (c.getFechaHoraInicio() != null && c.getFechaHoraFin() == null){
                    return true;
                }
            }
        }catch(Exception e){
            logger.error("Error al obtener la clase para el horario: "+h.getIdHorario(), e);
        }
        return false;
    }
    
    public boolean existeClaseFinalizadaPorHorario(Horario h){
        try{
            Query query = em.createQuery("select u from Clases u where u.idHorario = :h order by u.idClases asc");
            query.setParameter("h", h);
            Clases c = (Clases)query.getSingleResult();
            if(c != null){
                if (c.getFechaHoraInicio() != null && c.getFechaHoraFin() != null){
                    return true;
                }
            }
        }catch(Exception e){
            logger.error("Error al obtener la clase para el horario: "+h.getIdHorario(), e);
        }
        return false;
    }
    
    public List<Clases> getAllPorPersona(Persona p) {
        try {
            Query query = em.createQuery("select u from Clases u where u.idPersona = :p order by u.idClases asc");
            query.setParameter("p", p);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las clases que corresponden al profesor "+p.getApellido()+" "+p.getNombre(), e);
        }
        return null;
    }
    
}