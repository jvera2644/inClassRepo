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
import py.com.inclass.entities.Pais;
import py.com.inclass.facade.PaisFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class PaisBean extends BaseBean {
    
    //facades
    @EJB
    private PaisFacade paisFacade;
    
    //variables de clase
    private List<Pais> tiposDocumentos;
    private Pais pais;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                pais.setEstado(1);
                paisFacade.create(pais);
            }else{
                //edit
                paisFacade.edit(pais);
            }
            setInfoMessage(getMensajeGuardar());
            setPaises(paisFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        tiposDocumentos = paisFacade.getAllActivos();
    }
    
    public void nuevo(){
        pais = new Pais();
        modificar = false;
        abrirNuevoPais("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Pais td = pais;
            td.setEstado(0);
            paisFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setPaises(paisFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoPais(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<Pais> getPaises() {
        return tiposDocumentos;
    }

    public void setPaises(List<Pais> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
    
    
}
