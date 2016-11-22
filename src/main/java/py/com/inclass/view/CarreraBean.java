/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import py.com.inclass.entities.Semestre;
import py.com.inclass.entities.Carrera;
import py.com.inclass.entities.Facultad;
import py.com.inclass.facade.SemestreFacade;
import py.com.inclass.facade.CarreraFacade;
import py.com.inclass.facade.FacultadFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */
@ViewScoped
@ManagedBean
public class CarreraBean extends BaseBean {

    //variables de clase
    private List<Carrera> carreras;
    private List<Carrera> carrerasFiltrados;
    private Carrera carrera;
    private DualListModel<Semestre> semestres;
    
    private boolean modificar;
    private boolean habilitaBotonGuardar;
    private List<SelectItem> facultades;
    

    //facades
    @EJB
    private CarreraFacade carreraFacade;

    @EJB
    private SemestreFacade semestreFacade;
    
    @EJB
    private FacultadFacade facultadFacade;
    
    
    @PostConstruct
    public void init() {
        carreraMenu();
        inicializarListasSeleccion();
    }

    public void carreraMenu() {
        carrera = new Carrera();
        semestres = new DualListModel<Semestre>();
        carreras = carreraFacade.getAll();
        habilitaBotonGuardar = false;
    }

    public void nuevo() {
        carrera = new Carrera();
        semestres = new DualListModel<Semestre>(semestreFacade.getAllActivos(), new ArrayList<Semestre>(0));
        modificar = false;
        abrirNuevoCarrera("PF('dialogo').show();");
    }

    private void abrirNuevoCarrera(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }

    public void guardar() {
        try {
            carrera.setSemestreCollection(semestres.getTarget());
            if (!modificar) {
                carrera.setEstado(1);
                carreraFacade.create(carrera);
            } else {
                carreraFacade.edit(carrera);
            }
            setInfoMessage(getMensajeGuardar());
            setCarreras(carreraFacade.getAll());
        } catch (Exception e) {
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }

    public void editar() {
        List<Semestre> listaSource = semestreFacade.getAllActivos();
        List<Semestre> listaTarget = (List<Semestre>) carrera.getSemestreCollection();
        for (Semestre rol : listaTarget) {
            listaSource.remove(rol);
        }
        semestres = new DualListModel<Semestre>(listaSource, listaTarget);
        
    }

    public void eliminar() {
        try {
            Carrera unCarrera = carrera;
            unCarrera.setEstado(Integer.parseInt("0"));
            carreraFacade.edit(unCarrera);
            carreras = carreraFacade.getAll();
            setInfoMessage(getMensajeGuardar());
        } catch (Exception e) {
            setWarnMessage("Es refenciada por otra entidad");
            logger.error("No se puede eliminar", e);
        }
    }
    
    private void inicializarListasSeleccion(){
        facultades = new ArrayList<SelectItem>();
        cargarFacultadesSeleccion();
    }
    
    private void cargarFacultadesSeleccion() {
        facultades = new ArrayList<SelectItem>();                      
        try {
            List<Facultad> listaSource = facultadFacade.getAllActivos();
            if (listaSource != null) {
                if (!listaSource.isEmpty()) {
                    for (Facultad f : listaSource) {
                        facultades.add(new SelectItem(f, f.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error al cargar las facultades.", e);
        }
    }
   
    
    //getters && setters
    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    public List<Carrera> getCarrerasFiltrados() {
        return carrerasFiltrados;
    }

    public void setCarrerasFiltrados(List<Carrera> carrerasFiltrados) {
        this.carrerasFiltrados = carrerasFiltrados;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public DualListModel<Semestre> getSemestres() {
        return semestres;
    }

    public void setSemestres(DualListModel<Semestre> semestres) {
        this.semestres = semestres;
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

    public List<SelectItem> getFacultades() {
        return facultades;
    }

    public void setFacultades(List<SelectItem> facultades) {
        this.facultades = facultades;
    }

}
