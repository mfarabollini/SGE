/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import sge.entidades.Cliente;
import sge.entidades.Envio;
import sge.entidades.LineaEnvio;
import sge.exception.ConectividadException;



/**
 *
 * @author Propietario
 */
public class EnvioDAO extends DaoImp<Integer, Envio> implements IEnvioDAO  {
    public EnvioDAO()throws ConectividadException{
        
    }
    @Override
    public List<Envio> buscarEnvioPorCliente(Date fecha,Cliente aCliente) {
        List<Envio> envioList = null;
        Query q =null;
        Calendar cal =GregorianCalendar.getInstance();
        cal.setTime(fecha );
        cal.add(Calendar.MINUTE, 00);
        cal.add(Calendar.HOUR, 00);
        cal.add(Calendar.SECOND, 00);
        Date fechaMin = cal.getTime();
        
        cal.add(Calendar.HOUR, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);
        Date fechaMax = cal.getTime();
        
        q = entityManager.createQuery(            
        "SELECT  e FROM " + entityClass.getSimpleName()  +
                 " e JOIN e.lineaEnvioList l "
                 + " WHERE (e.fechaCreacion BETWEEN :fechaMin AND :fechaMax)AND (l.cliente =:cliente  )");
        q.setParameter("fechaMin",fechaMin );       
        q.setParameter("fechaMax",fechaMax );      
        q.setParameter("cliente",aCliente );
        
        return (List<Envio>) q.getResultList();
        
        
        
    }
    
    @Override
    public List<Envio> buscarEnviosCliente(Cliente aCliente) {
        List<Envio> envioList = null;
        Query q =null;
        
        q = entityManager.createQuery(            
       "SELECT e FROM " + entityClass.getSimpleName()  +
                 " e JOIN e.lineaEnvioList l "
                 + " WHERE e.fechaSalida is not null and "
                 + "       l.fechaEntrega is null and l.cliente =:cliente " );
        q.setParameter("cliente",aCliente );       

        return (List<Envio>) q.getResultList();
    }
    @Override
    public List<Envio> buscarEnvioPorFecha(Date fecha) {
        List<Envio> envioList = null;
        Query q =null;
        Calendar cal =GregorianCalendar.getInstance();
        cal.setTime(fecha );
        cal.add(Calendar.MINUTE, 00);
        cal.add(Calendar.HOUR, 00);
        cal.add(Calendar.SECOND, 00);
        Date fechaMin = cal.getTime();
        
        cal.add(Calendar.HOUR, 23);
        cal.add(Calendar.MINUTE, 59);
        cal.add(Calendar.SECOND, 59);
        Date fechaMax = cal.getTime();
        
        q = entityManager.createQuery(            
        "SELECT  e FROM " + entityClass.getSimpleName()  +
                 " e  JOIN e.lineaEnvioList l  WHERE e.fechaCreacion BETWEEN :fechaMin AND :fechaMax");
        q.setParameter("fechaMin",fechaMin );       
        q.setParameter("fechaMax",fechaMax );      
        
        return (List<Envio>) q.getResultList();
    }

    @Override
    public Envio buscarEnvioPorCodigoBarra(String codigo) {
       Query q =null;
       Envio aEnvio = null;
       q = entityManager.createQuery(            
       "SELECT  e FROM " + entityClass.getSimpleName()  +
                 " e JOIN e.lineaEnvioList l "
                 + " WHERE l.codigoDeBarra =:codigoBarra");
        q.setParameter("codigoBarra",codigo ); 
        try{
            aEnvio = (Envio) q.getSingleResult();
        }catch(Exception e){
            
        }
        return aEnvio;
        
    }
    @Override
    public Envio buscarEnvioPorCodigo(Integer idEnvio) {
       return findById(idEnvio);
    }   

    @Override
    public boolean registrarDespacho(Envio aEnvio) {
        boolean resultado=true;
        Envio envioLoc = null;
        LineaEnvio lineaEnvio= null;
        LineaEnvio lineaEnvioLoc = null;
                
        entityManager.getTransaction().begin();
        try {
            envioLoc = findById(aEnvio.getIdenvio());
            List<LineaEnvio> lineas =aEnvio.getLineaEnvioList();
            List<LineaEnvio> lineasLoc = envioLoc.getLineaEnvioList();
            
            for (int pos=0; pos< lineas.size();pos++) {
                lineaEnvio = lineas.get(pos);                
                lineaEnvioLoc = lineasLoc.get(pos);
                entityManager.merge(lineaEnvioLoc);
            }            
            envioLoc.setFechaSalida(GregorianCalendar.getInstance().getTime());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            resultado=false;
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        
        
        return resultado;
    }

    @Override
    public Envio buscarEnvioPorNroGuia(String codigo) {
        Query q =null;
        Envio aEnvio = null;
        q = entityManager.createQuery(            
        "SELECT  e FROM " + entityClass.getSimpleName()  +
                 " e JOIN e.lineaEnvioList l "
                 + " WHERE l.nroGuiaTransporte =:nroGuia");
        q.setParameter("nroGuia",codigo );       
        
        try{
            aEnvio = (Envio) q.getSingleResult();
        }catch(Exception e){
            
        }
        
        return aEnvio;
    }

    @Override
    public boolean registrarConfirmacion(Envio aEnvio,String codigoBarra) {
        boolean resultado= true;
        Envio envioLoc = null;
        LineaEnvio lineaEnvio= null;
        LineaEnvio lineaEnvioLoc = null;
        Date fechaLinea=null;
        Date fechaLoc = GregorianCalendar.getInstance().getTime();
        
        Calendar cal =GregorianCalendar.getInstance();
        cal.setTime(GregorianCalendar.getInstance().getTime() );
        cal.add(Calendar.MINUTE, 00);
        cal.add(Calendar.HOUR, 00);
        cal.add(Calendar.SECOND, 00);
        fechaLoc = cal.getTime();
        
        
        
        entityManager.getTransaction().begin();
        try {
            envioLoc = findById(aEnvio.getIdenvio());
            List<LineaEnvio> lineas =aEnvio.getLineaEnvioList();
            List<LineaEnvio> lineasLoc = envioLoc.getLineaEnvioList();
            
            for (int pos=0; pos< lineas.size();pos++) {
                lineaEnvio = lineas.get(pos);
                               
                cal.setTime(lineaEnvio.getFechaConfirmacion() );
                cal.add(Calendar.MINUTE, 00);
                cal.add(Calendar.HOUR, 00);
                cal.add(Calendar.SECOND, 00);
                fechaLinea = cal.getTime();
                
                if(lineaEnvio.getCodigoDeBarra().equals(codigoBarra)){
                    lineaEnvioLoc = lineasLoc.get(pos);
                    entityManager.merge(lineaEnvioLoc);                    
                    lineaEnvioLoc.setCantDevuelta(lineaEnvio.getCantDevuelta());
                    lineaEnvioLoc.setMotivoDevolucion(lineaEnvio.getMotivoDevolucion());
                    lineaEnvioLoc.setObservaciones(lineaEnvio.getObservaciones());
                    lineaEnvioLoc.setFechaConfirmacion(fechaLinea);
                    lineaEnvioLoc.setFechaEntrega(lineaEnvio.getFechaEntrega());
                    lineaEnvioLoc.setNroGuiaTransporte(lineaEnvio.getNroGuiaTransporte());
                }
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            resultado=false;
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        
        
        return resultado;
    }

    @Override
    public Envio buscarEnvioPorFactura(String numeroFactura) {
       Query q =null;
       Envio aEnvio = null;
       
       q = entityManager.createQuery(            
       "SELECT  e FROM " + entityClass.getSimpleName()  +
                 " e JOIN e.lineaEnvioList l "
                 + " WHERE l.nroFactura =:pNroFactura");
        q.setParameter("pNroFactura",numeroFactura ); 
        try{
            aEnvio = (Envio) q.getSingleResult();
        }catch(Exception e){
            
        }
        return aEnvio;
    }
    
}



