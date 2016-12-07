/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import py.com.inclass.entities.Clases;
import py.com.inclass.entities.Excepcion;
import py.com.inclass.entities.Horario;
import py.com.inclass.entities.Parametro;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.ClasesFacade;
import py.com.inclass.facade.ExcepcionFacade;
import py.com.inclass.facade.HorarioFacade;
import py.com.inclass.facade.ParametroFacade;
import py.com.inclass.facade.RolFacade;
import py.com.inclass.facade.TipoExcepcionFacade;
import py.com.inclass.util.BaseBean;
import py.com.inclass.util.DateUtil;
import py.com.inclass.util.ParametroEnum;
import py.com.inclass.util.StringUtil;


/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class HorarioProfesorBean extends BaseBean {
    
    @ManagedProperty(value = "#{SecurityBean.usuario}")
    private Usuario usuario;
    
    //facades
    @EJB
    private HorarioFacade horarioFacade;
    
    @EJB
    private ClasesFacade clasesFacade;
    
    @EJB
    private RolFacade rolFacade;
    
    @EJB
    private ExcepcionFacade excepcionFacade;
    
    @EJB
    private TipoExcepcionFacade tipoExcepcionFacade;
    
    @EJB
    private ParametroFacade parametroFacade;
    
    
    
    
    //variables de clase
    private List<Horario> horarios;
    private Horario horario;
    private String nombreApellidoProfesor;
    private String mensaje;
           
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    private void inicializarMenu(){
        //horarios del profesor logueado al sistema   
        if(rolFacade.tieneRolAdministrador(usuario)){
            horarios = horarioFacade.getAllActivos();
        }else{
            horarios = horarioFacade.getHorariosActivosPorPersona(usuario.getIdPersona());
        }
        
        if(!horarios.isEmpty()){
            for(Horario ho: horarios){
                if(clasesFacade.existeClaseIniciadaPorHorario(ho)){
                    ho.setClaseIniciadaEnHorario(true);
                }else{
                    ho.setClaseIniciadaEnHorario(false);
                }
            }
        }
        nombreApellidoProfesor = usuario.getIdPersona().getApellido() + " " + usuario.getIdPersona().getNombre();
    }
    
    
    public void iniciarClase(){
        
        Parametro p = null;
        try{
            
            if(verificarHorario()){
                
                Date horarioClaseInicio = StringUtil.convertirHoraStringADate(horario.getHoraInicio());
                p = parametroFacade.getParametroByCodigo(ParametroEnum.PAR_CAN_MIN_PRO.getCodigo());
                Date horarioToleranciaProfesor = DateUtil.sumarMinutosFecha(horarioClaseInicio, Integer.parseInt(p.getValor()));
                
                if(StringUtil.convertirHoraStringADate(DateUtil.getHoraActual()).after(horarioToleranciaProfesor)){
                    //se carga la excepcion del profesor por llegada tardía
                    Excepcion excep = new py.com.inclass.entities.Excepcion();
                    p = parametroFacade.getParametroByCodigo(ParametroEnum.PAR_EXC_LLE_TAR.getCodigo());
                    excep.setIdTipoExcepcion(tipoExcepcionFacade.getTipoExcepcionPorDescripcion(p.getValor()));
                    excep.setFehcaHoraCargaExcepciones(new Date(System.currentTimeMillis()));
                    excep.setFechaEvento(new Date());
                    excep.setIdUsuario(usuario);
                    excep.setObservacionEvento(horario.getIdPersona().getApellido() + " " + horario.getIdPersona().getNombre() + " INICIA CLASE FUERA DE HORA: "+ DateUtil.getHoraActual());
                    excepcionFacade.create(excep);
                }
                
                Clases c = new Clases();
                c.setIdPersona(horario.getIdPersona());
                c.setIdHorario(horario);
                c.setFechaHoraInicio(new Date(System.currentTimeMillis()));
                clasesFacade.create(c);
                setInfoMessage("La clase ha sido iniciada");

                if (rolFacade.tieneRolAdministrador(usuario)) {
                    setHorarios(horarioFacade.getAllActivos()); 
                } else {
                    setHorarios(horarioFacade.getHorariosActivosPorPersona(usuario.getIdPersona())); 
                }

                if (!horarios.isEmpty()) {
                    for (Horario ho : horarios) {
                        if (clasesFacade.existeClaseIniciadaPorHorario(ho)) {
                            ho.setClaseIniciadaEnHorario(true);
                        } else {
                            ho.setClaseIniciadaEnHorario(false);
                        }
                    }
                }
            }else{
                setErrorMessage(mensaje);
                logger.error("No puede iniciar la clase para el horario: "+horario.getIdHorario());
            }
            
            
        }catch(Exception e){
            setErrorMessage("Error al iniciar la clase");
            logger.error("Error al iniciar la clase para el horario: "+horario.getIdHorario(), e);
        }
    }
    
    private boolean verificarHorario(){
        
        String diaSemana = DateUtil.obtenerDiaSemana(new Date());
        if(!diaSemana.toUpperCase().equals(horario.getDia().toUpperCase())){
            mensaje = "No puede iniciar. Día no corresponde";
            return false;
        }else{
            Date horaActual = StringUtil.convertirHoraStringADate(DateUtil.getHoraActual());
            if (horaActual.before(StringUtil.convertirHoraStringADate(horario.getHoraInicio()))) {
                mensaje = "No puede iniciar antes del inicio del horario de clase";
                return false;
            }
            
            if (horaActual.after(StringUtil.convertirHoraStringADate(horario.getHoraFin()))) {
                mensaje = "No puede iniciar después de finalizar el horario de clase";
                return false;
            }
        }
        return true;
    }
    
        
    //getters && setters
    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreApellidoProfesor() {
        return nombreApellidoProfesor;
    }

    public void setNombreApellidoProfesor(String nombreApellidoProfesor) {
        this.nombreApellidoProfesor = nombreApellidoProfesor;
    }
       
    
}
