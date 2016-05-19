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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Edu
 */
@Entity
@Table(name = "justificativo")
@NamedQueries({
    @NamedQuery(name = "Justificativo.findAll", query = "SELECT j FROM Justificativo j")})
public class Justificativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_justificativo")
    private Integer idJustificativo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora_justificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraJustificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ausencia")
    @Temporal(TemporalType.DATE)
    private Date fechaAusencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private int estado;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "documento")
    private byte[] documento;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Persona idPersona;
    @JoinColumn(name = "id_motivo", referencedColumnName = "id_motivo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MotivoJustificativo idMotivo;

    public Justificativo() {
    }

    public Justificativo(Integer idJustificativo) {
        this.idJustificativo = idJustificativo;
    }

    public Justificativo(Integer idJustificativo, Date fechaHoraJustificacion, Date fechaAusencia, int estado, byte[] documento) {
        this.idJustificativo = idJustificativo;
        this.fechaHoraJustificacion = fechaHoraJustificacion;
        this.fechaAusencia = fechaAusencia;
        this.estado = estado;
        this.documento = documento;
    }

    public Integer getIdJustificativo() {
        return idJustificativo;
    }

    public void setIdJustificativo(Integer idJustificativo) {
        this.idJustificativo = idJustificativo;
    }

    public Date getFechaHoraJustificacion() {
        return fechaHoraJustificacion;
    }

    public void setFechaHoraJustificacion(Date fechaHoraJustificacion) {
        this.fechaHoraJustificacion = fechaHoraJustificacion;
    }

    public Date getFechaAusencia() {
        return fechaAusencia;
    }

    public void setFechaAusencia(Date fechaAusencia) {
        this.fechaAusencia = fechaAusencia;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public MotivoJustificativo getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(MotivoJustificativo idMotivo) {
        this.idMotivo = idMotivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJustificativo != null ? idJustificativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Justificativo)) {
            return false;
        }
        Justificativo other = (Justificativo) object;
        if ((this.idJustificativo == null && other.idJustificativo != null) || (this.idJustificativo != null && !this.idJustificativo.equals(other.idJustificativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.inclass.entities.Justificativo[ idJustificativo=" + idJustificativo + " ]";
    }
    
}
