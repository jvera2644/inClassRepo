package py.com.inclass.facade;

import py.com.inclass.entities.Semestre;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class SemestreFacade  extends AbstractFacade<Semestre>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public SemestreFacade(){
        super(Semestre.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Semestre
    public List<Semestre> getAll(){
        try{
            Query query = em.createQuery("select u from Semestre u order by u.idSemestre asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Semestres.", e);
        }
    return null;
   }
}
