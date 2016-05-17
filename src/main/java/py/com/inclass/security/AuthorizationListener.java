package py.com.inclass.security;

import java.io.IOException;
import java.util.Collection;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import py.com.inclass.entities.Permiso;
import py.com.inclass.entities.Rol;
import py.com.inclass.entities.Usuario;


/**
 *
 * @author DiegoY
 */
public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        SecurityBean securityBean = (SecurityBean) event.getFacesContext()
                .getExternalContext().getSessionMap().get("SecurityBean");
        
        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = currentPage.lastIndexOf("login.xhtml") != -1;
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Object currentUser = session.getAttribute("username");

        if (securityBean != null && !isLoginPage) {
            Usuario usuario = securityBean.getUsuario();
            if (usuario != null) {
                Collection<Rol> roles = usuario.getRolCollection();
                boolean entro = false;
                for (Rol rol : roles) {
                    for (Permiso permiso : rol.getPermisoCollection()) {
                        if (permiso.getUrlPrograma().equals(currentPage)) {
                            securityBean.setTienePermiso(true);
                            entro = true;
                            break;
                        }
                    }
                    if (entro) {
                        break;
                    }
                }
                if (!entro) {
                    securityBean.setTienePermiso(false);
                }
            }
        }
        if (!isLoginPage && (currentUser == null || currentUser == "")) {
            try {
                facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/index.jsp");
            } catch (IOException ex) {
                Logger.getLogger("inclass").error("Error en AuthorizationListener", ex);
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
