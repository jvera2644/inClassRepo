package py.com.inclass.facade;

import py.com.inclass.entities.Parametro;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class ParametroFacade  extends AbstractFacade<Parametro>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public ParametroFacade() {
        super(Parametro.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Parametro
    public List<Parametro> getAll() {
        try {
            Query query = em.createQuery("select u from Parametro u order by u.idParametro asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los parámetros del sistema.", e);
            throw new RuntimeException("Error al obtener los parámetros del sistema.", e);
        }
    }
    
    public Parametro getParametroByCodigo(String codigo){
        try {
            Query query = em.createQuery("select u from Parametro u where upper(u.codigo) = upper(:codigo) ");
            query.setParameter("codigo", codigo);
            return (Parametro) query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error al obtener el parámetro "+ codigo +" del sistema.", e);
            throw new RuntimeException("Error al obtener el parámetro "+ codigo +" del sistema.", e);
        }
    }
    
}