package py.com.inclass.facade;

import py.com.inclass.entities.Noticia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless 
public class NoticiaFacade  extends AbstractFacade<Noticia>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public NoticiaFacade() {
        super(Noticia.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Noticia
    public List<Noticia> getAll() {
        try {
            Query query = em.createQuery("select u from Noticia u order by u.idNoticia asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las noticias.", e);
        }
        return null;
    }
    
    public List<Noticia> getAllPublicados() {
        try {
            Query query = em.createQuery("select u from Noticia u where u.publicado = 1 order by u.idNoticia asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las noticias publicadas.", e);
        }
        return null;
    }
    
    public List<Noticia> getAllActivosPublicados() {
        try {
            Query query = em.createQuery("select u from Noticia u where u.publicado = 1 and u.estado = 1 order by u.idNoticia asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las noticias activas publicadas.", e);
        }
        return null;
    }
    
    public List<Noticia> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Noticia u where u.estado = 1 order by u.idNoticia asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las noticias activas.", e);
        }
        return null;
    }
    
}