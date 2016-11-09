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
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import py.com.inclass.entities.Carrera;
import py.com.inclass.entities.Materia;
import py.com.inclass.facade.CarreraFacade;
import py.com.inclass.facade.MateriaFacade;
import py.com.inclass.util.BaseBean;

/**
 *
 * @author Edu
 */
@ViewScoped
@ManagedBean
public class MateriaBean extends BaseBean {

    //variables de clase
    private List<Materia> materias;
    private List<Materia> materiasFiltrados;
    private Materia materia;
    private DualListModel<Carrera> carreras;
    
    private boolean modificar;
    private boolean habilitaBotonGuardar;
        

    //facades
    @EJB
    private MateriaFacade materiaFacade;

    @EJB
    private CarreraFacade carreraFacade;
    
    @PostConstruct
    public void init() {
        materiaMenu();
    }

    public void materiaMenu() {
        materia = new Materia();
        carreras = new DualListModel<Carrera>();
        materias = materiaFacade.getAll();
        habilitaBotonGuardar = false;
    }

    public void nuevo() {
        materia = new Materia();
        carreras = new DualListModel<Carrera>(carreraFacade.getAllActivos(), new ArrayList<Carrera>(0));
        modificar = false;
        abrirNuevaMateria("PF('dialogo').show();");
    }

    private void abrirNuevaMateria(String path) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(path);
    }

    public void guardar() {
        try {
            materia.setCarreraCollection(carreras.getTarget());
            if (!modificar) {
                materia.setEstado(1);
                materiaFacade.create(materia);
            } else {
                materiaFacade.edit(materia);
            }
            setInfoMessage(getMensajeGuardar());
            setMaterias(materiaFacade.getAllActivos());
        } catch (Exception e) {
            setErrorMessage(getMensajeError());
            logger.error(getMensajeError(), e);
        }
    }

    public void editar() {
        List<Carrera> listaSource = carreraFacade.getAllActivos();
        List<Carrera> listaTarget = (List<Carrera>) materia.getCarreraCollection();
        for (Carrera rol : listaTarget) {
            listaSource.remove(rol);
        }
        carreras = new DualListModel<Carrera>(listaSource, listaTarget);
        
    }

    public void eliminar() {
        try {
            Materia unaMateria = materia;
            unaMateria.setEstado(Integer.parseInt("0"));
            materiaFacade.edit(unaMateria);
            materias = materiaFacade.getAllActivos();
            setInfoMessage(getMensajeGuardar());
        } catch (Exception e) {
            setWarnMessage("Es refenciada por otra entidad");
            logger.error("No se puede eliminar", e);
        }
    }
       
    //getters && setters
    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Materia> getMateriasFiltrados() {
        return materiasFiltrados;
    }

    public void setMateriasFiltrados(List<Materia> materiasFiltrados) {
        this.materiasFiltrados = materiasFiltrados;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public DualListModel<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(DualListModel<Carrera> carreras) {
        this.carreras = carreras;
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
    
}
