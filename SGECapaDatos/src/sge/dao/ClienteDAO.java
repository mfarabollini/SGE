/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import javax.persistence.Query;
import sge.entidades.Cliente;
import sge.entidades.MedioEnvio;
import sge.exception.ConectividadException;

/**
 *
 * @author Propietario
 */
public class ClienteDAO extends DaoImp<Integer, Cliente> implements IClienteDAO  {

    public ClienteDAO() throws ConectividadException {
    }
    
    @Override
    public List<Cliente> buscarPorRazonSocial(String rsocial,boolean habilitado) {
        Query q=null;
        if(!habilitado){
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE  UPPER(e.razonSocial) LIKE :parrsocial AND e.habilitado = true");
        }else{
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE  UPPER(e.razonSocial) LIKE :parrsocial ");
        }    
        q.setParameter("parrsocial","%" + rsocial.toUpperCase() + "%" );

        return (List<Cliente>) q.getResultList();
        
        
    }

    @Override
    public Cliente buscarPorCodigo(Integer codigo,boolean habilitado) {
        Cliente aCliente = null;
        Query q=null;
        
        if(!habilitado){
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE e.id =:pcodigo AND e.habilitado =true");
        }else{
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE e.id =:pcodigo");
        }
         q.setParameter("pcodigo",codigo );
        try{
            aCliente = (Cliente) q.getSingleResult();
        }catch(Exception e){
            
        }
        return aCliente;
    }

    @Override
    public Boolean actualizarCliente(Cliente aCliente) {
        
        Cliente cliLocal = null;
        entityManager.getTransaction().begin();
        try {

           cliLocal = entityManager.find(Cliente.class, aCliente.getId());

           cliLocal.setRazonSocial(aCliente.getRazonSocial());
           cliLocal.setCuit( aCliente.getCuit());
           cliLocal.setDireccion( aCliente.getDireccion());
           cliLocal.setTelefono( aCliente.getTelefono());
           cliLocal.setTelefonoAlternativo( aCliente.getTelefonoAlternativo());
           cliLocal.setEmail( aCliente.getEmail());
           cliLocal.setLocalidad( aCliente.getLocalidad());
           cliLocal.setHabilitado( aCliente.getHabilitado());
           
           entityManager.getTransaction().commit();
           return true;
      } catch (Exception e) {
           e.printStackTrace();
           entityManager.getTransaction().rollback();
           return false;
       } finally {
           entityManager.close();
           
       }
        
    }

    @Override
    public Cliente buscarPorCuit(String cuit) {
        Cliente aCliente = null;
        Query q=null;
        String loccuit= cuit.trim().replace("-", "");
        q = entityManager.createQuery(            
        "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE e.cuit =:cuit");
        q.setParameter("cuit",loccuit);
        
        try{
            aCliente = (Cliente) q.getSingleResult();
        }catch(Exception e){
            
        }
        return aCliente;
    }
    
    
    
}
