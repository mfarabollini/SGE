/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import javax.persistence.Query;
import sge.entidades.MedioEnvio;
import sge.exception.ConectividadException;

/**
 *
 * @author Propietario
 */
public class MedioEnvioDAO extends DaoImp<Integer, MedioEnvio> implements IMedioEnvioDAO  {

    public MedioEnvioDAO() throws  ConectividadException {
    }

    @Override
    public MedioEnvio buscarPorCodigo(Integer codigo,boolean habilitado) {
        MedioEnvio aMedio = null;
        Query q=null;
        
        if(!habilitado){
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE e.id =:pcodigo AND e.habilitado = true");
        }else{
             q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE e.id =:pcodigo");
        }
        q.setParameter("pcodigo",codigo );
        try{
            aMedio = (MedioEnvio) q.getSingleResult();
        }catch(Exception e){
            
        }
        return aMedio;
     
    }

    @Override
    public List<MedioEnvio> buscarPorRazonSocial(String rsocial,boolean habilitado) {
        Query q=null;
        if(!habilitado){
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE  UPPER(e.razonSocial) LIKE :parrsocial AND e.habilitado = true");
        }else{
            q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE  UPPER(e.razonSocial) LIKE :parrsocial");
        }
        q.setParameter("parrsocial","%" + rsocial.toUpperCase() + "%" );
        return (List<MedioEnvio>) q.getResultList();
    }

    @Override
    public Boolean actualizarMedio(MedioEnvio aMedio) {
        MedioEnvio medLocal = null;
        entityManager.getTransaction().begin();
        try {
           medLocal = entityManager.find(MedioEnvio.class, aMedio.getId());
           medLocal.setRazonSocial(aMedio.getRazonSocial());
           medLocal.setCuit( aMedio.getCuit());
           medLocal.setDireccion( aMedio.getDireccion());
           medLocal.setTel( aMedio.getTel());
           medLocal.setClase(aMedio.getClase());
           medLocal.setHabilitado( aMedio.getHabilitado());
           
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
    public MedioEnvio buscarPorCuit(String cuit) {
        MedioEnvio aMedio= null;
        
        String loccuit= cuit.trim().replace("-", "");
          
        Query q = entityManager.createQuery(            
            "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE  e.cuit =:pcuit");
        q.setParameter("pcuit",loccuit);
        
        try{
            aMedio = (MedioEnvio) q.getSingleResult();
        }catch(Exception e){
            
        }
        
        return aMedio;
    }
}

