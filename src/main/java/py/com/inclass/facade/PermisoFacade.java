package py.com.inclass.facade;

import py.com.inclass.entities.Permiso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class PermisoFacade  extends AbstractFacade<Permiso>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public PermisoFacade(){
        super(Permiso.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Permiso
    public List<Permiso> getAll(){
        try{
            Query query = em.createQuery("select u from Permiso u order by u.idPermiso asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener los Permisos.", e);
        }
    return null;
   }
    
    public List<Permiso> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Permiso u where u.estado = 1 order by u.idPermiso asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los permisos activos del sistema.", e);
        }
        return null;
    }
}