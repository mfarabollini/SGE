/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.clases;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sge.interfaces.IReporte;

/**
 *
 * @author Propietario
 */
@Entity
public class Reporte implements Serializable,IReporte {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    private String reporte;
    private String nombreReporte;
    private String keyReporte;
    private boolean habilitado;
    @Transient()
    private int cantCopias;
    
    public boolean isHabilitado() {
        return habilitado;
    }
    
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
    @Transient
    private  Connection coneccion;
    @Transient
    private Map parametros;
    
    public Reporte(){
        cantCopias = 1;
        parametros = new HashMap();
    }
        
    public String getReporte() {
        return reporte;
    }

    @Override
    public void setReporte(String xmlReporte) {
        this.reporte = xmlReporte;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sge.reporte.Reporte[ id=" + id + " ]";
    }

    @Override
    public void addParametro(String param) {
        int cant = parametros.size();
        cant ++;
        String key = "par"+cant;
        parametros.put(key, param);
    }

    @Override
    public void setImpresora(String impresora) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void enviar(DestinoReporte destino)throws ReporteException {
        InputStream is=null;
        JasperViewer visor=null;
        try {
            
            is = new ByteArrayInputStream(reporte.getBytes("UTF-8"));
            
            JasperDesign jasperDesign = JRXmlLoader.load(is);
         
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, coneccion);
            
            if(IReporte.DestinoReporte.PANTALLA == destino){
                visor=new JasperViewer(jasperPrint,false);
                visor.setVisible(true);
            }else{
                for(int i =1; i<= cantCopias; i++){
                    JasperPrintManager.printReport(jasperPrint, false);                  
                }
            }
             parametros.clear();
            
        } catch (Exception ex) {
             System.out.println(ex.toString());
            String msg ="Se detectó un problema con el servicio de impresión, notifique a sistemas";
            ReporteException e = new ReporteException(ex,msg);
            throw e ;            
        }
        
    }

    /**
     * @return the nombreReporte
     */
    public String getNombreReporte() {
        return nombreReporte;
    }

    /**
     * @param nombreReporte the nombreReporte to set
     */
    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }
    /**
     * @param aConeccion the coneccion to set
     */
    @Override
    public  void setConeccion(Connection aConeccion) {
        coneccion = aConeccion;
    }

    /**
     * @return the keyReporte
     */
    public String getKeyReporte() {
        return keyReporte;
    }

    /**
     * @param keyReporte the keyReporte to set
     */
    public void setKeyReporte(String keyReporte) {
        this.keyReporte = keyReporte;
    }

    @Override
    public void setNroCopias(int nroCopias) {
        cantCopias = nroCopias;
    }
    public void limpiarParametros(){
        if(parametros != null){
            parametros.clear();
        }
    }
    
}
