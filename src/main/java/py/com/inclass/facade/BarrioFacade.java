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
    
    public BarrioFacade(){
        super(Barrio.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Barrio
    public List<Barrio> getAll(){
        try{
            Query query = em.createQuery("select u from Barrio u order by u.idBarrio asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Barrios.", e);
        }
    return null;
   }
}