package py.com.inclass.facade;

import py.com.inclass.entities.Asistencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class AsistenciaFacade  extends AbstractFacade<Asistencia>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public AsistenciaFacade(){
        super(Asistencia.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Asistencias
    public List<Asistencia> getAll(){
        try{
            Query query = em.createQuery("select u from Asistencia u order by u.idAsistencia asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Asistencias.", e);
        }
    return null;
   }
}