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
import py.com.inclass.entities.Nacionalidad;
import py.com.inclass.facade.NacionalidadFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class NacionalidadBean extends BaseBean {
    
    //facades
    @EJB
    private NacionalidadFacade nacionalidadFacade;
    
    //variables de clase
    private List<Nacionalidad> nacionalidades;
    private Nacionalidad nacionalidad;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                nacionalidad.setEstado(1);
                nacionalidadFacade.create(nacionalidad);
            }else{
                //edit
                nacionalidadFacade.edit(nacionalidad);
            }
            setInfoMessage(getMensajeGuardar());
            setNacionalidades(nacionalidadFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
         try{
            nacionalidades = nacionalidadFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        nacionalidad = new Nacionalidad();
        modificar = false;
        abrirNuevoNacionalidad("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Nacionalidad td = nacionalidad;
            td.setEstado(0);
            nacionalidadFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setNacionalidades(nacionalidadFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoNacionalidad(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<Nacionalidad> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(List<Nacionalidad> nacionalidades) {
        this.nacionalidades = nacionalidades;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
    
    
}
