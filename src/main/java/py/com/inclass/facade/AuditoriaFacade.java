package py.com.inclass.facade;

import py.com.inclass.entities.Auditoria;
import py.com.inclass.entities.Auditoria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class AuditoriaFacade  extends AbstractFacade<Auditoria>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public AuditoriaFacade(){
        super(Auditoria.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Asistencias
    public List<Auditoria> getAll(){
        try{
            Query query = em.createQuery("select u from Asistencia u order by u.idAuditoria asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Auditorias.", e);
        }
    return null;
   }
}