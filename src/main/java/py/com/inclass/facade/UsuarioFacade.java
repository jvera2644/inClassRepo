/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Usuario;

/**
 *
 * @author Edu
 */

@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Usuario> getAll() {
        try {
            Query query = em.createQuery("select u from Usuario u order by u.idUsuario asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los usuarios del sistema.", e);
        }
        return null;
    }
    
    public List<Usuario> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Usuario u where u.estado = 1 order by u.idUsuario asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los usuarios activos del sistema.", e);
        }
        return null;
    }
    
    public Usuario getUsuarioPorUsuarioContrasena(String nombreUsuario, String contrasena){
        Usuario u = null;
        try{
            Query q = em.createQuery("select u from Usuario u where UPPER(u.usuario) = UPPER(:nombreUsuario) and u.contrasena = :contrasena");
            q.setParameter("nombreUsuario", nombreUsuario);
            q.setParameter("contrasena", contrasena);
            u = (Usuario)q.getSingleResult();
        }catch(NoResultException nre){
            logger.warn("No existe el usuario (por usuario y contraseña) "+ nombreUsuario + ".", nre);
        }catch(Exception e){
            logger.error("Error al obtener el usuario (por usuario y contraseña) "+ nombreUsuario + ".", e);
        }
        return u;
    }        
    
    
    public Usuario getUsuarioPorNombreUsuario(String nombreUsuario){
        Usuario u = null;
        try{
            Query q = em.createQuery("select u from Usuario u where UPPER(u.usuario) = UPPER(:nombreUsuario)");
            q.setParameter("nombreUsuario", nombreUsuario);
            u = (Usuario)q.getSingleResult();
        }catch(NoResultException nre){
            logger.warn("No existe el usuario (por nombre de usuario) "+ nombreUsuario + ".", nre);
        }catch(Exception e){
            logger.error("Error al obtener el usuario (por nombre de usuario) "+ nombreUsuario + ".", e);
        }
        return u;
    }   
}
