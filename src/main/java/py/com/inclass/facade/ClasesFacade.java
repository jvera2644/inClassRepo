package py.com.inclass.facade;

import py.com.inclass.entities.Clases;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class ClasesFacade  extends AbstractFacade<Clases>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public ClasesFacade(){
        super(Clases.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Clases
    public List<Clases> getAll(){
        try{
            Query query = em.createQuery("select u from Clases u order by u.idClases asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Clases.", e);
        }
    return null;
   }
}