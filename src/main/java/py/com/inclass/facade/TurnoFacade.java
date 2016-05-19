package py.com.inclass.facade;

import py.com.inclass.entities.Turno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class TurnoFacade  extends AbstractFacade<Turno>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public TurnoFacade(){
        super(Turno.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Turno
    public List<Turno> getAll(){
        try{
            Query query = em.createQuery("select u from Turno u order by u.idTurno asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Turnos.", e);
        }
    return null;
   }
}