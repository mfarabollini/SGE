/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import sge.entidades.Etiqueta;
import sge.exception.ConectividadException;

/**
 *
 * @author mboni
 */
public class EtiquetaDAO extends DaoImp<Integer, Etiqueta> implements   IEtiquetaDAO{
    public EtiquetaDAO()throws  ConectividadException{
    
    }
}
