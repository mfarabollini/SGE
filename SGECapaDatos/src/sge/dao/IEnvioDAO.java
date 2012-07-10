/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.Date;
import java.util.List;
import sge.entidades.Cliente;
import sge.entidades.Envio;

/**
 *
 * @author Propietario
 */
public interface IEnvioDAO extends IDao<Integer,Envio>{
     public List<Envio> buscarEnvioPorCliente(Date fecha,Cliente aCliente);
     public List<Envio> buscarEnvioPorFecha(Date fecha);
     public Envio buscarEnvioPorCodigoBarra(String codigo);
     public boolean registrarDespacho(Envio aEnvio);
     public Envio buscarEnvioPorNroGuia(String codigo);
     public boolean registrarConfirmacion(Envio aEnvio,String codigoBarra);
     public Envio buscarEnvioPorFactura(String numeroFactura);
     public List<Envio> buscarEnviosCliente(Cliente aCliente);
     public Envio buscarEnvioPorCodigo(Integer idEnvio);
}
