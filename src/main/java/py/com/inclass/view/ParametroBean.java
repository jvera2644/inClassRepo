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
import py.com.inclass.entities.Parametro;
import py.com.inclass.facade.ParametroFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class ParametroBean extends BaseBean {
    
    //facades
    @EJB
    private ParametroFacade parametroFacade;
    
    //variables de clase
    private List<Parametro> parametros;
    private Parametro parametro;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                parametroFacade.create(parametro);
            }else{
                //edit
                parametroFacade.edit(parametro);
            }
            setInfoMessage(getMensajeGuardar());
            setParametros(parametroFacade.getAll());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            parametros = parametroFacade.getAll();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        parametro = new Parametro();
        modificar = false;
        abrirNuevoParametro("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Parametro p = parametro;
            parametroFacade.edit(p);
            setInfoMessage(getMensajeGuardar());
            setParametros(parametroFacade.getAll());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoParametro(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
    
    
}
