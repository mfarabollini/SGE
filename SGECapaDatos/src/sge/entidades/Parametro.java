/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Propietario
 */
@Entity
public class Parametro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String codKey;
    
    @OneToMany(mappedBy = "parametro", cascade= CascadeType.ALL)
    private List<DetalleParametro> detalle;
    private boolean habilitado;

    public Parametro(){
        this.habilitado = true;
        this.detalle = new ArrayList();
    }
    
    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    
    
    
    public List<DetalleParametro> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleParametro> detalle) {
        this.detalle = detalle;
    }
    
    public String getCodKey() {
        return codKey;
    }

    public void setCodKey(String codKey) {
        this.codKey = codKey;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.entidades.Parametro[ id=" + id + " ]";
    }
    
    public void agregarDetalle(String pCodKey, String pDesc, String pValor){
        DetalleParametro aDetalle = new DetalleParametro();
        aDetalle.setCodKey(pCodKey);
        aDetalle.setDescripcion(pDesc);
        aDetalle.setValor(pValor);   
        aDetalle.setParametro(this);
        this.detalle.add(aDetalle);
    }
    
}
