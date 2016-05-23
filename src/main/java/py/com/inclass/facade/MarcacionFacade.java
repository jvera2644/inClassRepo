package py.com.inclass.facade;

import py.com.inclass.entities.Marcacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class MarcacionFacade  extends AbstractFacade<Marcacion>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public MarcacionFacade(){
        super(Marcacion.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Marcacion
    public List<Marcacion> getAll(){
        try{
            Query query = em.createQuery("select u from Marcacion u order by u.idMarcacion asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Marcacion.", e);
        }
    return null;
   }
}