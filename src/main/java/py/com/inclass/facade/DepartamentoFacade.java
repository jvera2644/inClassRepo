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

    public DepartamentoFacade() {
        super(Departamento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Departamento
    public List<Departamento> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Departamento u where u.estado = 1 order by u.idDepartamento asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los departamentos activos.", e);
            throw new RuntimeException("Error al obtener los departamentos activos.", e);
        }
        //return null;
    }

    public List<Departamento> getAll() {
        try {
            Query query = em.createQuery("select u from Departamento u order by u.idDepartamento asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los departamentos.", e);
        }
        return null;
    }
    
    public List<Departamento> getDepartamentosPorPais(Integer idPais){
        try {
            Query query = em.createQuery("select u from Departamento u where u.idPais.idPais = :idPais");
            query.setParameter("idPais", idPais);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los departamentos del país " + idPais, e);
        }
        return null;
    }
}
