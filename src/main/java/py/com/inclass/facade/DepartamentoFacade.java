package py.com.inclass.facade;

import py.com.inclass.entities.Departamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class DepartamentoFacade  extends AbstractFacade<Departamento>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public DepartamentoFacade(){
        super(Departamento.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    //Departamento
    public List<Departamento> getAll(){
        try{
            Query query = em.createQuery("select u from Departamento u order by u.idDepartamento asc");
            return query.getResultList();
        }catch(Exception e){
            logger.error("Error al obtener las Departamento.", e);
        }
    return null;
   }
}