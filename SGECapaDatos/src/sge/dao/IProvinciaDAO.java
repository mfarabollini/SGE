/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import sge.entidades.Provincia;

/**
 *
 * @author Marcos
 */
public interface IProvinciaDAO extends  IDao<Integer, Provincia> {
    Provincia buscarPorCodigo(Integer codigo);
    Provincia buscarPorNombre(String nombre); 
    List<Provincia> buscarTodas();
}