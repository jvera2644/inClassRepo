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
import py.com.inclass.entities.Menu;
import py.com.inclass.entities.Permiso;
import py.com.inclass.facade.MenuFacade;
import py.com.inclass.facade.PermisoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class PermisoBean extends BaseBean {
    
    //facades
    @EJB
    private PermisoFacade permisoFacade;
    
    @EJB
    private MenuFacade menuFacade;
    
    //variables de clase
    private List<Permiso> permisos;
    private Permiso permiso;
    private boolean modificar;
    private List<SelectItem> menus;
    
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                permiso.setEstado(1);
                permisoFacade.create(permiso);
            }else{
                //edit
                permisoFacade.edit(permiso);
            }
            setInfoMessage(getMensajeGuardar());
            setPermisos(permisoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        permisos = permisoFacade.getAllActivos();
    }
    
    public void nuevo(){
        permiso = new Permiso();
        modificar = false;
        abrirNuevoPermiso("PF('dialogo').show();");
    }
    
    public String editar(){
        return null;
    }
    
    public void eliminar(){
        try{
            Permiso p = permiso;
            p.setEstado(0);
            permisoFacade.edit(p);
            setInfoMessage(getMensajeGuardar());
            setPermisos(permisoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoPermiso(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        menus = new ArrayList<SelectItem>();
        cargarMenus();
    }
    
    private void cargarMenus() {
        menus = new ArrayList<SelectItem>();                      
        try {
            List<Menu> listaSource = menuFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Menu m : listaSource) {
                        menus.add(new SelectItem(m, m.getNombre()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los menús del sistema", e);
        }
    }
        
    //getters && setter
    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
    

    public boolean isModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getMenus() {
        return menus;
    }

    public void setMenus(List<SelectItem> menus) {
        this.menus = menus;
    }
    
    
    
}
