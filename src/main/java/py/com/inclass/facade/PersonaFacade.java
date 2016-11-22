/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Persona;
import py.com.inclass.util.DateUtil;

/**
 *
 * @author Edu
 */

@Stateless
public class PersonaFacade extends AbstractFacade<Persona>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    @EJB
    TipoDocumentoFacade tipoDocumentoFacade;
    
    @EJB
    BarrioFacade barrioFacade;
    
    @EJB
    NacionalidadFacade nacionalidadFacade;
    
    public PersonaFacade() {
        super(Persona.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Persona> getAll() {
        try {
            Query query = em.createQuery("select u from Persona u order by u.idPersona asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las personas del sistema.", e);
        }
        return null;
    }
    
    public List<Persona> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Persona u where u.estado = 1 order by u.idPersona asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las personas del sistema.", e);
            throw new RuntimeException("Error al obtener todas las personas del sistema.", e);
        }
    }
    
    public Persona findByNroDocumento(String nroDocumento) {
        try {
            Query query = em.createQuery("select u from Persona u where u.numeroDocumento like :numeroDocumento");
            query.setParameter("numeroDocumento", "%" + nroDocumento + "%");
            Persona p = (Persona) query.getSingleResult();
            return p;
        } catch (NoResultException ne) {
            logger.warn("No existe persona con numero de documento " + nroDocumento, ne);
            return null;
        } catch (Exception e) {
            logger.error("Error al obtener persona con numero de documento " + nroDocumento, e);
            return null;
        }
    }
    
    
    public List<Persona> findByNroDocumentoOnComplete(String nroDocumento) {
        try {
            Query query = em.createQuery("select u from Persona u where u.numeroDocumento like :numeroDocumento");
            query.setParameter("numeroDocumento", "%" + nroDocumento + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener persona con numero de documento " + nroDocumento, e);
            return null;
        }
    }
    
    public List<Persona> findByNombresApellidos(String nombreApellidoPersona){
         try {
            Query query = em.createQuery("select u from Persona u where u.nombre like :nombreApellidoPersona or u.apellido like :nombreApellidoPersona");
            query.setParameter("nombreApellidoPersona", "%" + nombreApellidoPersona + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener personas con nombres y/o apellidos " + nombreApellidoPersona, e);
            return null;
        }
    }
    
    public List<Persona> getAllPersonasConRolProfesorActivos() {
        try {
            Query query = em.createNativeQuery("select persona.id_persona, persona.nombre, persona.apellido, persona.id_tipo_documento, " + 
            "persona.numero_documento, persona.id_barrio, persona.direccion, persona.telefono, persona.celular, persona.correo, " + 
            "persona.fecha_nacimiento, persona.sexo, persona.id_nacionalidad, persona.estado " +        
            "FROM unidb.persona as persona JOIN unidb.usuario as usuario " +
            "ON persona.id_persona = usuario.id_persona " +
            "JOIN unidb.usuario_rol as usuario_rol ON " +
            "usuario.id_usuario = usuario_rol.id_usuario " +
            "JOIN unidb.rol as r ON " +
            "r.id_rol = usuario_rol.id_rol " +
            "WHERE r.id_rol = 3 AND persona.estado = 1");

             List<Object[]> results = query.getResultList();
             List<Persona> listaPersonas = new ArrayList<Persona>();
             for(Object[] result: results){
                Persona p = new Persona();
                p.setIdPersona(Integer.parseInt(result[0].toString()));
                p.setNombre(result[1].toString());
                p.setApellido(result[2].toString());
                p.setIdTipoDocumento(tipoDocumentoFacade.find(result[3]));
                p.setNumeroDocumento(result[4].toString());
                p.setIdBarrio(barrioFacade.find(result[5]));
                p.setDireccion(result[6].toString());
                p.setTelefono(result[7].toString());
                p.setCelular(result[8].toString());
                p.setCorreo(result[9].toString());
                p.setFechaNacimiento(DateUtil.formaterStringToDate(result[10].toString()));
                p.setSexo(result[11].toString());
                p.setIdNacionalidad(nacionalidadFacade.find(result[12]));
                p.setEstado(Integer.parseInt(result[13].toString()));
                listaPersonas.add(p);
             }
            
            return listaPersonas;
        } catch (Exception e) {
            logger.error("Error al obtener todas las personas con rol Profesor del sistema.", e);
            throw new RuntimeException("Error al obtener todas las personas con rol Profesor del sistema.", e);
        }
    }
    
    
    public List<Persona> getAllPersonasConRolAlumnoActivos() {
        try {
            Query query = em.createNativeQuery("select persona.id_persona, persona.nombre, persona.apellido, persona.id_tipo_documento, " + 
            "persona.numero_documento, persona.id_barrio, persona.direccion, persona.telefono, persona.celular, persona.correo, " + 
            "persona.fecha_nacimiento, persona.sexo, persona.id_nacionalidad, persona.estado " +        
            "FROM unidb.persona as persona JOIN unidb.usuario as usuario " +
            "ON persona.id_persona = usuario.id_persona " +
            "JOIN unidb.usuario_rol as usuario_rol ON " +
            "usuario.id_usuario = usuario_rol.id_usuario " +
            "JOIN unidb.rol as r ON " +
            "r.id_rol = usuario_rol.id_rol " +
            "WHERE r.id_rol = 2 AND persona.estado = 1");

             List<Object[]> results = query.getResultList();
             List<Persona> listaPersonas = new ArrayList<Persona>();
             for(Object[] result: results){
                Persona p = new Persona();
                p.setIdPersona(Integer.parseInt(result[0].toString()));
                p.setNombre(result[1].toString());
                p.setApellido(result[2].toString());
                p.setIdTipoDocumento(tipoDocumentoFacade.find(result[3]));
                p.setNumeroDocumento(result[4].toString());
                p.setIdBarrio(barrioFacade.find(result[5]));
                p.setDireccion(result[6].toString());
                p.setTelefono(result[7].toString());
                p.setCelular(result[8].toString());
                p.setCorreo(result[9].toString());
                p.setFechaNacimiento(DateUtil.formaterStringToDate(result[10].toString()));
                p.setSexo(result[11].toString());
                p.setIdNacionalidad(nacionalidadFacade.find(result[12]));
                p.setEstado(Integer.parseInt(result[13].toString()));
                listaPersonas.add(p);
             }
            
            return listaPersonas;
        } catch (Exception e) {
            logger.error("Error al obtener todas las personas con rol Alumno del sistema.", e);
            throw new RuntimeException("Error al obtener todas las personas con rol Alumno del sistema.", e);
        }
    }
    
}
