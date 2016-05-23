package py.com.inclass.facade;

import py.com.inclass.entities.TipoExcepcion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class TipoExcepcionFacade  extends AbstractFacade<TipoExcepcion>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public TipoExcepcionFacade(){
        super(TipoExcepcion.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //TipoExcepcion
    public List<TipoExcepcion> getAll(){
        try{
            Query query = em.createQuery("select u from TipoExcepcion u order by u.idTTipoExcepcion asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Tipos de Excepcion.", e);
        }
    return null;
   }
}