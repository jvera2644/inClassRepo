/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Rol;
import py.com.inclass.entities.Usuario;

/**
 *
 * @author Edu
 */

@Stateless
public class RolFacade extends AbstractFacade<Rol>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    public RolFacade() {
        super(Rol.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Rol> getAll() {
        try {
            Query query = em.createQuery("select u from Rol u order by u.idRol asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los roles del sistema.", e);
        }
        return null;
    }
    
    public List<Rol> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Rol u where u.estado = 1 order by u.idRol asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los roles activos del sistema.", e);
        }
        return null;
    }
    
    public boolean tieneRolAdministrador(Usuario usuario){
        try{
            for(Rol r: usuario.getRolCollection()){
                if(r.getDescripcion().equalsIgnoreCase("administrador")){
                    return true;
                }
            }
        }catch(Exception e){
            logger.error("Error al obtener todos los roles activos del usuario: "+usuario.getUsuario(), e);
        }
        return false;
    }
    
    public boolean tieneRolAlumno(Usuario usuario){
        try{
            for(Rol r: usuario.getRolCollection()){
                if(r.getDescripcion().equalsIgnoreCase("alumno")){
                    return true;
                }
            }
        }catch(Exception e){
            logger.error("Error al obtener todos los roles activos del usuario: "+usuario.getUsuario(), e);
        }
        return false;
    }
    
}   
