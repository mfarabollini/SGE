/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sge.dao;

import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import sge.exception.ConectividadException;
/**
 *
 * @author mboni
 */
public abstract class DaoImp<K, E> implements IDao<K, E>  {
    static {
         emf = Persistence.createEntityManagerFactory("SGECapaDatosPU");
    }
    private static EntityManagerFactory emf;
    protected Class<E> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;

    public DaoImp() throws ConectividadException {
        try{ 
            entityManager = emf.createEntityManager();
            ParameterizedType genericSuperclass = (ParameterizedType)getClass().getGenericSuperclass();
            this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
        }catch(Exception e) {
            
            String msg= "El servicio de base de datos no esta disponible, notifique a sistemas";
            ConectividadException excepCon = new ConectividadException(e,msg);
            throw excepCon;
        }
    }

    @Override
    public void persist(E entity) throws ConectividadException  { 
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(entity);
            entityManager.flush();
            entityManager.getTransaction().commit();
            
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            String msg= "El servicio de base de datos no esta disponible, notifique a sistemas";
            ConectividadException excepCon = new ConectividadException(e,msg);
            throw excepCon;
            
        } finally {
            entityManager.close();
        }
      
      
    }

    @Override
    public void remove(E entity) { 
        entityManager.remove(entity);
    }

    @Override
    public E findById(K id) {
        return entityManager.find(entityClass, id);
    }

 
}
