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
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import py.com.inclass.entities.Clases;
import py.com.inclass.entities.Inscripcion;
import py.com.inclass.entities.Marcacion;
import py.com.inclass.entities.Parametro;

import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.ClasesFacade;
import py.com.inclass.facade.InscripcionFacade;
import py.com.inclass.facade.MarcacionFacade;
import py.com.inclass.facade.ParametroFacade;

import py.com.inclass.util.BaseBean;
import py.com.inclass.util.DateUtil;
import py.com.inclass.util.ParametroEnum;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class LlamarListaBean extends BaseBean {
    
    @ManagedProperty(value = "#{SecurityBean.usuario}")
    private Usuario usuario;
    
    @EJB
    private ParametroFacade parametroFacade;
    
    @EJB
    private MarcacionFacade marcacionFacade;
    
    @EJB
    private ClasesFacade claseFacade;
    
    @EJB
    private InscripcionFacade inscripcionFacade;
    
    
    //variables de clase
    private Clases clase;
    private List<Marcacion> listadoMarcacionesDentroRango;
    private List<Marcacion> listadoMarcacionesFueraRango;
    private List<Marcacion> listadoMarcaciones;
    private String idClase;
    private List<Inscripcion> inscripciones;
    
    
    @PostConstruct
    public void init() {
        idClase = getFacesContext().getExternalContext().getRequestParameterMap().get("claseId");
        inicializarMenu();
    }
    
    private void inicializarMenu(){
        listadoMarcaciones = new ArrayList<Marcacion>();
        inscripciones = new ArrayList<Inscripcion>();
        try{
            //obtener las marcaciones que se encuentran dentro del rango establecido
            clase = claseFacade.find(new Integer(idClase));
            Parametro p = parametroFacade.getParametroByCodigo(ParametroEnum.PAR_CAN_MIN_ALU.getCodigo());
            Date fechaHoraInicioLimite = clase.getFechaHoraInicio();
            Date fechaHoraFinLimite = DateUtil.sumarMinutosFecha(fechaHoraInicioLimite, Integer.parseInt(p.getValor()));
            verificarAlumnosInscriptosEnHorario();
            for(Inscripcion i: inscripciones){
                listadoMarcacionesDentroRango = marcacionFacade.getMarcacionesDentroRango(fechaHoraFinLimite, i.getIdPersona());
                listadoMarcacionesFueraRango = marcacionFacade.getMarcacionesFueraRango(fechaHoraFinLimite, i.getIdPersona());
                cargarListaMarcacion();
            }      
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public String listarMarcacionesDeAlumnos(){
        return "/secure/view/llamarLista.xhtml";        
    }
    
    private void cargarListaMarcacion(){
        for(Marcacion m: listadoMarcacionesDentroRango){
            m.setMarcacionFueraRango(false);
            listadoMarcaciones.add(m);
        }
        
        for(Marcacion m: listadoMarcacionesFueraRango){
            m.setMarcacionFueraRango(true);
            listadoMarcaciones.add(m);
        }
    }
    
    private void verificarAlumnosInscriptosEnHorario(){
        try{
            inscripciones = inscripcionFacade.getInscriptosEnHorario(clase.getIdHorario());
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }        
    }
    
    public void puntearListaDeAlumnos(){
        try{
        }catch(Exception e){
        }
    }
    
    public String confirmarLista(){
        int contadorMarcaciones = 0;
        try{
            for(Marcacion m: listadoMarcaciones){
                if(m.getHabilitado()){
                    contadorMarcaciones++;
                }
            } 
            if(contadorMarcaciones == 0){
                setWarnMessage("No puede confirmar la operación. Debe marcar por lo menos uno");
                return null;
            }else{
                //confirmar marcaciones, poblar asistencias
                marcacionFacade.confirmarMarcaciones(listadoMarcaciones, clase.getIdHorario());
                //finalizar la clase
                Clases c = clase;
                c.setFechaHoraFin(new Date(System.currentTimeMillis()));
                claseFacade.edit(c);
                return "/secure/view/clase.xhtml";
            }
        }catch(Exception e){
            setErrorMessage(getMensajeGuardar());
            logger.error(getMensajeGuardar(), e);
            return null;
        }
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Clases getClase() {
        return clase;
    }

    public void setClase(Clases clase) {
        this.clase = clase;
    }

    public List<Marcacion> getListadoMarcaciones() {
        return listadoMarcaciones;
    }

    public void setListadoMarcaciones(List<Marcacion> listadoMarcaciones) {
        this.listadoMarcaciones = listadoMarcaciones;
    }
    
    
    
}
