/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import py.com.inclass.entities.Barrio;
import py.com.inclass.entities.Persona;
import py.com.inclass.entities.Ciudad;
import py.com.inclass.entities.Departamento;
import py.com.inclass.entities.Nacionalidad;
import py.com.inclass.entities.Pais;
import py.com.inclass.entities.TipoDocumento;
import py.com.inclass.facade.BarrioFacade;
import py.com.inclass.facade.PersonaFacade;
import py.com.inclass.facade.CiudadFacade;
import py.com.inclass.facade.DepartamentoFacade;
import py.com.inclass.facade.NacionalidadFacade;
import py.com.inclass.facade.PaisFacade;
import py.com.inclass.facade.TipoDocumentoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class PersonaBean extends BaseBean {
    
    //facades
    @EJB
    private PersonaFacade personaFacade;
    
    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;
    
    @EJB
    private PaisFacade paisFacade;
    
    @EJB
    private DepartamentoFacade departamentoFacade;
    
    @EJB
    private CiudadFacade ciudadFacade;
    
    @EJB
    private BarrioFacade barrioFacade;
    
    @EJB
    private NacionalidadFacade nacionalidadFacade;
    
    
    //variables de clase
    private List<Persona> personas;
    private Persona persona;
    private boolean modificar;
    private List<SelectItem> paises;
    private List<SelectItem> departamentos;
    private List<SelectItem> ciudades;
    private List<SelectItem> barrios;
    private List<SelectItem> nacionalidades;
    private List<SelectItem> tiposDocumentos;
    
    private Pais paisSeleccionado;
    private Departamento departamentoSeleccionado;
    private Ciudad ciudadSeleccionado;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                persona.setEstado(1);
                personaFacade.create(persona);
            }else{
                //edit
                personaFacade.edit(persona);
            }
            setInfoMessage(getMensajeGuardar());
            setPersonas(personaFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            personas = personaFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        persona = new Persona();
        modificar = false;
        paisSeleccionado = null;
        departamentoSeleccionado = null;
        ciudadSeleccionado = null;
        abrirNuevoPersona("PF('dialogo').show(); PF('dialogo').content.scrollTop('0')");
    }
    
    public void editar(){
        
        try {
            Persona p = personaFacade.find(persona.getIdPersona());
            Barrio barrio = p.getIdBarrio();
            ciudadSeleccionado = barrio.getIdCiudad();
            departamentoSeleccionado = ciudadSeleccionado.getIdDepartamento();
            paisSeleccionado = departamentoSeleccionado.getIdPais();

            cargarPaisesSeleccion();
            cargarDepartamentosPorPais();
            cargarCiudadesPorDepartamento();
            cargarBarriosPorCiudad();
            
        } catch (Exception e) {
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }

        abrirNuevoPersona("PF('dialogo').show();");
    }
    
    public void eliminar(){
        try{
            Persona td = persona;
            td.setEstado(0);
            personaFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setPersonas(personaFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoPersona(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        paises = new ArrayList<SelectItem>();
        departamentos = new ArrayList<SelectItem>();
        ciudades = new ArrayList<SelectItem>();
        barrios = new ArrayList<SelectItem>();
        nacionalidades = new ArrayList<SelectItem>();
        tiposDocumentos = new ArrayList<SelectItem>();
        paisSeleccionado = null;
        departamentoSeleccionado = null;
        ciudadSeleccionado = null;
        cargarPaisesSeleccion();
        cargarTiposDocumentosSeleccion();
        cargarNacionalidadesSeleccion();
    }
    
    private void cargarPaisesSeleccion() {
        paises = new ArrayList<SelectItem>();                      
        try {
            List<Pais> listaSource = paisFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Pais p : listaSource) {
                        paises.add(new SelectItem(p, p.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los países del sistema", e);
        }
    }
    
    private void cargarNacionalidadesSeleccion() {
        nacionalidades = new ArrayList<SelectItem>();                      
        try {
            List<Nacionalidad> listaSource = nacionalidadFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Nacionalidad n : listaSource) {
                        nacionalidades.add(new SelectItem(n, n.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar las nacionalidades del sistema", e);
        }
    }
    
    private void cargarTiposDocumentosSeleccion() {
        tiposDocumentos = new ArrayList<SelectItem>();                      
        try {
            List<TipoDocumento> listaSource = tipoDocumentoFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (TipoDocumento td : listaSource) {
                        tiposDocumentos.add(new SelectItem(td, td.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los tipos de documentos", e);
        }
    }
    
    public void onChangePais(){
        cargarDepartamentosPorPais();
    }
    
    public void onChangeDepartamento(){
        cargarCiudadesPorDepartamento();
    }
    
    public void onChangeCiudad(){
        cargarBarriosPorCiudad();
    }
    
    private void cargarDepartamentosPorPais(){
        departamentos = new ArrayList<SelectItem>();
        try {
            List<Departamento> listaSource = departamentoFacade.getDepartamentosPorPais(paisSeleccionado.getIdPais());
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Departamento d : listaSource) {
                        departamentos.add(new SelectItem(d, d.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar departamentos para el pais", e);
        }
    }
    
    private void cargarCiudadesPorDepartamento(){
        ciudades = new ArrayList<SelectItem>();
        try {
            List<Ciudad> listaSource = ciudadFacade.findCiudadesPorDepartamento(departamentoSeleccionado.getIdDepartamento());
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Ciudad c : listaSource) {
                        ciudades.add(new SelectItem(c, c.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar departamentos por pais", e);
        }
    }
    
    private void cargarBarriosPorCiudad(){
        barrios = new ArrayList<SelectItem>();
        try {
            List<Barrio> listaSource = barrioFacade.findBarriosPorCiudad(ciudadSeleccionado.getIdCiudad());
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Barrio b : listaSource) {
                        barrios.add(new SelectItem(b, b.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar barrios por ciudad", e);
        }
    }
    
    
    //getters && setter
    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }
    
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<SelectItem> ciudades) {
        this.ciudades = ciudades;
    }

    public List<SelectItem> getPaises() {
        return paises;
    }

    public void setPaises(List<SelectItem> paises) {
        this.paises = paises;
    }

    public List<SelectItem> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<SelectItem> departamentos) {
        this.departamentos = departamentos;
    }

    public List<SelectItem> getBarrios() {
        return barrios;
    }

    public void setBarrios(List<SelectItem> barrios) {
        this.barrios = barrios;
    }

    public List<SelectItem> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(List<SelectItem> nacionalidades) {
        this.nacionalidades = nacionalidades;
    }

    public List<SelectItem> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public Pais getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(Pais paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }

    public Departamento getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(Departamento departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public Ciudad getCiudadSeleccionado() {
        return ciudadSeleccionado;
    }

    public void setCiudadSeleccionado(Ciudad ciudadSeleccionado) {
        this.ciudadSeleccionado = ciudadSeleccionado;
    }

    
}
