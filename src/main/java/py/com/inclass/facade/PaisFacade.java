package py.com.inclass.facade;

import py.com.inclass.entities.Pais;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class PaisFacade  extends AbstractFacade<Pais>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public PaisFacade(){
        super(Pais.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Pais
    public List<Pais> getAll(){
        try{
            Query query = em.createQuery("select u from Pais u order by u.idPais asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Paises.", e);
        }
    return null;
   }
}