package py.com.inclass.facade;

import py.com.inclass.entities.Nacionalidad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class NacionalidadFacade  extends AbstractFacade<Nacionalidad>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public NacionalidadFacade(){
        super(Nacionalidad.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Nacionalidad
    public List<Nacionalidad> getAll(){
        try{
            Query query = em.createQuery("select u from Nacionalidad u order by u.idNacionalidad asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Nacionalidades.", e);
        }
    return null;
   }
}