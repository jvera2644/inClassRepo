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
import py.com.inclass.entities.TipoExcepcion;
import py.com.inclass.facade.TipoExcepcionFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class TipoExcepcionBean extends BaseBean {
    
    //facades
    @EJB
    private TipoExcepcionFacade tipoExcepcionFacade;
    
    //variables de clase
    private List<TipoExcepcion> tiposExcepciones;
    private TipoExcepcion tipoExcepcion;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                tipoExcepcion.setEstado(1);
                tipoExcepcionFacade.create(tipoExcepcion);
            }else{
                //edit
                tipoExcepcionFacade.edit(tipoExcepcion);
            }
            setInfoMessage(getMensajeGuardar());
            setTiposExcepciones(tipoExcepcionFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        tiposExcepciones = tipoExcepcionFacade.getAllActivos();
    }
    
    public void nuevo(){
        tipoExcepcion = new TipoExcepcion();
        modificar = false;
        abrirNuevoTipoExcepcion("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            TipoExcepcion td = tipoExcepcion;
            td.setEstado(0);
            tipoExcepcionFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setTiposExcepciones(tipoExcepcionFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoTipoExcepcion(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<TipoExcepcion> getTiposExcepciones() {
        return tiposExcepciones;
    }

    public void setTiposExcepciones(List<TipoExcepcion> tiposExcepciones) {
        this.tiposExcepciones = tiposExcepciones;
    }

    public TipoExcepcion getTipoExcepcion() {
        return tipoExcepcion;
    }

    public void setTipoExcepcion(TipoExcepcion tipoExcepcion) {
        this.tipoExcepcion = tipoExcepcion;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
}
