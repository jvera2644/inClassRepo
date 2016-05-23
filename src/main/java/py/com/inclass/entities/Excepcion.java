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
import javax.validation.constraints.Size;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "unidb.excepcion")
public class Excepcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_excepcion")
    private Integer idExcepcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fehca_hora_carga_excepciones")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fehcaHoraCargaExcepciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_evento")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "observacion_evento")
    private String observacionEvento;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;
    @JoinColumn(name = "id_tipo_excepcion", referencedColumnName = "id_tipo_excepcion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoExcepcion idTipoExcepcion;

    public Excepcion() {
    }

    public Excepcion(Integer idExcepcion) {
        this.idExcepcion = idExcepcion;
    }

    public Excepcion(Integer idExcepcion, Date fehcaHoraCargaExcepciones, Date fechaEvento, String observacionEvento) {
        this.idExcepcion = idExcepcion;
        this.fehcaHoraCargaExcepciones = fehcaHoraCargaExcepciones;
        this.fechaEvento = fechaEvento;
        this.observacionEvento = observacionEvento;
    }

    public Integer getIdExcepcion() {
        return idExcepcion;
    }

    public void setIdExcepcion(Integer idExcepcion) {
        this.idExcepcion = idExcepcion;
    }

    public Date getFehcaHoraCargaExcepciones() {
        return fehcaHoraCargaExcepciones;
    }

    public void setFehcaHoraCargaExcepciones(Date fehcaHoraCargaExcepciones) {
        this.fehcaHoraCargaExcepciones = fehcaHoraCargaExcepciones;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getObservacionEvento() {
        return observacionEvento;
    }

    public void setObservacionEvento(String observacionEvento) {
        this.observacionEvento = observacionEvento;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoExcepcion getIdTipoExcepcion() {
        return idTipoExcepcion;
    }

    public void setIdTipoExcepcion(TipoExcepcion idTipoExcepcion) {
        this.idTipoExcepcion = idTipoExcepcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExcepcion != null ? idExcepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Excepcion)) {
            return false;
        }
        Excepcion other = (Excepcion) object;
        if ((this.idExcepcion == null && other.idExcepcion != null) || (this.idExcepcion != null && !this.idExcepcion.equals(other.idExcepcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.Excepcion[ idExcepcion=" + idExcepcion + " ]";
    }
    
}
