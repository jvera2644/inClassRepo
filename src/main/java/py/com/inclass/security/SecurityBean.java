package py.com.inclass.security;

//import com.interbanco.security.ObfuscatorException;
//import com.interbanco.security.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.menuitem.UIMenuItem;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import py.com.inclass.entities.Menu;
import py.com.inclass.entities.Permiso;
import py.com.inclass.entities.Rol;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.MenuFacade;
import py.com.inclass.facade.UsuarioFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author DiegoY
 */
@ManagedBean(name = "SecurityBean")
@SessionScoped
public class SecurityBean extends BaseBean {

    private String userName;
    private String password;
    private String nuevoPassword;
    private String sesionAnterior;
    private boolean loginOk;
    
    @EJB
    private MenuFacade menuFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
//    
//    @EJB
//    private HorarioAtencionFacade horarioFacade;
//    
//    @EJB
//    private PermisoFacade permisoFacade;
//    
//    @ManagedProperty(value = "#{usuarioBean}")
//    private UsuarioBean usuarioBean;
//    
    private Usuario usuario;
    private boolean tienePermiso;
//    private List<HorarioAtencion> horarios;
    private MenuModel menus;

    @PostConstruct
    public void init() {
        userName = "";
        password = "";
    }

    public void cambiarPassword() {
        try {
//            Util.setSharedSecret(Util.getDEFAULT_KEY());
//            nuevoPassword = Util.encrypt(nuevoPassword);
//            usuario.setPassword(nuevoPassword);
//            usuarioFacade.edit(usuario);
            setInfoMessage("Contraseña modificada");
        } catch (Exception e) {
            logger.error("Error en cambiarPassword", e);
        }
    }

    public void nuevo() {
        password = "";
        nuevoPassword = "";
    }

    public void passwordValidator(FacesContext fc, UIComponent component, Object value) {
        try {
//            String passw = value.toString();
//            Util.setSharedSecret(Util.getDEFAULT_KEY());
//            passw = Util.encrypt(passw);
//            if (!usuario.getPassword().equals(passw)) {
//                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "incorrecto", "incorrecto");
//                fc.addMessage(":passwordForm:actual", msg);
//                throw new ValidatorException(msg);
//            }
        } catch (Exception e) {
            logger.error("Error en passwordValidator", e);
        }
    }
    
    private String formatearUrl(String url) {
        int posicion = url.lastIndexOf(".");
        String nuevaUrl = url.substring(0, posicion);
        nuevaUrl += ".jsf";
        
        return nuevaUrl;
    }

    private void cargarMenu() {
        menus = new DefaultMenuModel();
        
        Set<Permiso> permisos = new HashSet<Permiso>();
        for (Rol rol : usuario.getRolCollection()) {
            permisos.addAll(rol.getPermisoCollection());
        }
        
        List<Menu> menuList = menuFacade.findAll();
        List<DefaultSubMenu> subMenuList = null;
        
        if (menuList != null && !menuList.isEmpty()) {
            subMenuList = new ArrayList<DefaultSubMenu>(menuList.size());
            for (Menu menu : menuList) {
                subMenuList.add(new DefaultSubMenu(menu.getNombre()));
            }
        }
        
        for (Permiso permiso : permisos) {
            DefaultMenuItem item = new DefaultMenuItem();
            item.setValue(permiso.getDescripcion().toUpperCase());
            item.setUrl(formatearUrl(permiso.getUrlPrograma()));
            item.setIcon("ui-icon-star");
            for (DefaultSubMenu subm : subMenuList) {
                if (subm.getLabel().equals(permiso.getIdMenu().getNombre())) {
                    subm.addElement(item);
                }
            }
        }
        for (DefaultSubMenu subm : subMenuList) {
            if (subm.getElements().size() > 0) {
                menus.addElement(subm);
            }
        }
    }

    public String login() {
        try {
            Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            if (map.containsValue(userName)) {
                setWarnMessage("Existe una sesión con el mismo usuario");
                return null;
            }
//            Util.setSharedSecret(Util.getDEFAULT_KEY());
//            password = Util.encrypt(password);
            usuario = usuarioFacade.getUsuarioPorUsuarioContrasena(userName, password);
            loginOk = usuario != null;
            if (loginOk) {
                map.put("username", userName);
                cargarMenu();
                return "/template/menu?faces-redirect=true";
            } else {
                setWarnMessage("Credenciales incorrectas");
                return null;
            }
        } catch (Exception e) {
            logger.error("Error en login", e);
            setErrorMessage("Ocurrió un error interno");
            return null;
        }
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("username", "");
        String url = context.getExternalContext().getRequestContextPath() + "/index.jsp";
        
        try {
            context.getExternalContext().invalidateSession();
            context.getExternalContext().redirect(url);
        } catch (IOException ex) {
            logger.error("logout", ex);
        }
    }

    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of username
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the loginOk
     */
    public boolean isLoginOk() {
        return loginOk;
    }

    /**
     * @param loginOk the loginOk to set
     */
    public void setLoginOk(boolean loginOk) {
        this.loginOk = loginOk;
    }

    /**
     * @return the sesionAnterior
     */
    public String getSesionAnterior() {
        return sesionAnterior;
    }

    /**
     * @param sesionAnterior the sesionAnterior to set
     */
    public void setSesionAnterior(String sesionAnterior) {
        this.sesionAnterior = sesionAnterior;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the usuarioFacade
     */
//    public UsuarioFacade getUsuarioFacade() {
//        return usuarioFacade;
//    }
//
//    /**
//     * @param usuarioBean the usuarioBean to set
//     */
//    public void setUsuarioBean(UsuarioBean usuarioBean) {
//        this.usuarioBean = usuarioBean;
//    }
//
    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @return the tienePermiso
     */
    public boolean getTienePermiso() {
        return tienePermiso;
    }

    /**
     * @param tienePermiso the tienePermiso to set
     */
    public void setTienePermiso(boolean tienePermiso) {
        this.tienePermiso = tienePermiso;
    }

    /**
     * @return the nuevoPassword
     */
    public String getNuevoPassword() {
        return nuevoPassword;
    }

    /**
     * @param nuevoPassword the nuevoPassword to set
     */
    public void setNuevoPassword(String nuevoPassword) {
        this.nuevoPassword = nuevoPassword;
    }

    /**
     * @return the horarios
     */
//    public List<HorarioAtencion> getHorarios() {
//        return horarios;
//    }
//
//    /**
//     * @param horarios the horarios to set
//     */
//    public void setHorarios(List<HorarioAtencion> horarios) {
//        this.horarios = horarios;
//    }
//
    /**
     * @return the menus
     */
    public MenuModel getMenus() {
        return menus;
    }
}
