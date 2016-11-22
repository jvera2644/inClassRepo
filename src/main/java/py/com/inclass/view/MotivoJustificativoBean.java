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
import py.com.inclass.entities.MotivoJustificativo;
import py.com.inclass.facade.MotivoJustificativoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class MotivoJustificativoBean extends BaseBean {
    
    //facades
    @EJB
    private MotivoJustificativoFacade motivoJustificativoFacade;
    
    //variables de clase
    private List<MotivoJustificativo> motivosJustificativos;
    private MotivoJustificativo motivoJustificativo;
    private boolean modificar;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                motivoJustificativo.setEstado(1);
                motivoJustificativoFacade.create(motivoJustificativo);
            }else{
                //edit
                motivoJustificativoFacade.edit(motivoJustificativo);
            }
            setInfoMessage(getMensajeGuardar());
            setMotivosJustificativos(motivoJustificativoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        motivosJustificativos = motivoJustificativoFacade.getAllActivos();
    }
    
    public void nuevo(){
        motivoJustificativo = new MotivoJustificativo();
        modificar = false;
        abrirNuevoMotivoJustificativo("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            MotivoJustificativo td = motivoJustificativo;
            td.setEstado(0);
            motivoJustificativoFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setMotivosJustificativos(motivoJustificativoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoMotivoJustificativo(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
        
    //getters && setters
    public List<MotivoJustificativo> getMotivosJustificativos() {
        return motivosJustificativos;
    }

    public void setMotivosJustificativos(List<MotivoJustificativo> motivosJustificativos) {
        this.motivosJustificativos = motivosJustificativos;
    }

    public MotivoJustificativo getMotivoJustificativo() {
        return motivoJustificativo;
    }

    public void setMotivoJustificativo(MotivoJustificativo motivoJustificativo) {
        this.motivoJustificativo = motivoJustificativo;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }
    
    
    
}
