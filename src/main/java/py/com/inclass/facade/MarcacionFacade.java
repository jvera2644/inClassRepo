package py.com.inclass.facade;

import java.util.Date;
import py.com.inclass.entities.Marcacion;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.entities.Asistencia;
import py.com.inclass.entities.Horario;
import py.com.inclass.entities.Persona;

@Stateless 
public class MarcacionFacade  extends AbstractFacade<Marcacion>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;
    
    @Resource
    private SessionContext context;
    
    @EJB
    private AsistenciaFacade asistenciaFacade;
    

    public MarcacionFacade() {
        super(Marcacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Marcacion
    public List<Marcacion> getAll() {
        try {
            Query query = em.createQuery("select u from Marcacion u order by u.idMarcacion asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las marcaciones ingresadas.", e);
        }
        return null;
    }
    
    public List<Marcacion> getMarcacionesDentroRango(Date fechaHoraFinLimite, Persona persona) {
        try {
            Query query = em.createQuery("select u from Marcacion u where u.fecha <= :fechaHoraFinLimite and u.idUsuario.idPersona = :persona and u.verificada = false order by u.idMarcacion asc");
            query.setParameter("fechaHoraFinLimite", fechaHoraFinLimite);
            query.setParameter("persona", persona);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las marcaciones ingresadas dentro del rango. Fin: "+fechaHoraFinLimite + " alumno: "+persona.getNumeroDocumento(), e);
        }
        return null;
    }
    
    public List<Marcacion> getMarcacionesFueraRango(Date fechaHoraFinLimite, Persona persona) {
        try {
                Query query = em.createQuery("select u from Marcacion u where u.fecha > :fechaHoraFinLimite and u.idUsuario.idPersona = :persona and u.verificada = false order by u.idMarcacion asc");
            query.setParameter("fechaHoraFinLimite", fechaHoraFinLimite);
            query.setParameter("persona", persona);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener las marcaciones ingresadas despues de la fecha/hora: "+fechaHoraFinLimite + " alumno: "+persona.getNumeroDocumento(), e);
        }
        return null;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean confirmarMarcaciones(List<Marcacion> marcaciones, Horario horario){
        boolean retorno = true;
        try{
            for(Marcacion m: marcaciones){
                if(m.getHabilitado()){
                    Asistencia a = new Asistencia();
                    a.setFechaHora(new Date(System.currentTimeMillis()));
                    a.setIdPersona(m.getIdUsuario().getIdPersona());
                    a.setIdHorario(horario);
                    asistenciaFacade.create(a);
                }
            }
            
            for(Marcacion m: marcaciones){
                m.setVerificada(true);
                edit(m);
            }
            
        }catch(Exception e){
            context.setRollbackOnly();
            retorno = false;
            logger.error("Error al confirmar las marcaciones de los alumnos.", e);
            throw new RuntimeException("Error al confirmar las marcaciones de los alumnos.", e);
        }
        return retorno;
    }
    
}