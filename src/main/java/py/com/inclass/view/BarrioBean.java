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
import py.com.inclass.entities.Ciudad;
import py.com.inclass.facade.BarrioFacade;
import py.com.inclass.facade.CiudadFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class BarrioBean extends BaseBean {
    
    //facades
    @EJB
    private BarrioFacade barrioFacade;
    
    @EJB
    private CiudadFacade ciudadFacade;
    
    //variables de clase
    private List<Barrio> barrios;
    private Barrio barrio;
    private boolean modificar;
    private List<SelectItem> ciudades;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                barrio.setEstado(1);
                barrioFacade.create(barrio);
            }else{
                //edit
                barrioFacade.edit(barrio);
            }
            setInfoMessage(getMensajeGuardar());
            setBarrios(barrioFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            barrios = barrioFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        barrio = new Barrio();
        modificar = false;
        abrirNuevoBarrio("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Barrio td = barrio;
            td.setEstado(0);
            barrioFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setBarrios(barrioFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoBarrio(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        ciudades = new ArrayList<SelectItem>();
        cargarCiudadesSeleccion();
    }
    
    private void cargarCiudadesSeleccion() {
        ciudades = new ArrayList<SelectItem>();                      
        try {
            List<Ciudad> listaSource = ciudadFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Ciudad c : listaSource) {
                        ciudades.add(new SelectItem(c, c.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los países del sistema", e);
        }
    }
        
    //getters && setter
    public List<Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(List<Barrio> barrios) {
        this.barrios = barrios;
    }
    
    public Barrio getBarrio() {
        return barrio;
    }

    public void setBarrio(Barrio barrio) {
        this.barrio = barrio;
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

    
}
