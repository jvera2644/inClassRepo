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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "unidb.huella")
public class Huella implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_huella")
    private Integer idHuella;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "huella")
    private byte[] huella;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public Huella() {
    }

    public Huella(Integer idHuella) {
        this.idHuella = idHuella;
    }

    public Huella(Integer idHuella, byte[] huella) {
        this.idHuella = idHuella;
        this.huella = huella;
    }

    public Integer getIdHuella() {
        return idHuella;
    }

    public void setIdHuella(Integer idHuella) {
        this.idHuella = idHuella;
    }

    public byte[] getHuella() {
        return huella;
    }

    public void setHuella(byte[] huella) {
        this.huella = huella;
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
        hash += (idHuella != null ? idHuella.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Huella)) {
            return false;
        }
        Huella other = (Huella) object;
        if ((this.idHuella == null && other.idHuella != null) || (this.idHuella != null && !this.idHuella.equals(other.idHuella))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.Huella[ idHuella=" + idHuella + " ]";
    }
    
}
