package py.com.inclass.util;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import py.com.inclass.entities.Parametro;
import py.com.inclass.facade.ParametroFacade;

/**
 *
 * @author DiegoY
 */
@SessionScoped
public class BaseBean implements Serializable{
    
    @EJB
    ParametroFacade parametroFacade;
    
    protected static final Logger logger = Logger.getLogger("inclass");
    public String nombreLargoProyecto = "Sistema Biométrico de Asistencias";
    private static String mensajeGuardar = "Los cambios han sido guardados con éxito";
    private static String mensajeError = "Hubo un error al guardar una transacción";
    private static String mensajeUsuarioDuplicado = "Usuario ya existe";
    private static String mensajeErrorConsulta = "Hubo un error al consultar una tabla";
    
    private final String tiposArchivosPermitidos = "/(\\.|\\/)(pdf|jpe?g|png|gif|doc|docx|odt|xls|xlsx|ods)$/";
    private final String msgLimiteCantidad = "Se ha superado la cantidad de archivos permitidos.";
    private final String msgFormatoNoPermitido = "El formato del archivo no está permitido.";
    private final String labelCancelar = "Cancelar";
    private final String labelAdjuntar = "Adjuntar";
    private String msgTamanioNoPermitido;
    private long tamanioMaxArchivo = 0;
    private String msgTamanioArchivo;
    
    private long cantBytesEnUnMega = 1048576;
    
    @PostConstruct
    private void establecerTamanioMaxArchivo( ) {
        Parametro p = parametroFacade.getParametroByCodigo(ParametroEnum.PAR_TAM_ARC_JUS.getCodigo());
        long tamEnKbytes = Long.parseLong( p.getValor() );
        tamanioMaxArchivo =  tamEnKbytes * 1024;
        logger.warn("Se asigno tamanio maximo archivo " + tamanioMaxArchivo);

        msgTamanioArchivo = "Tamaño máximo para archivos adjuntos: "
                        + tamanioMaxArchivo / cantBytesEnUnMega +"MB";

        msgTamanioNoPermitido = "El tamaño del archivo supera lo permitido ("+ tamanioMaxArchivo / cantBytesEnUnMega +"MB).";
    }
            
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

    public static String getMensajeErrorConsulta() {
        return mensajeErrorConsulta;
    }

    public String getNombreLargoProyecto() {
        return nombreLargoProyecto;
    }

    public String getTiposArchivosPermitidos() {
        return tiposArchivosPermitidos;
    }

    public String getMsgLimiteCantidad() {
        return msgLimiteCantidad;
    }

    public String getMsgFormatoNoPermitido() {
        return msgFormatoNoPermitido;
    }

    public String getLabelCancelar() {
        return labelCancelar;
    }

    public String getLabelAdjuntar() {
        return labelAdjuntar;
    }

    public long getTamanioMaxArchivo() {
        return tamanioMaxArchivo;
    }

    public void setTamanioMaxArchivo(long tamanioMaxArchivo) {
        this.tamanioMaxArchivo = tamanioMaxArchivo;
    }

    public String getMsgTamanioArchivo() {
        return msgTamanioArchivo;
    }

    public void setMsgTamanioArchivo(String msgTamanioArchivo) {
        this.msgTamanioArchivo = msgTamanioArchivo;
    }

    public String getMsgTamanioNoPermitido() {
        return msgTamanioNoPermitido;
    }

    public void setMsgTamanioNoPermitido(String msgTamanioNoPermitido) {
        this.msgTamanioNoPermitido = msgTamanioNoPermitido;
    }
    
}
