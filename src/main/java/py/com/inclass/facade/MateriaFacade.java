package py.com.inclass.facade;

import py.com.inclass.entities.Materia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class MateriaFacade  extends AbstractFacade<Materia>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public MateriaFacade(){
        super(Materia.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Materia
    public List<Materia> getAll(){
        try{
            Query query = em.createQuery("select u from Materia u order by u.idMateria asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Materias.", e);
        }
    return null;
   }
}