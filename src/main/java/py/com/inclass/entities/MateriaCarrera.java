/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "materia_carrera")
@NamedQueries({
    @NamedQuery(name = "MateriaCarrera.findAll", query = "SELECT m FROM MateriaCarrera m")})
public class MateriaCarrera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_materia_carrera")
    private Integer idMateriaCarrera;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Materia idMateria;
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Carrera idCarrera;

    public MateriaCarrera() {
    }

    public MateriaCarrera(Integer idMateriaCarrera) {
        this.idMateriaCarrera = idMateriaCarrera;
    }

    public Integer getIdMateriaCarrera() {
        return idMateriaCarrera;
    }

    public void setIdMateriaCarrera(Integer idMateriaCarrera) {
        this.idMateriaCarrera = idMateriaCarrera;
    }

    public Materia getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Materia idMateria) {
        this.idMateria = idMateria;
    }

    public Carrera getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Carrera idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateriaCarrera != null ? idMateriaCarrera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MateriaCarrera)) {
            return false;
        }
        MateriaCarrera other = (MateriaCarrera) object;
        if ((this.idMateriaCarrera == null && other.idMateriaCarrera != null) || (this.idMateriaCarrera != null && !this.idMateriaCarrera.equals(other.idMateriaCarrera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.MateriaCarrera[ idMateriaCarrera=" + idMateriaCarrera + " ]";
    }
    
}
