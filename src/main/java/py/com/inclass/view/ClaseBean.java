/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import py.com.inclass.entities.Clases;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.ClasesFacade;
import py.com.inclass.facade.RolFacade;
import py.com.inclass.util.BaseBean;


/**
 *
 * @author Edu
 */

@ManagedBean
@ViewScoped
public class ClaseBean extends BaseBean {
    
    @ManagedProperty(value = "#{SecurityBean.usuario}")
    private Usuario usuario;
    
    //facades
    @EJB
    private ClasesFacade clasesFacade;
    
    @EJB
    private RolFacade rolFacade;
    
    
    //variables de clase
    private List<Clases> clases;
    private Clases clase;
    private String nombreApellidoProfesor;
    
           
    @PostConstruct
    public void init() {
        inicializarMenu();
    }
    
    
    private void inicializarMenu(){
        try{
            //todas las clases del profesor logueado al sistema
            if(rolFacade.tieneRolAdministrador(usuario)){
                clases = clasesFacade.getAll();
            }else{
                clases = clasesFacade.getAllPorPersona(usuario.getIdPersona());
            }
            
            nombreApellidoProfesor = usuario.getIdPersona().getApellido() + " " + usuario.getIdPersona().getNombre();
        }catch(Exception e){
            setErrorMessage("Error al obtener las clases del Profesor");
            logger.error("Error al obtener las clases del Profesor "+usuario.getIdPersona().getApellido()+" "+usuario.getIdPersona().getNombre(), e);
        }  
    }
    
    
    public void finalizarClase(){
        try{
//            Clases c = clase;
//            c.setFechaHoraFin(new Date(System.currentTimeMillis()));
//            clasesFacade.edit(c);
//            setInfoMessage("La clase ha sido finalizada");
//            
//            if(rolFacade.tieneRolAdministrador(usuario)){
//                clases = clasesFacade.getAll();
//            }else{
//                clases = clasesFacade.getAllPorPersona(usuario.getIdPersona());
//            }

        abrirLista("PF('dialogo').show();");
           

        }catch(Exception e){
            setErrorMessage("Error al finalizar la clase");
            logger.error("Error al finalizar la clase: "+clase.getIdClases(), e);
        }
    }
    
    private void abrirLista(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }
    
        
    //getters && setters
    public List<Clases> getClases() {
        return clases;
    }

    public void setClases(List<Clases> clases) {
        this.clases = clases;
    }

    public Clases getClase() {
        return clase;
    }

    public void setClase(Clases clase) {
        this.clase = clase;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreApellidoProfesor() {
        return nombreApellidoProfesor;
    }

    public void setNombreApellidoProfesor(String nombreApellidoProfesor) {
        this.nombreApellidoProfesor = nombreApellidoProfesor;
    }
       
    
}
