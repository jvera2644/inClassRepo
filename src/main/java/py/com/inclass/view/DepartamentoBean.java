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
import py.com.inclass.entities.Departamento;
import py.com.inclass.entities.Pais;
import py.com.inclass.facade.DepartamentoFacade;
import py.com.inclass.facade.PaisFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class DepartamentoBean extends BaseBean {
    
    //facades
    @EJB
    private DepartamentoFacade departamentoFacade;
    
     @EJB
    private PaisFacade paisFacade;
    
    //variables de clase
    private List<Departamento> departamentos;
    private Departamento departamento;
    private boolean modificar;
    private List<SelectItem> paises;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                departamento.setEstado(1);
                departamentoFacade.create(departamento);
            }else{
                //edit
                departamentoFacade.edit(departamento);
            }
            setInfoMessage(getMensajeGuardar());
            setDepartamentos(departamentoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            departamentos = departamentoFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        departamento = new Departamento();
        modificar = false;
        abrirNuevoDepartamento("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Departamento td = departamento;
            td.setEstado(0);
            departamentoFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setDepartamentos(departamentoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoDepartamento(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        paises = new ArrayList<SelectItem>();
        cargarPaisesSeleccion();
    }
    
    private void cargarPaisesSeleccion() {
        paises = new ArrayList<SelectItem>();                      
        try {
            List<Pais> listaSource = paisFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Pais p : listaSource) {
                        paises.add(new SelectItem(p, p.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los países del sistema", e);
        }
    }
        
    //getters && setter
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
    
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getPaises() {
        return paises;
    }

    public void setPaises(List<SelectItem> paises) {
        this.paises = paises;
    }
    
    
    
}
