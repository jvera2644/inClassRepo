/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import py.com.inclass.entities.Permiso;
import py.com.inclass.entities.Rol;
import py.com.inclass.facade.PermisoFacade;
import py.com.inclass.facade.RolFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */
@ViewScoped
@ManagedBean
public class RolBean extends BaseBean {

    //variables de clase
    private List<Rol> roles;
    private List<Rol> rolesFiltrados;
    private Rol rol;
    private DualListModel<Permiso> permisos;
    
        
    private boolean modificar;
    private boolean habilitaBotonGuardar;
    
    //facades
    @EJB
    private RolFacade rolFacade;

    @EJB
    private PermisoFacade permisoFacade;

    
    @PostConstruct
    public void init() {
        rolMenu();
    }

    public void rolMenu() {
        rol = new Rol();
        permisos = new DualListModel<Permiso>();
        roles = rolFacade.getAllActivos();
        habilitaBotonGuardar = false;
    }

    public void nuevo() {
        rol = new Rol();
        permisos = new DualListModel<Permiso>(permisoFacade.getAllActivos(), new ArrayList<Permiso>(0));
        modificar = false;
        abrirNuevoRol("PF('dialogo').show();");
    }

    private void abrirNuevoRol(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }

    public void guardar() {
        try {
            rol.setPermisoCollection(permisos.getTarget());
            
            if (!modificar) {
                rol.setEstado(1);
                rolFacade.create(rol);
            } else {
                rolFacade.edit(rol);
            }
            setInfoMessage(getMensajeGuardar());
            setRoles(rolFacade.getAllActivos());
        } catch (Exception e) {
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }

    public void editar() {
        
        List<Permiso> listaSource = permisoFacade.getAllActivos();
        List<Permiso> listaTarget = (List<Permiso>) rol.getPermisoCollection();
        for (Permiso rol : listaTarget) {
            listaSource.remove(rol);
        }
        permisos = new DualListModel<Permiso>(listaSource, listaTarget);
    }

    public void eliminar() {
        try {
            Rol unRol = rol;
            unRol.setEstado(Integer.parseInt("0"));
            rolFacade.edit(unRol);
            roles = rolFacade.getAllActivos();
            setInfoMessage(getMensajeGuardar());
        } catch (Exception e) {
            setWarnMessage("Es refenciada por otra entidad");
            logger.error("No se puede eliminar", e);
        }
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Rol> getRolesFiltrados() {
        return rolesFiltrados;
    }

    public void setRolesFiltrados(List<Rol> rolesFiltrados) {
        this.rolesFiltrados = rolesFiltrados;
    }

    public Rol getRol() {
        return rol;
    }

    //getters && setters
    public void setRol(Rol rol) {    
        this.rol = rol;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public boolean getHabilitaBotonGuardar() {
        return habilitaBotonGuardar;
    }

    public void setHabilitaBotonGuardar(boolean habilitaBotonGuardar) {
        this.habilitaBotonGuardar = habilitaBotonGuardar;
    }

    public DualListModel<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(DualListModel<Permiso> permisos) {
        this.permisos = permisos;
    }
    
}
