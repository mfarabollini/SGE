/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sge.dao;

import sge.exception.ConectividadException;

/**
 *
 * @author mboni
 */
public interface IDao<K, E> {
    public void persist(E entity) throws ConectividadException;
    public void remove(E entity);
    public E findById(K id);
}
