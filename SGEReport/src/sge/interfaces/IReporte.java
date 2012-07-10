/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.interfaces;

import java.sql.Connection;
import sge.clases.ReporteException;

/**
 *
 * @author Propietario
 */

public interface IReporte {
    public enum DestinoReporte {
                    IMPRESORA, PANTALLA;
    }
    
    public void setNroCopias(int nroCopias);
    public void addParametro(String param);
    public void setReporte(String nombre);
    public void setImpresora(String impresora);
    public  void setConeccion(Connection aConeccion);
    public void enviar(IReporte.DestinoReporte destino)throws ReporteException;
    
}
