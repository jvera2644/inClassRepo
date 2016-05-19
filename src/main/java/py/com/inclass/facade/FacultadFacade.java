package py.com.inclass.facade;

import py.com.inclass.entities.Facultad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class FacultadFacade  extends AbstractFacade<Facultad>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public FacultadFacade(){
        super(Facultad.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Facultad
    public List<Facultad> getAll(){
        try{
            Query query = em.createQuery("select u from Facultad u order by u.idFacultad asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Facultad.", e);
        }
    return null;
   }
}