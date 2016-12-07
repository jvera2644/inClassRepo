package py.com.inclass.facade;

import java.util.Date;
import py.com.inclass.entities.Turno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.inclass.util.StringUtil;

@Stateless 
public class TurnoFacade  extends AbstractFacade<Turno>{
    
    @PersistenceContext(unitName = "INCLASSPU")
    private EntityManager em;

    public TurnoFacade() {
        super(Turno.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    //Turno
    public List<Turno> getAll() {
        try {
            Query query = em.createQuery("select u from Turno u order by u.idTurno asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los turnos.", e);
        }
        return null;
    }

    public List<Turno> getAllActivos() {
        try {
            Query query = em.createQuery("select u from Turno u where u.estado = 1 order by u.idTurno asc");
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener los turnos activos.", e);
            throw new RuntimeException("Error al obtener los turnos activos.", e);
        }
    }
    
    public boolean verificarHorarioTurno(Turno turno, Date hora){
        try{
            Query query = em.createQuery("select u from Turno u where u = :turno");
            query.setParameter("turno", turno);
            Turno t = (Turno) query.getSingleResult();
            if(hora.compareTo(StringUtil.convertirHoraStringADate(t.getHoraInicio())) == 0 ||
                    hora.after(StringUtil.convertirHoraStringADate(t.getHoraInicio()))){
                if(hora.compareTo(StringUtil.convertirHoraStringADate(t.getHoraFin())) == 0 ||
                    hora.before(StringUtil.convertirHoraStringADate(t.getHoraFin()))){
                    return true;
                }    
            }
            return false;
        }catch(Exception e){
            logger.error("Error al verificar el horario del turno: "+turno.getDescripcion(), e);
            throw new RuntimeException("Error al verificar el horario del turno: "+turno.getDescripcion(), e);
        }
    }
    
}