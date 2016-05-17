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
@Table(name = "carrera_semestre")
@NamedQueries({
    @NamedQuery(name = "CarreraSemestre.findAll", query = "SELECT c FROM CarreraSemestre c")})
public class CarreraSemestre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_carrera_semestre")
    private Integer idCarreraSemestre;
    @JoinColumn(name = "id_semestre", referencedColumnName = "id_semestre")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Semestre idSemestre;
    @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Carrera idCarrera;

    public CarreraSemestre() {
    }

    public CarreraSemestre(Integer idCarreraSemestre) {
        this.idCarreraSemestre = idCarreraSemestre;
    }

    public Integer getIdCarreraSemestre() {
        return idCarreraSemestre;
    }

    public void setIdCarreraSemestre(Integer idCarreraSemestre) {
        this.idCarreraSemestre = idCarreraSemestre;
    }

    public Semestre getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Semestre idSemestre) {
        this.idSemestre = idSemestre;
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
        hash += (idCarreraSemestre != null ? idCarreraSemestre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarreraSemestre)) {
            return false;
        }
        CarreraSemestre other = (CarreraSemestre) object;
        if ((this.idCarreraSemestre == null && other.idCarreraSemestre != null) || (this.idCarreraSemestre != null && !this.idCarreraSemestre.equals(other.idCarreraSemestre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.CarreraSemestre[ idCarreraSemestre=" + idCarreraSemestre + " ]";
    }
    
}
