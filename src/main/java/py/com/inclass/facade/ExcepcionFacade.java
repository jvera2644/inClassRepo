package py.com.inclass.facade;

import py.com.inclass.entities.Excepcion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class ExcepcionFacade  extends AbstractFacade<Excepcion>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public ExcepcionFacade(){
        super(Excepcion.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Excepcion
    public List<Excepcion> getAll(){
        try{
            Query query = em.createQuery("select u from Excepcion u order by u.idExcepcion asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Excepcion.", e);
        }
    return null;
   }
}