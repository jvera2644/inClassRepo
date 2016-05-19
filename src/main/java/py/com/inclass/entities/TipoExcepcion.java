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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "tipo_excepcion")
@NamedQueries({
    @NamedQuery(name = "TipoExcepcion.findAll", query = "SELECT t FROM TipoExcepcion t")})
public class TipoExcepcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_excepcion")
    private Integer idTipoExcepcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descripion")
    private String descripion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoExcepcion", fetch = FetchType.LAZY)
    private Collection<Excepcion> excepcionCollection;

    public TipoExcepcion() {
    }

    public TipoExcepcion(Integer idTipoExcepcion) {
        this.idTipoExcepcion = idTipoExcepcion;
    }

    public TipoExcepcion(Integer idTipoExcepcion, String descripion) {
        this.idTipoExcepcion = idTipoExcepcion;
        this.descripion = descripion;
    }

    public Integer getIdTipoExcepcion() {
        return idTipoExcepcion;
    }

    public void setIdTipoExcepcion(Integer idTipoExcepcion) {
        this.idTipoExcepcion = idTipoExcepcion;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public Collection<Excepcion> getExcepcionCollection() {
        return excepcionCollection;
    }

    public void setExcepcionCollection(Collection<Excepcion> excepcionCollection) {
        this.excepcionCollection = excepcionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoExcepcion != null ? idTipoExcepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoExcepcion)) {
            return false;
        }
        TipoExcepcion other = (TipoExcepcion) object;
        if ((this.idTipoExcepcion == null && other.idTipoExcepcion != null) || (this.idTipoExcepcion != null && !this.idTipoExcepcion.equals(other.idTipoExcepcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.TipoExcepcion[ idTipoExcepcion=" + idTipoExcepcion + " ]";
    }
    
}
