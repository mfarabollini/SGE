/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import sge.entidades.Parametro;

/**
 *
 * @author Propietario
 */
public interface IParametroDAO extends  IDao<Integer, Parametro>  {
   Parametro buscarPorKey(String codKey);     
   List<Parametro> buscarTodos();
}
