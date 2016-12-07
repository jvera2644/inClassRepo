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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.validation.ValidationException;
import org.primefaces.context.RequestContext;
import py.com.inclass.entities.Horario;
import py.com.inclass.entities.Materia;
import py.com.inclass.entities.Persona;
import py.com.inclass.entities.Turno;
import py.com.inclass.facade.HorarioFacade;
import py.com.inclass.facade.MateriaFacade;
import py.com.inclass.facade.PersonaFacade;
import py.com.inclass.facade.TurnoFacade;
import py.com.inclass.util.BaseBean;
import py.com.inclass.util.StringUtil;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class HorarioBean extends BaseBean {
    
    //facades
    @EJB
    private HorarioFacade horarioFacade;
    
    @EJB
    private MateriaFacade materiaFacade;
    
    @EJB
    private TurnoFacade turnoFacade;
    
    @EJB
    private PersonaFacade personaFacade;
    
    //variables de clase
    private List<Horario> horarios;
    private Horario horario;
    private boolean modificar;
    private List<SelectItem> materias;
    private List<SelectItem> turnos;
    private List<SelectItem> personas;
    private String nombreApellidoPersona;
    private String nroDocumentoPersona;
    private boolean habilitaBotonGuardar;
    private Turno turnoSeleccionado;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
       
    private void inicializarListasSeleccion(){
        materias = new ArrayList<SelectItem>();
        turnos = new ArrayList<SelectItem>();
        personas = new ArrayList<SelectItem>();
        cargarMateriasSeleccion();
        cargarTurnosSeleccion();
        cargarPersonasSeleccion();
        //prueba
        //List<Persona> lista = personaFacade.getAllPersonasConRolProfesorActivos();
    }
    
    private void cargarMateriasSeleccion() {
        materias = new ArrayList<SelectItem>();                      
        try {
            List<Materia> listaSource = materiaFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Materia m : listaSource) {
                        materias.add(new SelectItem(m, m.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar las materias del sistema", e);
        }
    }
    
    private void cargarTurnosSeleccion() {
        turnos = new ArrayList<SelectItem>();                      
        try {
            List<Turno> listaSource = turnoFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Turno t : listaSource) {
                        turnos.add(new SelectItem(t, t.getDescripcion() + " " + t.getHoraInicio() + " " + t.getHoraFin()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los turnos del sistema", e);
        }
    }
    
    private void cargarPersonasSeleccion(){
        personas = new ArrayList<SelectItem>();                      
        try {
            List<Persona> listaSource = personaFacade.getAllPersonasConRolProfesorActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Persona p : listaSource) {
                        personas.add(new SelectItem(p, p.getApellido() + " " + p.getNombre()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar las personas con rol de profesor dentro sistema", e);
        }
    }
    
    
    public void guardar(){
        try{
            //horario.setIdPersona(persona);
            if(!modificar){
                //create
                horario.setEstado(1);
                horarioFacade.create(horario);
            }else{
                //edit
                horarioFacade.edit(horario);
            }
            setInfoMessage(getMensajeGuardar());
            setHorarios(horarioFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        horarios = horarioFacade.getAllActivos();
        habilitaBotonGuardar = false;
    }
    
    public void nuevo(){
        horario = new Horario();
        modificar = false;
        nroDocumentoPersona = null;
        nombreApellidoPersona = null;
        abrirNuevoHorario("PF('dialogo').show();");
    }
    
    public String editar(){
        try{
            nroDocumentoPersona = horario.getIdPersona().getNumeroDocumento();
            nombreApellidoPersona = horario.getIdPersona().getApellido() + ", " + horario.getIdPersona().getNombre();
            //persona = horario.getIdPersona(); 
            turnoSeleccionado = horario.getIdTurno();
        }catch(Exception e){
            setErrorMessage("Error al editar un horario de clase.");
            logger.error("Error al editar un horario de clase.", e);
        }
        return null;
    }
    
    public void eliminar(){
        try{
            Horario td = horario;
            td.setEstado(0);
            horarioFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setHorarios(horarioFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoHorario(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
//    public void nroDocumentoOnChanged() {
//        habilitaBotonGuardar = false;
//        try{
//            //verificamos si el nro. de documento ingresado existe
//            persona = personaFacade.findByNroDocumento(nroDocumentoPersona);
//            if(persona != null){
//                setNombreApellidoPersona(persona.getNombre() + ", " + persona.getApellido());
//            }else{
//                setNombreApellidoPersona("");
//                setWarnMessage(nroDocumentoPersona + " no está registrado.");
//                habilitaBotonGuardar = true;
//                }
//
//            }catch(Exception e){
//            logger.error("Error al buscar persona por nro. de documento", e);
//        }
//    }
    
    public void turnoChange(){
        turnoSeleccionado = horario.getIdTurno();
    }
    
    public void validarHoraInicio(FacesContext context, UIComponent component, Object value) throws ValidatorException{
        String msg = "Hora inicio incorrecto";
        String horaInicio = (String) value;
        Date horaInicioDate = StringUtil.convertirHoraStringADate(horaInicio);
        //verificar si el horario ingresado corresponde al turno correspondiente.
        boolean validacionHorarioInicio = turnoFacade.verificarHorarioTurno(turnoSeleccionado, horaInicioDate);
        if(!validacionHorarioInicio){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
    }
    
    public void validarHoraFinalizacion(FacesContext context, UIComponent component, Object value) throws ValidatorException{
        String msg = "Hora finalización incorrecto";
        String horaFin = (String) value;
        Date horaFinDate = StringUtil.convertirHoraStringADate(horaFin);
        //verificar si el horario ingresado corresponde al turno correspondiente.
        boolean validacionHorarioInicio = turnoFacade.verificarHorarioTurno(turnoSeleccionado, horaFinDate);
        if(!validacionHorarioInicio){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
        }
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

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getMaterias() {
        return materias;
    }

    public void setMaterias(List<SelectItem> materias) {
        this.materias = materias;
    }

    public List<SelectItem> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<SelectItem> turnos) {
        this.turnos = turnos;
    }

    public String getNombreApellidoPersona() {
        return nombreApellidoPersona;
    }

    public void setNombreApellidoPersona(String nombreApellidoPersona) {
        this.nombreApellidoPersona = nombreApellidoPersona;
    }

    public String getNroDocumentoPersona() {
        return nroDocumentoPersona;
    }

    public void setNroDocumentoPersona(String nroDocumentoPersona) {
        this.nroDocumentoPersona = nroDocumentoPersona;
    }

    public boolean getHabilitaBotonGuardar() {
        return habilitaBotonGuardar;
    }

    public void setHabilitaBotonGuardar(boolean habilitaBotonGuardar) {
        this.habilitaBotonGuardar = habilitaBotonGuardar;
    }

    public List<SelectItem> getPersonas() {
        return personas;
    }

    public void setPersonas(List<SelectItem> personas) {
        this.personas = personas;
    }
       
}
