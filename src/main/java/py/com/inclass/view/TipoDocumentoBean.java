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
import py.com.inclass.entities.TipoDocumento;
import py.com.inclass.facade.TipoDocumentoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class TipoDocumentoBean extends BaseBean {
    
    //facades
    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;
    
    //variables de clase
    private List<TipoDocumento> tiposDocumentos;
    private TipoDocumento tipoDocumento;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                tipoDocumento.setEstado(1);
                tipoDocumentoFacade.create(tipoDocumento);
            }else{
                //edit
                tipoDocumentoFacade.edit(tipoDocumento);
            }
            setInfoMessage(getMensajeGuardar());
            setTiposDocumentos(tipoDocumentoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        tiposDocumentos = tipoDocumentoFacade.getAllActivos();
    }
    
    public void nuevo(){
        tipoDocumento = new TipoDocumento();
        modificar = false;
        abrirNuevoTipoDocumento("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            TipoDocumento td = tipoDocumento;
            td.setEstado(0);
            tipoDocumentoFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setTiposDocumentos(tipoDocumentoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoTipoDocumento(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<TipoDocumento> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<TipoDocumento> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public boolean isModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
    
    
}
