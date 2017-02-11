/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package py.com.inclass.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import py.com.inclass.entities.Persona;
import py.com.inclass.entities.Rol;
import py.com.inclass.entities.Usuario;
import py.com.inclass.facade.PersonaFacade;
import py.com.inclass.facade.RolFacade;
import py.com.inclass.facade.UsuarioFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */  
@ViewScoped
@ManagedBean
public class UsuarioBean extends BaseBean {

    //variables de clase
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;
    private Usuario usuario;
    private DualListModel<Rol> roles;
    private boolean modificar;
    private String nroDocumentoPersona;
    private String nombreApellidoPersona;
    private boolean habilitaBotonGuardar;
    private Persona persona;

    //facades
    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private RolFacade rolFacade;

    @EJB
    private PersonaFacade personaFacade;

    @PostConstruct
    public void init() {
        usuarioMenu();
    }

    public void usuarioMenu() {
        usuario = new Usuario();
        roles = new DualListModel<Rol>();
        usuarios = usuarioFacade.getAll();
        habilitaBotonGuardar = false;
    }

    public void nuevo() {
        usuario = new Usuario();
        roles = new DualListModel<Rol>(rolFacade.getAllActivos(), new ArrayList<Rol>(0));
        modificar = false;
        nroDocumentoPersona = null;
        nombreApellidoPersona = null;
        abrirNuevoUsuario("PF('dialogo').show();");
    }

    private void abrirNuevoUsuario(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }

    public void guardar() {
        try {
            usuario.setRolCollection(roles.getTarget());
            usuario.setUltimoAcceso(new Date());
            usuario.setIdPersona(persona);
            if (!modificar) {
                usuario.setIntentoFallido(0);
                usuario.setEstado(1);
                usuario.setHabilitado("S");
                usuarioFacade.create(usuario);
            } else {
                if (usuario.getHabilitado().equals("S")){
                    usuario.setIntentoFallido(0);
                }
                usuarioFacade.edit(usuario);
            }
            setInfoMessage(getMensajeGuardar());
            setUsuarios(usuarioFacade.getAll());
        } catch (Exception e) {
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }

    public void editar() {
        List<Rol> listaSource = rolFacade.getAllActivos();
        List<Rol> listaTarget = (List<Rol>) usuario.getRolCollection();
        for (Rol rol : listaTarget) {
            listaSource.remove(rol);
        }
        roles = new DualListModel<Rol>(listaSource, listaTarget);
        nroDocumentoPersona = usuario.getIdPersona().getNumeroDocumento();
        nombreApellidoPersona = usuario.getIdPersona().getNombre() + ", " + usuario.getIdPersona().getApellido();
        persona = usuario.getIdPersona();
    }

    public void eliminar() {
        try {
            Usuario unUsuario = usuario;
            unUsuario.setEstado(Integer.parseInt("0"));
            usuarioFacade.edit(unUsuario);
            usuarios = usuarioFacade.getAll();
            setInfoMessage(getMensajeGuardar());
        } catch (Exception e) {
            setWarnMessage("Es refenciada por otra entidad");
            logger.error("No se puede eliminar", e);
        }
    }

    public List<Persona> autoCompletePersona(String nroDocumento) {
        List<Persona> personas = personaFacade.findByNroDocumentoOnComplete(nroDocumento);
        return personas;
    }
    
    public void nroDocumentoOnChanged() {
        habilitaBotonGuardar = false;
        try{
            //verificamos si el nro. de documento ingresado existe
            persona = personaFacade.findByNroDocumento(nroDocumentoPersona);
            if(persona != null){
                setNombreApellidoPersona(persona.getNombre() + ", " + persona.getApellido());
            }else{
                setNombreApellidoPersona("");
                setWarnMessage(nroDocumentoPersona + " no está registrado.");
                habilitaBotonGuardar = true;
            }
                
        }catch(Exception e){
            logger.error("Error al buscar persona por nro. de documento", e);
        }
    }
    
    
    public void nombreUsuarioOnChanged() {
        habilitaBotonGuardar = false;
        try{
            String user = usuario.getUsuario();
            if (usuarioFacade.getUsuarioPorNombreUsuario(user) != null) {
                setWarnMessage(user + " ya se encuentra registrado.");
                habilitaBotonGuardar = true;
            }                
        }catch(Exception e){
            logger.error("Error al buscar persona por nombre de usuario.", e);
        }
    }
    

    //getters && setters
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DualListModel<Rol> getRoles() {
        return roles;
    }

    public void setRoles(DualListModel<Rol> roles) {
        this.roles = roles;
    }

    public boolean getModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public String getNroDocumentoPersona() {
        return nroDocumentoPersona;
    }

    public void setNroDocumentoPersona(String nroDocumentoPersona) {
        this.nroDocumentoPersona = nroDocumentoPersona;
    }

    public String getNombreApellidoPersona() {
        return nombreApellidoPersona;
    }

    public void setNombreApellidoPersona(String nombreApellidoPersona) {
        this.nombreApellidoPersona = nombreApellidoPersona;
    }

    public boolean getHabilitaBotonGuardar() {
        return habilitaBotonGuardar;
    }

    public void setHabilitaBotonGuardar(boolean habilitaBotonGuardar) {
        this.habilitaBotonGuardar = habilitaBotonGuardar;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

}
