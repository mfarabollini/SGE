/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.metadata;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author mboni
 */
@Entity
@Table(name = "reporte")
@NamedQueries({
    @NamedQuery(name = "ReporteMetadata.findAll", query = "SELECT r FROM ReporteMetadata r")})
public class ReporteMetadata implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "HABILITADO")
    private Boolean habilitado;
    @Column(name = "KEYREPORTE")
    private String keyreporte;
    @Column(name = "NOMBREREPORTE")
    private String nombrereporte;
    @Lob
    @Column(name = "REPORTE")
    private String reporte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reporteMetadata")
    private List<ParamReporte> paramReporteList;
    @JoinColumn(name = "GRUPO_ID", referencedColumnName = "ID")
    @ManyToOne
    private Grupo grupo;

    public ReporteMetadata() {
    }

    public ReporteMetadata(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getKeyreporte() {
        return keyreporte;
    }

    public void setKeyreporte(String keyreporte) {
        this.keyreporte = keyreporte;
    }

    public String getNombrereporte() {
        return nombrereporte;
    }

    public void setNombrereporte(String nombrereporte) {
        this.nombrereporte = nombrereporte;
    }

    public String getReporte() {
        return reporte;
    }

    public void setReporte(String reporte) {
        this.reporte = reporte;
    }

    public List<ParamReporte> getParamReporteList() {
        return paramReporteList;
    }

    public void setParamReporteList(List<ParamReporte> paramReporteList) {
        this.paramReporteList = paramReporteList;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReporteMetadata)) {
            return false;
        }
        ReporteMetadata other = (ReporteMetadata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.metadata.ReporteMetadata[ id=" + id + " ]";
    }
    
}
