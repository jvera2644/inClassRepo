package py.com.inclass.util;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author DiegoY
 */
public class BaseBean implements Serializable{
    protected static final Logger logger = Logger.getLogger("inclass");
    public String nombreLargoProyecto = "Sistema Biométrico de Asistencias";
    private static String mensajeGuardar = "Los cambios han sido guardados con éxito";
    private static String mensajeError = "Hubo un error al guardar una transacción";
    private static String mensajeUsuarioDuplicado = "Usuario ya existe";
        
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    protected void setInfoMessage(UIComponent component, String summary, String detail) {
        getFacesContext().addMessage(((component != null) ? component.getClientId(getFacesContext()) : null), new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    protected void setInfoMessage(String summary, String detail) {
        setInfoMessage(null, summary, detail);
    }

    protected void setInfoMessage(String summary) {
        setInfoMessage(null, summary, null);
    }

    protected void setWarnMessage(UIComponent component, String summary, String detail) {
        getFacesContext().addMessage(((component != null) ? component.getClientId(getFacesContext()) : null), new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    protected void setWarnMessage(String summary, String detail) {
        setWarnMessage(null, summary, detail);
    }

    protected void setWarnMessage(String summary) {
        setWarnMessage(null, summary, null);
    }

    protected void setErrorMessage(UIComponent component, String summary, String detail) {
        getFacesContext().addMessage(((component != null) ? component.getClientId(getFacesContext()) : null), new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    protected void setErrorMessage(String summary, String detail) {
        setErrorMessage(null, summary, detail);
    }

    protected void setErrorMessage(String summary) {
        setErrorMessage(null, summary, null);
    }

    protected void setFatalMessage(UIComponent component, String summary, String detail) {
        getFacesContext().addMessage(((component != null) ? component.getClientId(getFacesContext()) : null), new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, null));
    }

    protected void setFatalMessage(String summary, String detail) {
        setFatalMessage(null, summary, detail);
    }

    protected void setFatalMessage(String summary) {
        setFatalMessage(null, summary, null);
    }

    public static String getMensajeGuardar() {
        return mensajeGuardar;
    }

    public static String getMensajeError() {
        return mensajeError;
    }

    public static String getMensajeUsuarioDuplicado() {
        return mensajeUsuarioDuplicado;
    }
    
    
}
