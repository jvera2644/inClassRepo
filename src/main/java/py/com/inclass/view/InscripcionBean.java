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
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import py.com.inclass.entities.Inscripcion;
import py.com.inclass.entities.Horario;
import py.com.inclass.entities.Persona;
import py.com.inclass.facade.InscripcionFacade;
import py.com.inclass.facade.HorarioFacade;
import py.com.inclass.facade.PersonaFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class InscripcionBean extends BaseBean {
    
    //facades
    @EJB
    private InscripcionFacade inscripcionFacade;
    
    @EJB
    private HorarioFacade horarioFacade;
    
    @EJB
    private PersonaFacade personaFacade;
    
    //variables de clase
    private List<Inscripcion> inscripciones;
    private Inscripcion inscripcion;
    private boolean modificar;
    private List<SelectItem> horarios;
    private List<SelectItem> personas;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                inscripcion.setEstado(1);
                inscripcion.setFecha(new Date());
                inscripcionFacade.create(inscripcion);
            }else{
                //edit
                inscripcionFacade.edit(inscripcion);
            }
            setInfoMessage(getMensajeGuardar());
            setInscripciones(inscripcionFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            inscripciones = inscripcionFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        inscripcion = new Inscripcion();
        modificar = false;
        abrirNuevoInscripcion("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Inscripcion td = inscripcion;
            td.setEstado(0);
            inscripcionFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setInscripciones(inscripcionFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoInscripcion(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        horarios = new ArrayList<SelectItem>();
        personas = new ArrayList<SelectItem>();
        cargarHorariosSeleccion();
        cargarPersonasSeleccion();
    }
    
    private void cargarPersonasSeleccion(){
        personas = new ArrayList<SelectItem>();                      
        try {
            List<Persona> listaSource = personaFacade.getAllPersonasConRolAlumnoActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Persona p : listaSource) {
                        personas.add(new SelectItem(p, p.getApellido() + " " + p.getNombre()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar las personas con rol de alumno dentro sistema", e);
        }
    }
    
    private void cargarHorariosSeleccion() {
        horarios = new ArrayList<SelectItem>();                      
        try {
            List<Horario> listaSource = horarioFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Horario h : listaSource) {
                        horarios.add(new SelectItem(h, h.getDia() + " - " + h.getHoraInicio() + " - " + h.getHoraFin() + " - " + h.getIdMateria().getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los horarios de clases.", e);
        }
    }
        
    //getters && setter
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    
    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<SelectItem> horarios) {
        this.horarios = horarios;
    }

    public List<SelectItem> getPersonas() {
        return personas;
    }

    public void setPersonas(List<SelectItem> personas) {
        this.personas = personas;
    }
    
    

}
