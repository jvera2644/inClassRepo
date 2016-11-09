/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import py.com.inclass.entities.Turno;
import py.com.inclass.facade.TurnoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class TurnoBean extends BaseBean {
    
    //facades
    @EJB
    private TurnoFacade turnoFacade;
    
    //variables de clase
    private List<Turno> turnos;
    private Turno turno;
    private boolean modificar;
    private boolean habilitaBotonGuardar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                turno.setEstado(1);
                turnoFacade.create(turno);
            }else{
                //edit
                turnoFacade.edit(turno);
            }
            setInfoMessage(getMensajeGuardar());
            setTurnos(turnoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            turnos = turnoFacade.getAllActivos();
            habilitaBotonGuardar = false;
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        turno = new Turno();
        modificar = false;
        abrirNuevoTurno("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Turno t = turno;
            t.setEstado(0);
            turnoFacade.edit(t);
            setInfoMessage(getMensajeGuardar());
            setTurnos(turnoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoTurno(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
//    public void turnoInicioOnChange(){
//        habilitaBotonGuardar = false;
//        try {
//            int posicionDosPuntos = turno.getHoraInicio().indexOf(":");
//            String horaPrimera = turno.getHoraInicio().substring(0, posicionDosPuntos);
//            String horaSegunda = turno.getHoraInicio().substring(posicionDosPuntos + 1);
//
//            if (Integer.parseInt(horaPrimera) == 0 || 
//                Integer.parseInt(horaPrimera) > 24 || 
//                Integer.parseInt(horaSegunda) > 59) {
//                setWarnMessage(getMensajeWarningTurno());
//                habilitaBotonGuardar = true;
//            }
//                       
//            
//        } catch (Exception e) {
//            setWarnMessage(getMensajeWarningTurno());
//            logger.warn(getMensajeWarningTurno(), e);
//        }
//        
//    }
    
    
//    public void turnoFinalizacionOnChange(){
//        habilitaBotonGuardar = false;
//        try {
//            int posicionDosPuntos = turno.getHoraFin().indexOf(":");
//            String horaPrimera = turno.getHoraFin().substring(0, posicionDosPuntos);
//            String horaSegunda = turno.getHoraFin().substring(posicionDosPuntos + 1);
//
//            if (Integer.parseInt(horaPrimera) == 0 || 
//                Integer.parseInt(horaPrimera) > 24 || 
//                Integer.parseInt(horaSegunda) > 59) {
//                setWarnMessage(getMensajeWarningTurno());
//                habilitaBotonGuardar = true;
//            }
//                       
//            
//        } catch (Exception e) {
//            setWarnMessage(getMensajeWarningTurno());
//            logger.warn(getMensajeWarningTurno(), e);
//        }
//        
//    }
//        
    //getters && setters
    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public boolean getHabilitaBotonGuardar() {
        return habilitaBotonGuardar;
    }

    public void setHabilitaBotonGuardar(boolean habilitaBotonGuardar) {
        this.habilitaBotonGuardar = habilitaBotonGuardar;
    }
    
}
