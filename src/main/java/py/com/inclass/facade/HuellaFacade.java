package py.com.inclass.facade;

import py.com.inclass.entities.Huella;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class HuellaFacade  extends AbstractFacade<Huella>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public HuellaFacade(){
        super(Huella.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Huella
    public List<Huella> getAll(){
        try{
            Query query = em.createQuery("select u from Huella u order by u.idHuella asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Huellas.", e);
        }
    return null;
   }
}