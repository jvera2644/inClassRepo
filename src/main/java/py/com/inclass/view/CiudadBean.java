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
import py.com.inclass.entities.Ciudad;
import py.com.inclass.entities.Departamento;
import py.com.inclass.facade.CiudadFacade;
import py.com.inclass.facade.DepartamentoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class CiudadBean extends BaseBean {
    
    //facades
    @EJB
    private CiudadFacade ciudadFacade;
    
     @EJB
    private DepartamentoFacade departamentoFacade;
    
    //variables de clase
    private List<Ciudad> ciudades;
    private Ciudad ciudad;
    private boolean modificar;
    private List<SelectItem> departamentos;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                ciudad.setEstado(1);
                ciudadFacade.create(ciudad);
            }else{
                //edit
                ciudadFacade.edit(ciudad);
            }
            setInfoMessage(getMensajeGuardar());
            setCiudades(ciudadFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            ciudades = ciudadFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        ciudad = new Ciudad();
        modificar = false;
        abrirNuevoCiudad("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Ciudad td = ciudad;
            td.setEstado(0);
            ciudadFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setCiudades(ciudadFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoCiudad(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        departamentos = new ArrayList<SelectItem>();
        cargarDepartamentosSeleccion();
    }
    
    private void cargarDepartamentosSeleccion() {
        departamentos = new ArrayList<SelectItem>();                      
        try {
            List<Departamento> listaSource = departamentoFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Departamento d : listaSource) {
                        departamentos.add(new SelectItem(d, d.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los países del sistema", e);
        }
    }
        
    //getters && setter
    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<SelectItem> departamentos) {
        this.departamentos = departamentos;
    }

    
}
