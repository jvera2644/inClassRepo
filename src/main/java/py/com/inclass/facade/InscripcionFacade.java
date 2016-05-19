package py.com.inclass.facade;

import py.com.inclass.entities.Inscripcion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class InscripcionFacade  extends AbstractFacade<Inscripcion>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public InscripcionFacade(){
        super(Inscripcion.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Inscripcion
    public List<Inscripcion> getAll(){
        try{
            Query query = em.createQuery("select u from Inscripcion u order by u.idInscripcion asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Inscripciones.", e);
        }
    return null;
   }
}