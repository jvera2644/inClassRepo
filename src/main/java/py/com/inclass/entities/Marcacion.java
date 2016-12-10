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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "unidb.marcacion")
public class Marcacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marcacion")
    private Integer idMarcacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;
    @Column(name = "verificada")
    private boolean verificada;
    
    @Transient
    private boolean marcacionFueraRango;
    
    @Transient
    private boolean habilitado;
        
    public Marcacion() {
    }

    public Marcacion(Integer idMarcacion) {
        this.idMarcacion = idMarcacion;
    }

    public Marcacion(Integer idMarcacion, Date fecha) {
        this.idMarcacion = idMarcacion;
        this.fecha = fecha;
    }

    public Integer getIdMarcacion() {
        return idMarcacion;
    }

    public void setIdMarcacion(Integer idMarcacion) {
        this.idMarcacion = idMarcacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMarcacion != null ? idMarcacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marcacion)) {
            return false;
        }
        Marcacion other = (Marcacion) object;
        if ((this.idMarcacion == null && other.idMarcacion != null) || (this.idMarcacion != null && !this.idMarcacion.equals(other.idMarcacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.Marcacion[ idMarcacion=" + idMarcacion + " ]";
    }

    public boolean isMarcacionFueraRango() {
        return marcacionFueraRango;
    }

    public void setMarcacionFueraRango(boolean marcacionFueraRango) {
        this.marcacionFueraRango = marcacionFueraRango;
    }

    public boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean getVerificada() {
        return verificada;
    }

    public void setVerificada(boolean verificada) {
        this.verificada = verificada;
    }
    
}
