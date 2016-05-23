/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "unidb.clases") 
public class Clases implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_clases")
    private Integer idClases;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraFin;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Persona idPersona;
    @JoinColumn(name = "id_horario", referencedColumnName = "id_horario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Horario idHorario;

    public Clases() {
    }

    public Clases(Integer idClases) {
        this.idClases = idClases;
    }

    public Clases(Integer idClases, Date fechaHoraInicio, Date fechaHoraFin) {
        this.idClases = idClases;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
    }

    public Integer getIdClases() {
        return idClases;
    }

    public void setIdClases(Integer idClases) {
        this.idClases = idClases;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Horario getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Horario idHorario) {
        this.idHorario = idHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClases != null ? idClases.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clases)) {
            return false;
        }
        Clases other = (Clases) object;
        if ((this.idClases == null && other.idClases != null) || (this.idClases != null && !this.idClases.equals(other.idClases))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.Clases[ idClases=" + idClases + " ]";
    }
    
}
