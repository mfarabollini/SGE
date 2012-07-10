/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.metadata;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author mboni
 */
@Embeddable
public class ParamReportePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "REPORTE_ID")
    private int reporteId;

    public ParamReportePK() {
    }

    public ParamReportePK(int id, int reporteId) {
        this.id = id;
        this.reporteId = reporteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReporteId() {
        return reporteId;
    }

    public void setReporteId(int reporteId) {
        this.reporteId = reporteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) reporteId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamReportePK)) {
            return false;
        }
        ParamReportePK other = (ParamReportePK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.reporteId != other.reporteId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.metadata.ParamReportePK[ id=" + id + ", reporteId=" + reporteId + " ]";
    }
    
}
