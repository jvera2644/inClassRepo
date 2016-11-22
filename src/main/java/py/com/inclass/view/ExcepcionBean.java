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
import py.com.inclass.entities.Excepcion;
import py.com.inclass.entities.TipoExcepcion;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.ExcepcionFacade;
import py.com.inclass.facade.TipoExcepcionFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class ExcepcionBean extends BaseBean {
    
    @ManagedProperty(value = "#{SecurityBean.usuario}")
    private Usuario usuario;
    
    //facades
    @EJB
    private ExcepcionFacade excepcionFacade;
    
     @EJB
    private TipoExcepcionFacade tipoExcepcionFacade;
    
    //variables de clase
    private List<Excepcion> excepciones;
    private Excepcion excepcion;
    private boolean modificar;
    private List<SelectItem> tiposExcepciones;
    
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                excepcion.setEstado(1);
                excepcion.setIdUsuario(usuario);
                Date now = new Date(System.currentTimeMillis());
                excepcion.setFehcaHoraCargaExcepciones(now);
                excepcionFacade.create(excepcion);
            }else{
                //edit
                excepcionFacade.edit(excepcion);
            }
            setInfoMessage(getMensajeGuardar());
            setExcepciones(excepcionFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            excepciones = excepcionFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        excepcion = new Excepcion();
        modificar = false;
        abrirNuevoExcepcion("PF('dialogo').show();");
    }
    
    public String editar(){ 
        return null;
    }
    
    public void eliminar(){
        try{
            Excepcion td = excepcion;
            td.setEstado(0);
            excepcionFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setExcepciones(excepcionFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoExcepcion(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        tiposExcepciones = new ArrayList<SelectItem>();
        cargarTiposExcepcionesSeleccion();
    }
    
    private void cargarTiposExcepcionesSeleccion() {
        tiposExcepciones = new ArrayList<SelectItem>();                      
        try {
            List<TipoExcepcion> listaSource = tipoExcepcionFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (TipoExcepcion te : listaSource) {
                        tiposExcepciones.add(new SelectItem(te, te.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los tipos de excepciones.", e);
        }
    }
    
    //getters && setter
    public List<Excepcion> getExcepciones() {
        return excepciones;
    }

    public void setExcepciones(List<Excepcion> excepciones) {
        this.excepciones = excepciones;
    }
    
    public Excepcion getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(Excepcion excepcion) {
        this.excepcion = excepcion;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getTiposExcepciones() {
        return tiposExcepciones;
    }

    public void setTiposExcepciones(List<SelectItem> tiposExcepciones) {
        this.tiposExcepciones = tiposExcepciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
