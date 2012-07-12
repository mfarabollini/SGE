/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Propietario
 */
@Entity
@Table(name = "lineaenvio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LineaEnvio.findAll", query = "SELECT l FROM LineaEnvio l")})
public class LineaEnvio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NUMEROLINEA")
    private int numeroLinea;
    @Column(name = "CANTBULTOS")
    private Integer cantBultos;
    @Column(name = "FECHAENTREGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Column(name = "NROFACTURA")
    private String nroFactura;
    @Column(name = "NROGUIATRANSPORTE")
    private String nroGuiaTransporte;
    @Column(name = "CODIGODEBARRA" )
    private String codigoDeBarra;
    @Column(name = "MOTIVODEVOLUCION")
    private String motivoDevolucion;
    @Column(name = "CANTDEVUELTA")
    private Integer cantDevuelta;
    @Column(name = "FECHACONFIRMACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaConfirmacion;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @ManyToOne()
    private Cliente cliente;
    @ManyToOne(cascade= CascadeType.ALL)
    private Envio envio;

    public LineaEnvio() {
    }

    public LineaEnvio(Integer id) {
        this.id = id;
    }

    public LineaEnvio(Integer id, int numerolinea) {
        this.id = id;
        this.numeroLinea = numerolinea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numerolinea) {
        this.numeroLinea = numerolinea;
    }

    public Integer getCantBultos() {
        return cantBultos;
    }

    public void setCantBultos(Integer cantbultos) {
        this.cantBultos = cantbultos;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaentrega) {
        this.fechaEntrega = fechaentrega;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nrofactura) {
        this.nroFactura = nrofactura;
    }

    public String getNroGuiaTransporte() {
        return nroGuiaTransporte;
    }

    public void setNroGuiaTransporte(String nroguiatransporte) {
        this.nroGuiaTransporte = nroguiatransporte;
    }

    public String getCodigoDeBarra() {
        return codigoDeBarra;
    }

    public void setCodigoDeBarra(String codigodebarra) {
        this.codigoDeBarra = codigodebarra;
    }

    public String getMotivoDevolucion() {
        return motivoDevolucion;
    }

    public void setMotivoDevolucion(String motivodevolucion) {
        this.motivoDevolucion = motivodevolucion;
    }

    public Integer getCantDevuelta() {
        return cantDevuelta;
    }

    public void setCantDevuelta(Integer cantdevuelta) {
        this.cantDevuelta = cantdevuelta;
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(Date fechaconfirmacion) {
        this.fechaConfirmacion = fechaconfirmacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof LineaEnvio)) {
            return false;
        }
        LineaEnvio other = (LineaEnvio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.entidades.LineaEnvio[ id=" + id + " ]";
    }

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the envio
     */
    public Envio getEnvio() {
        return envio;
    }

    /**
     * @param envio the envio to set
     */
    public void setEnvio(Envio envio) {
        this.envio = envio;
    }
    @PostPersist 
    private void generarCodigoDeBarra(){
        String codigoEnv = "0000000" + envio.getIdenvio().toString() ;
        String codLinea = "000" + this.numeroLinea; 
        this.codigoDeBarra =  codigoEnv.substring(codigoEnv.length() - 8, codigoEnv.length())+ codLinea.substring(codLinea.length() - 4, codLinea.length());
    }
        public Integer getDigitoVerificador(){
        //this.codigoDeBarra;
        int var_cont = 0;
        int sumaPar = 0;
        int sumaImpar = 0;
        int total = 0;

        while ( var_cont <= 11 ){
            var_cont++;
            Integer nro = Integer.parseInt(this.codigoDeBarra.substring(var_cont-1, var_cont))*1;

            if((var_cont % 2)==0){
                sumaPar = sumaPar + nro;
            }else{
                sumaImpar = sumaImpar + nro;
            }
            nro = 0;
        }
        
    total = (sumaPar*3) + sumaImpar;
    return (10  - (total%10))%10;
   
    }
    
}
