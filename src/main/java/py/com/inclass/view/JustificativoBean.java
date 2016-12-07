/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import py.com.inclass.entities.Justificativo;
import py.com.inclass.entities.MotivoJustificativo;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.JustificativoFacade;
import py.com.inclass.facade.MotivoJustificativoFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class JustificativoBean extends BaseBean {
    
    @ManagedProperty(value = "#{SecurityBean.usuario}")
    private Usuario usuario;
    
    //facades
    @EJB
    private JustificativoFacade justificativoFacade;
    
     @EJB
    private MotivoJustificativoFacade motivoJustificativoFacade;
    
    //variables de clase
    private List<Justificativo> justificativos;
    private Justificativo justificativo;
    private boolean modificar;
    private List<SelectItem> motivosJustificativos;
    private byte[] archivoJustificativo;
    private String fileName;
    private String tipoContenido;
    private StreamedContent archivoJustificativoDescarga;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
        inicializarListasSeleccion();
    }
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                justificativo.setEstado(1);
                justificativo.setIdPersona(usuario.getIdPersona());
                Date now = new Date(System.currentTimeMillis());
                justificativo.setFechaHoraJustificacion(now);
                justificativoFacade.create(justificativo);
            }else{
                //edit
                justificativoFacade.edit(justificativo);
            }
            setInfoMessage(getMensajeGuardar());
            setJustificativos(justificativoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            justificativos = justificativoFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        justificativo = new Justificativo();
        modificar = false;
        fileName = null;
        tipoContenido = null;
        archivoJustificativo = null;
        abrirNuevoJustificativo("PF('dialogo').show();");
    }
    
    public String editar(){ 
        archivoJustificativo = justificativo.getDocumento();
        fileName = justificativo.getNombreDocumento();
        tipoContenido = justificativo.getExtensionDocumento();
        return null;
    }
    
    public void eliminar(){
        try{
            Justificativo td = justificativo;
            td.setEstado(0);
            justificativoFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setJustificativos(justificativoFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoJustificativo(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    private void inicializarListasSeleccion(){
        motivosJustificativos = new ArrayList<SelectItem>();
        cargarMotivosJustificacionSeleccion();
    }
    
    private void cargarMotivosJustificacionSeleccion() {
        motivosJustificativos = new ArrayList<SelectItem>();                      
        try {
            List<MotivoJustificativo> listaSource = motivoJustificativoFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (MotivoJustificativo mj : listaSource) {
                        motivosJustificativos.add(new SelectItem(mj, mj.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar los motivos de justificación", e);
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try{
            
            UploadedFile uploadedFile = event.getFile();
            fileName = uploadedFile.getFileName();
            tipoContenido = uploadedFile.getContentType();
            archivoJustificativo = uploadedFile.getContents(); // Or getInputStream()
            
            setInfoMessage("El archivo "+fileName+" fue adjuntado.");
            //archivoJustificativo = event.getFile().getContents();
            justificativo.setDocumento(archivoJustificativo);
            justificativo.setNombreDocumento(fileName);
            justificativo.setExtensionDocumento(tipoContenido);
            
        }catch(Exception e){
            setErrorMessage("No se pudo adjuntar el archivo "+event.getFile().getFileName());
            logger.error("Error al adjuntar el archivo de justificación", e);
        }
    }
    
    public void handleFileDownload(){
        try{
            InputStream stream = new ByteArrayInputStream(archivoJustificativo);
            archivoJustificativoDescarga = new DefaultStreamedContent(stream, tipoContenido, fileName);
        }catch(Exception e){
            setErrorMessage("No se pudo descargar el archivo "+fileName);
            logger.error("Error al descargar el archivo de justificación", e);
        }
    }
    
    public void eliminarArchivo(){
        fileName = null;
        tipoContenido = null;
        archivoJustificativo = null;
            
        justificativo.setDocumento(null);
        justificativo.setNombreDocumento(null);
        justificativo.setExtensionDocumento(null);
    }
    
            
    //getters && setter
    public List<Justificativo> getJustificativos() {
        return justificativos;
    }

    public void setJustificativos(List<Justificativo> justificativos) {
        this.justificativos = justificativos;
    }
    
    public Justificativo getJustificativo() {
        return justificativo;
    }

    public void setJustificativo(Justificativo justificativo) {
        this.justificativo = justificativo;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public List<SelectItem> getMotivosJustificativos() {
        return motivosJustificativos;
    }

    public void setMotivosJustificativos(List<SelectItem> motivosJustificativos) {
        this.motivosJustificativos = motivosJustificativos;
    }

    public byte[] getArchivoJustificativo() {
        return archivoJustificativo;
    }

    public void setArchivoJustificativo(byte[] archivoJustificativo) {
        this.archivoJustificativo = archivoJustificativo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public StreamedContent getArchivoJustificativoDescarga() {
        return archivoJustificativoDescarga;
    }

    public void setArchivoJustificativoDescarga(StreamedContent archivoJustificativoDescarga) {
        this.archivoJustificativoDescarga = archivoJustificativoDescarga;
    }
    
}
