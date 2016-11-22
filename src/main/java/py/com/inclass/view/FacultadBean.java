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
import py.com.inclass.entities.Facultad;
import py.com.inclass.facade.FacultadFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class FacultadBean extends BaseBean {
    
    //facades
    @EJB
    private FacultadFacade facultadFacade;
    
    //variables de clase
    private List<Facultad> facultades;
    private Facultad facultad;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                facultad.setEstado(1);
                facultadFacade.create(facultad);
            }else{
                //edit
                facultadFacade.edit(facultad);
            }
            setInfoMessage(getMensajeGuardar());
            setFacultades(facultadFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
         try{
            facultades = facultadFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        facultad = new Facultad();
        modificar = false;
        abrirNuevoFacultad("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Facultad td = facultad;
            td.setEstado(0);
            facultadFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setFacultades(facultadFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoFacultad(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<Facultad> getFacultades() {
        return facultades;
    }

    public void setFacultades(List<Facultad> facultades) {
        this.facultades = facultades;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
}
