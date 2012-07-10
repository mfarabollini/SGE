/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Propietario
 */
@Entity
@Table(name = "envio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Envio.findAll", query = "SELECT e FROM Envio e")})
public class Envio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    //@Column(name = "idenvio")
    private Integer id;
    @Basic(optional = false)
    //@Column(name = "fechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    //@Column(name = "fechaSalida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @ManyToOne()
    private MedioEnvio medio;
    
    @OneToMany(mappedBy = "envio", cascade= CascadeType.ALL)
    private List<LineaEnvio> lineaEnvioList;

    public Envio() {
    }

    public Envio(Integer idenvio) {
        this.id = idenvio;
    }

    public Envio(Integer idenvio, Date fechaCreacion) {
        this.id = idenvio;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdenvio() {
        return id;
    }

    public void setIdenvio(Integer idenvio) {
        this.id = idenvio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public MedioEnvio getIdmedio() {
        return medio;
    }

    public void setIdmedio(MedioEnvio idmedio) {
        this.medio = idmedio;
    }

    @XmlTransient
    public List<LineaEnvio> getLineaEnvioList() {
        return lineaEnvioList;
    }

    public void setLineaEnvioList(List<LineaEnvio> lineaEnvioList) {
        this.lineaEnvioList = lineaEnvioList;
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
        if (!(object instanceof Envio)) {
            return false;
        }
        Envio other = (Envio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.entidades.Envio[ idenvio=" + id + " ]";
    }
    
    public boolean agregarLinea(Map linea){
        int cant=0;
        LineaEnvio aLinea;
        Cliente aCliente = null;
        
        if(lineaEnvioList== null){
           lineaEnvioList= new ArrayList(); 
        }
        cant = lineaEnvioList.size();
        cant++;
        aCliente=(Cliente) linea.get("CLI");
        String obj= linea.get("BUL").toString();
        int cantBultos = Integer.parseInt(obj);
        aLinea = new LineaEnvio();
        aLinea.setNumeroLinea(cant);
        aLinea.setCliente(aCliente);
        aLinea.setNroFactura(linea.get("FAC").toString());
        aLinea.setCantBultos(cantBultos);
        aLinea.setEnvio(this);
        
        lineaEnvioList.add(aLinea);
        return true;
    }
}
