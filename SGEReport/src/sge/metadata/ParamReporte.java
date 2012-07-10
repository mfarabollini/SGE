/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.metadata;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author mboni
 */
@Entity
@Table(name = "paramreporte")
@NamedQueries({
    @NamedQuery(name = "ParamReporte.findAll", query = "SELECT p FROM ParamReporte p")})
public class ParamReporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParamReportePK paramReportePK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ORDEN")
    private Integer orden;
    @JoinColumn(name = "REPORTE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ReporteMetadata reporteMetadata;
    @Column(name = "OBLIGATORIO")
    private Boolean obligatorio;
    @Column(name = "CLASE")
    private String clase;
    @Column(name = "ENTIDAD")
    private Boolean entidad;
    
    public ParamReporte() {
    }

    public ParamReporte(ParamReportePK paramReportePK) {
        this.paramReportePK = paramReportePK;
    }

    public ParamReporte(int id, int reporteId) {
        this.paramReportePK = new ParamReportePK(id, reporteId);
    }

    public ParamReportePK getParamReportePK() {
        return paramReportePK;
    }

    public void setParamReportePK(ParamReportePK paramReportePK) {
        this.paramReportePK = paramReportePK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public ReporteMetadata getReporteMetadata() {
        return reporteMetadata;
    }

    public void setReporteMetadata(ReporteMetadata reporteMetadata) {
        this.reporteMetadata = reporteMetadata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paramReportePK != null ? paramReportePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamReporte)) {
            return false;
        }
        ParamReporte other = (ParamReporte) object;
        if ((this.paramReportePK == null && other.paramReportePK != null) || (this.paramReportePK != null && !this.paramReportePK.equals(other.paramReportePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.metadata.ParamReporte[ paramReportePK=" + paramReportePK + " ]";
    }

    /**
     * @return the obligatorio
     */
    public Boolean getObligatorio() {
        return obligatorio;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the entidad
     */
    public Boolean getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(Boolean entidad) {
        this.entidad = entidad;
    }
    
}
