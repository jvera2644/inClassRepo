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
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import py.com.inclass.entities.Noticia;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.NoticiaFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class NoticiaBean extends BaseBean {
    
    @ManagedProperty(value = "#{SecurityBean.usuario}")
    private Usuario usuario;
    
    //facades
    @EJB
    private NoticiaFacade noticiaFacade;
    
        
    //variables de clase
    private List<Noticia> noticias;
    private Noticia noticia;
    private boolean modificar;
    private byte[] archivoNoticia;
    private String fileName;
    private String tipoContenido;
    private StreamedContent archivoNoticiaDescarga;
    
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    public void guardar(){
        try{
            if(!modificar){
                //create
                noticia.setEstado(1);
                noticia.setIdUsuario(usuario);
                noticiaFacade.create(noticia);
            }else{
                //edit
                noticiaFacade.edit(noticia);
            }
            setInfoMessage(getMensajeGuardar());
            setNoticias(noticiaFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void inicializarMenu(){
        try{
            noticias = noticiaFacade.getAllActivos();
        }catch(Exception e){
            setErrorMessage(getMensajeErrorConsulta());
            logger.error(getMensajeErrorConsulta(), e);
        }
    }
    
    public void nuevo(){
        noticia = new Noticia();
        modificar = false;
        fileName = null;
        tipoContenido = null;
        archivoNoticia = null;
        abrirNuevoNoticia("PF('dialogo').show();");
    }
    
    public String editar(){ 
        archivoNoticia = noticia.getArchivo();
        fileName = noticia.getNombreArchivo();
        tipoContenido = noticia.getExtensionArchivo();
        return null;
    }
    
    public void eliminar(){
        try{
            Noticia td = noticia;
            td.setEstado(0);
            noticiaFacade.edit(td);
            setInfoMessage(getMensajeGuardar());
            setNoticias(noticiaFacade.getAllActivos());    
        }catch(Exception e){
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }
    
    private void abrirNuevoNoticia(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try{
            
            UploadedFile uploadedFile = event.getFile();
            fileName = uploadedFile.getFileName();
            tipoContenido = uploadedFile.getContentType();
            archivoNoticia = uploadedFile.getContents(); // Or getInputStream()
            
            setInfoMessage("El archivo "+fileName+" fue adjuntado.");
            //archivoNoticia = event.getFile().getContents();
            noticia.setArchivo(archivoNoticia);
            noticia.setNombreArchivo(fileName);
            noticia.setExtensionArchivo(tipoContenido);
            
        }catch(Exception e){
            setErrorMessage("No se pudo adjuntar el archivo "+event.getFile().getFileName());
            logger.error("Error al adjuntar el archivo de justificación", e);
        }
    }
    
    public void handleFileDownload(){
        try{
            InputStream stream = new ByteArrayInputStream(archivoNoticia);
            archivoNoticiaDescarga = new DefaultStreamedContent(stream, tipoContenido, fileName);
        }catch(Exception e){
            setErrorMessage("No se pudo descargar el archivo "+fileName);
            logger.error("Error al descargar el archivo de justificación", e);
        }
    }
    
    public void eliminarArchivo(){
        fileName = null;
        tipoContenido = null;
        archivoNoticia = null;
            
        noticia.setArchivo(null);
        noticia.setNombreArchivo(null);
        noticia.setExtensionArchivo(null);
    }
        
    //getters && setter
    public List<Noticia> getNoticias() {
        return noticias;
    }

    public void setNoticias(List<Noticia> noticias) {
        this.noticias = noticias;
    }
    
    public Noticia getNoticia() {
        return noticia;
    }

    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public byte[] getArchivoNoticia() {
        return archivoNoticia;
    }

    public void setArchivoNoticia(byte[] archivoNoticia) {
        this.archivoNoticia = archivoNoticia;
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

    public StreamedContent getArchivoNoticiaDescarga() {
        return archivoNoticiaDescarga;
    }

    public void setArchivoNoticiaDescarga(StreamedContent archivoNoticiaDescarga) {
        this.archivoNoticiaDescarga = archivoNoticiaDescarga;
    }
    
}
