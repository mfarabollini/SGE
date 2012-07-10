/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Propietario
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //@Column(name = "razonSocial")
    private String razonSocial;
    //@Column(name = "cuit")
    private String cuit;
    //@Column(name = "direccion")
    private String direccion;     
    //@Column(name = "telefono")
    private String telefono;
    //@Column(name = "email")
    private String email;  
    @Column(name = "telefono_alt")
    private String telefonoAlternativo;
    @ManyToOne
    private Localidad localidad;

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }
    
    public String getTelefonoAlternativo() {
        return telefonoAlternativo;
    }

    public void setTelefonoAlternativo(String telefonoAlternativo) {
        this.telefonoAlternativo = telefonoAlternativo;
    }
    //@Column(name = "habilitado")
    private Boolean habilitado;
  
    @OneToMany(mappedBy = "cliente")
    private List<LineaEnvio> lineaEnvios;
   
    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.id = idCliente;
    }

    public Integer getId() {
        return id;
    }

    public void setIdCliente(Integer idCliente) {
        this.id = idCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.entidades.Cliente[ idCliente=" + id + " ]";
    }

    /**
     * @return the lineaEnvios
     */
    public List<LineaEnvio> getLineaEnvios() {
        return lineaEnvios;
    }

    /**
     * @param lineaEnvios the lineaEnvios to set
     */
    public void setLineaEnvios(List<LineaEnvio> lineaEnvios) {
        this.lineaEnvios = lineaEnvios;
    }
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    } 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
