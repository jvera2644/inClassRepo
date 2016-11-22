package py.com.inclass.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import py.com.inclass.entities.Menu;
import py.com.inclass.entities.Parametro;
import py.com.inclass.entities.Permiso;
import py.com.inclass.entities.Persona;
import py.com.inclass.entities.Rol;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.MenuFacade;
import py.com.inclass.facade.ParametroFacade;
import py.com.inclass.facade.UsuarioFacade;
import py.com.inclass.util.BaseBean;
import py.com.inclass.util.ParametroEnum;

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
    private String nuevoPasswordConfirmacion;
    private String sesionAnterior;
    private boolean loginOk;
    private int estadoUsuario;
    
    @EJB
    private MenuFacade menuFacade;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @EJB
    private ParametroFacade parametroFacade;

    private Usuario usuario;
    private boolean tienePermiso;
    private MenuModel menus;

    @PostConstruct
    public void init() {
        userName = "";
        password = "";
        nuevoPassword = "";
        nuevoPasswordConfirmacion = "";
        tienePermiso = false;
    }

    public void cambiarPassword() {
        try {
            //Util.setSharedSecret(Util.getDEFAULT_KEY());
            //nuevoPassword = Util.encrypt(nuevoPassword);
            if (!usuario.getContrasena().equals(password)) {
                setWarnMessage("Contraseña actual incorrecta");
                password = "";
                nuevoPassword = "";
                nuevoPasswordConfirmacion = "";
            }else{
                usuario.setContrasena(nuevoPassword);
                usuarioFacade.edit(usuario);
                setInfoMessage("Contraseña modificada con éxito");
            }
        } catch (Exception e) {
            logger.error("Error al cambiar la contraseña del usuario: "+ usuario.getUsuario(), e);
        }
    }

    public void nuevo() {
        password = "";
        nuevoPassword = "";
        nuevoPasswordConfirmacion = "";
        tienePermiso = false;
        abrirCambioContrasenha("PF('dialogoPass').show();");
    }
    
    private void abrirCambioContrasenha(String path){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }

    public void validarContrasenhaActual(){
        tienePermiso = false;
        try{
            if (!usuario.getContrasena().equals(password)) {
                setWarnMessage("Contraseña actual incorrecta");
                tienePermiso = true;
            }
        }catch(Exception e){
            logger.error("Error al validar la contraseña actual "+password, e);
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
        //se obtienen los roles del usuario logeado
        for (Rol rol : usuario.getRolCollection()) {
            //por cada rol del usuario logeado, se obtienen los permisos.
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
                estadoUsuario = usuario.getEstado();
                if (estadoUsuario == 1) {    //activo
                    map.put("username", userName);
                    cargarMenu();
                    if (usuario.getIntentoFallido() != 0){
                        usuario.setIntentoFallido(0);
                        usuarioFacade.edit(usuario);
                    }
                    return "/template/menu?faces-redirect=true";
                } else {                      //inactivo
                    setWarnMessage("Usuario inactivo");
                    return null;
                }
                
            } else {
                Usuario usuarioSinContraseña = usuarioFacade.getUsuarioPorNombreUsuario(userName);
                if (usuarioSinContraseña != null){
                    Parametro p = parametroFacade.getParametroByCodigo(ParametroEnum.PAR_CAN_INT_FAL.getCodigo());
                    if (usuarioSinContraseña.getIntentoFallido() < Integer.parseInt(p.getValor())){
                        usuarioSinContraseña.setIntentoFallido(usuarioSinContraseña.getIntentoFallido()+1);
                        usuarioFacade.edit(usuarioSinContraseña);
                        setWarnMessage("Credenciales incorrectas");
                    }else{
                        usuarioSinContraseña.setHabilitado("N");
                        usuarioFacade.edit(usuarioSinContraseña);
                        setErrorMessage("Usuario bloqueado. Ha excedido el límite de conexiones!");
                    }
                }else{
                    setWarnMessage("Usuario no existe en la base de datos");
                }
                
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
     * @return the menus
     */
    public MenuModel getMenus() {
        return menus;
    }

    public String getNuevoPasswordConfirmacion() {
        return nuevoPasswordConfirmacion;
    }

    public void setNuevoPasswordConfirmacion(String nuevoPasswordConfirmacion) {
        this.nuevoPasswordConfirmacion = nuevoPasswordConfirmacion;
    }
 
}
