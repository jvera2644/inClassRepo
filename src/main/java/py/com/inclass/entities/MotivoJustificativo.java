/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "unidb.motivo_justificativo")
public class MotivoJustificativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_motivo")
    private Integer idMotivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMotivo", fetch = FetchType.LAZY)
    private Collection<Justificativo> justificativoCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private int estado;

    public MotivoJustificativo() {
    }

    public MotivoJustificativo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public MotivoJustificativo(Integer idMotivo, String descripcion, Integer estado) {
        this.idMotivo = idMotivo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<Justificativo> getJustificativoCollection() {
        return justificativoCollection;
    }

    public void setJustificativoCollection(Collection<Justificativo> justificativoCollection) {
        this.justificativoCollection = justificativoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMotivo != null ? idMotivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MotivoJustificativo)) {
            return false;
        }
        MotivoJustificativo other = (MotivoJustificativo) object;
        if ((this.idMotivo == null && other.idMotivo != null) || (this.idMotivo != null && !this.idMotivo.equals(other.idMotivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.MotivoJustificativo[ idMotivo=" + idMotivo + " ]";
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
}
