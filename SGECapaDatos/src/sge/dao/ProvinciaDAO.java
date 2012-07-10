/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import javax.persistence.Query;
import sge.entidades.Provincia;
import sge.exception.ConectividadException;

/**
 *
 * @author Marcos
 */
public class ProvinciaDAO extends DaoImp<Integer, Provincia> implements IProvinciaDAO  {
    public ProvinciaDAO() throws  ConectividadException {
        
    }

    @Override
    public Provincia buscarPorCodigo(Integer codigo) {
        return findById(codigo);
    }
    
    @Override
    public Provincia buscarPorNombre(String nombre) {
        Query q = entityManager.createQuery(            
        "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE e.nombre = :parnombre" );
        q.setParameter("parnombre",nombre );   
        return (Provincia) q.getSingleResult();
    }

    @Override
    public List<Provincia> buscarTodas() {
                
        Query q = entityManager.createQuery(            
        "SELECT e FROM " + entityClass.getSimpleName()  + " e" );
        return (List<Provincia>) q.getResultList();
    }
    
}