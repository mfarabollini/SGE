/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import javax.persistence.Query;
import sge.entidades.Parametro;
import sge.exception.ConectividadException;

/**
 *
 * @author Propietario
 */
public class ParametroDAO extends DaoImp<Integer, Parametro> implements IParametroDAO {

    public ParametroDAO() throws ConectividadException {
    }
    
    @Override
    public Parametro buscarPorKey(String codKey) {
        Parametro aParam = null;
        Query q = entityManager.createQuery(            
        "SELECT e FROM " + entityClass.getSimpleName()  + " e WHERE  UPPER(e.codKey) =:pCodKey");
        q.setParameter("pCodKey",codKey );        
        
        try{
            aParam = (Parametro) q.getSingleResult();
        }catch(Exception e){
            
        }
        return aParam;
    }

    @Override
    public List<Parametro> buscarTodos() {
        
        Query q = entityManager.createQuery(            
        "SELECT e FROM " + entityClass.getSimpleName()  + " e");
        
        return q.getResultList();
    }
    
}
